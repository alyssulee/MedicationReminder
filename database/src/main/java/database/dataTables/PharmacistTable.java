package database.dataTables;

import database.SQLDatabase;
import model.FamilyMember;
import model.Patient;
import model.Pharmacist;
import model.UserType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class PharmacistTable extends SQLDatabase
{
    public PharmacistTable()
    {
        super();
        createPharmacistTable();
        readTextFile();
    }

    private void createPharmacistTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "Pharmacist", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE Pharmacist " +
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

    public boolean addPharmacist(Pharmacist pharmacist)
    {
        try
        {
            String query = "INSERT INTO Pharmacist (IDNum)" +
                    "VALUES ((SELECT IDNum FROM appuser WHERE IDNum = ?));";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, pharmacist.getId().toString());
            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Pharmacist> getAllPharmacists()
    {
        ArrayList<Pharmacist> pharmList = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM AppUser, Pharmacist WHERE AppUser.IDNum = Pharmacist.IDNum";
            resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                UUID id = UUID.fromString(resultSet.getString("IDNum"));
                String firstname = resultSet.getString("Fname");
                String lastname = resultSet.getString("Lname");
                String username = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                pharmList.add(new Pharmacist(id, firstname, lastname, username, password));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return pharmList;
    }


    private void readTextFile()
    {
        boolean bool = false;
        try
        {
            String query = "SELECT * FROM Pharmacist";
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
                FileReader fileReader = new FileReader("Pharmacist.txt");
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
                    Pharmacist p = new Pharmacist(id, fname, lname, username, password);

                    AppUserTable userTable = new AppUserTable();
                    userTable.addUser(p, UserType.Pharmacist);
                    addPharmacist(p);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public Optional<Pharmacist> getPharmacistByID(UUID idNum)
    {
        Pharmacist pharmacist = null;
        try
        {
            String query = "SELECT * FROM Pharmacist, AppUser WHERE Pharmacist.IDNum = ? AND AppUser.IDNum = Pharmacist.IDNum";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, idNum.toString());
            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                UUID id = UUID.fromString(resultSet.getString("IDNum"));
                String firstname = resultSet.getString("Fname");
                String lastname = resultSet.getString("Lname");
                String username = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                pharmacist = new Pharmacist(id, firstname, lastname, username, password);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Optional.ofNullable(pharmacist);
    }
}
