package database.dataTables;

import database.SQLDatabase;
import model.Doctor;
import model.Pharmacist;
import model.UserType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class DoctorTable extends SQLDatabase
{
    public DoctorTable()
    {
        super();
        createDoctorTable();
        readTextFile();
    }

    private void createDoctorTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "Doctor", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE Doctor " +
                        "(IDNum VARCHAR(255) NOT NULL," +
                        "PRIMARY KEY (IDNum)," +
                        "FOREIGN KEY (IDNum) REFERENCES AppUser(IDNum)" +
                        "ON DELETE CASCADE ON UPDATE CASCADE);";
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addDoctor(Doctor doctor)
    {
        try
        {
            String query = "INSERT INTO Doctor (IDNum)" +
                    "VALUES ((SELECT IDNum FROM appuser WHERE IDNum = ?));";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, doctor.getId().toString());
            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Doctor> getAllDoctors()
    {
        ArrayList<Doctor> doctorList = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM AppUser, Doctor WHERE AppUser.IDNum = Doctor.IDNum";
            resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                UUID id = UUID.fromString(resultSet.getString("IDNum"));
                String firstname = resultSet.getString("Fname");
                String lastname = resultSet.getString("Lname");
                String username = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                doctorList.add(new Doctor(id, firstname, lastname, username, password));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return doctorList;
    }

    private void readTextFile()
    {
        boolean bool = false;
        try
        {
            String query = "SELECT * FROM Doctor";
            resultSet = statement.executeQuery(query);
            bool = resultSet.next();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        //If no prior data exists, read from file
        if (bool == false)
        {
            try
            {
                FileReader fileReader = new FileReader("Doctor.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    String[] temp = line.split(",");
                    UUID id = UUID.randomUUID();
                    String fname = temp[0];
                    String lname = temp[1];
                    String username = temp[2];
                    String password = temp[3];
                    ArrayList<String> names = new ArrayList<>();
                    Doctor d = new Doctor(id, fname, lname, username, password);

                    AppUserTable userTable = new AppUserTable();
                    userTable.addUser(d, UserType.Doctor);
                    addDoctor(d);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
