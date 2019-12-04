package database.dataTables;

import database.SQLDatabase;
import model.FamilyMember;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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


}
