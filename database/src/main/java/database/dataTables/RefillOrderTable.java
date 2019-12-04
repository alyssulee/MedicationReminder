package database.dataTables;

import database.SQLDatabase;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RefillOrderTable extends SQLDatabase
{
    public RefillOrderTable()
    {
        super();
        createRefillOrderTable();
    }

    private void createRefillOrderTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "RefillOrder", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE RefillOrder " +
                        "(PatientID VARCHAR(255) NOT NULL," +
                        "OrderNum VARCHAR(255) NOT NULL," +
                        "Date DATE," +
                        "PRIMARY KEY (PatientID, OrderNum)," +
                        "FOREIGN KEY (PatientID) REFERENCES Patient(IDNum)" +
                        "ON DELETE CASCADE ON UPDATE CASCADE);";
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO: Add, remove, getAll, getRefillOrderByPatient...


}
