package database.dataTables;

import database.SQLDatabase;
import model.Pharmacist;
import model.RefillOrder;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
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
    public boolean addPharmacistReceivesOrder(Pharmacist pharmacist, RefillOrder order)
    {
        try
        {
            String query = "INSERT INTO Fills (PharmacistID, OrderNum)" +
                    "VALUES ((SELECT IDNum FROM Pharmacist WHERE IDNUm = ?), (SELECT OrderNum FROM RefillOrder WHERE OrderNum = ?));";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, pharmacist.getId().toString());
            pState.setString(2, order.getOrderID().toString());
            pState.execute();


            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
