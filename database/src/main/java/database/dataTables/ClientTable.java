package database.dataTables;

import database.SQLDatabase;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ClientTable extends SQLDatabase
{
    public ClientTable()
    {
        super();
        createClientTable();
    }

    private void createClientTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "Client", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE Client " +
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

    public boolean addClient(UUID idNum)
    {
        try
        {
            String query = "INSERT INTO client (IDNum)" +
                            "VALUES ((SELECT IDNum FROM appuser WHERE IDNum = ?));";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, idNum.toString());
            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
