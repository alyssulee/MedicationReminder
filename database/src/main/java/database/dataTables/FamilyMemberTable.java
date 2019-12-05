package database.dataTables;

import database.SQLDatabase;
import model.FamilyMember;
import model.Patient;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class FamilyMemberTable extends SQLDatabase
{
    public FamilyMemberTable()
    {
        super();
        createFamilyTable();
    }

    private void createFamilyTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "FamilyMember", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE FamilyMember " +
                        "(IDNum VARCHAR(255) NOT NULL," +
                        "FamilyRelation VARCHAR(255) NOT NULL," +
                        "PRIMARY KEY (IDNum)," +
                        "FOREIGN KEY(IDNum) REFERENCES client (IDNum)" +
                        "ON DELETE CASCADE ON UPDATE CASCADE);";
                statement.executeUpdate(query);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public boolean addFamily(FamilyMember user)
    {
        try
        {
            String query = "INSERT INTO FamilyMember (IDNum, FamilyRelation)" +
                    "VALUES ((SELECT IDNum FROM client WHERE IDNum = ?), ?);";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, user.getId().toString());
            pState.setString(2, user.getRelation());
            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<FamilyMember> getFamilyMemberByID(UUID idNum)
    {
        FamilyMember family = null;
        try
        {
            String query = "SELECT * FROM FamilyMember, AppUser WHERE FamilyMember.IDNum = ? AND AppUser.IDNum = FamilyMember.IDNum";
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
                String relation = resultSet.getString("FamilyRelation");
                family = new FamilyMember(id, firstname, lastname, username, password, relation);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Optional.ofNullable(family);
    }


}
