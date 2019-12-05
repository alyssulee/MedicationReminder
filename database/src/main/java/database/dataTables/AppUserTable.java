package database.dataTables;

import database.SQLDatabase;
import model.*;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class AppUserTable extends SQLDatabase
{
    public AppUserTable()
    {
        super();
        createUserTable();
    }

    private void createUserTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "AppUser", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE AppUser " +
                        "(IDNum VARCHAR(255) NOT NULL," +
                        "Fname VARCHAR(255) NOT NULL," +
                        "LName VARCHAR(255) NOT NULL," +
                        "Username VARCHAR(255) NOT NULL," +
                        "Password VARCHAR(255) NOT NULL," +
                        "UserType VARCHAR(255) NOT NULL," +
                        "PRIMARY KEY (IDNum)," +
                        "UNIQUE(Username) )";
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User login(String username, String password)
    {
        User user = null;
        try
        {
            String query = "SELECT * FROM AppUser WHERE username = ? AND password = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, username);
            pState.setString(2, password);
            resultSet = pState.executeQuery();
            while(resultSet.next())
            {
                UserType userType = UserType.valueOf(resultSet.getString("UserType"));
                UUID idNum = UUID.fromString(resultSet.getString("IDNum"));
                String firstname = resultSet.getString("Fname");
                String lastname = resultSet.getString("LName");

                switch(userType)
                {
                    case Patient:
                        PatientTables patientTables = new PatientTables();
                        user = patientTables.getPatientByID(idNum).get();
                        break;
                    case FamilyMember:
                        FamilyMemberTable fTable = new FamilyMemberTable();
                        user = fTable.getFamilyMemberByID(idNum).get();
                        break;
                    case Doctor:
                        DoctorTable dTable = new DoctorTable();
                        user = dTable.getDoctorByID(idNum).get();
                        break;
                    case Pharmacist:
                        PharmacistTable pTable = new PharmacistTable();
                        user = pTable.getPharmacistByID(idNum).get();
                        break;
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return user;
    }

    public boolean addUser(User user, UserType userType)
    {
        boolean returnVal = true;
        try
        {
            String query = "INSERT INTO appuser (IDNum, Fname, Lname, Username, Password, usertype)" +
                            "VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, user.getId().toString());
            pState.setString(2, user.getFirstName());
            pState.setString(3, user.getLastName());
            pState.setString(4, user.getUsername());
            pState.setString(5, user.getPassword());
            pState.setString(6, userType.toString());
            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    //Since foreign keys referencing AppUser IDNum are cascade upon delete, removing user will remove it in other tables as well.
    public boolean removeUser(User user)
    {
        try
        {
            String query = "DELETE FROM AppUser WHERE IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, user.getId().toString());
            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
