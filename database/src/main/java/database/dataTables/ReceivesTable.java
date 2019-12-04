package database.dataTables;

import database.SQLDatabase;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReceivesTable extends SQLDatabase
{
    public ReceivesTable()
    {
        super();
        createReceivesTable();
    }

    private void createReceivesTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "Receives", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE Receives " +
                        "(PharmacistID VARCHAR(255) NOT NULL," +
                        "PatientID VARCHAR(255) NOT NULL,"+
                        "OrderNum VARCHAR(255) NOT NULL,"+
                        "PRIMARY KEY (PharmacistID, PatientID, OrderNum)," +
                        "FOREIGN KEY (PharmacistID) REFERENCES Pharmacist(IDNum)"+
                        "ON DELETE CASCADE ON UPDATE CASCADE,"+
                        "FOREIGN KEY (PatientID, OrderNum) REFERENCES RefillOrder(PatientID, OrderNum)"+
                        "ON DELETE CASCADE ON UPDATE CASCADE);";
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO: Add, remove, getAll, getReceivesBy...


}
