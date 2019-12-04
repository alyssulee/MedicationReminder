package database.dataTables;

import database.SQLDatabase;
import model.Pharmacist;
import model.Prescription;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FillsTable extends SQLDatabase
{
    public FillsTable()
    {
        super();
        createFillsTable();
    }

    private void createFillsTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "Fills", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE Fills " +
                        "(PharmacistID VARCHAR(255) NOT NULL," +
                        "PrescriptionID VARCHAR(255) NOT NULL,"+
                        "PRIMARY KEY (PharmacistID, PrescriptionID)," +
                        "FOREIGN KEY (PharmacistID) REFERENCES Pharmacist(IDNum)"+
                        "ON DELETE CASCADE ON UPDATE CASCADE,"+
                        "FOREIGN KEY (PrescriptionID) REFERENCES Prescription(PrescriptionID)"+
                        "ON DELETE CASCADE ON UPDATE CASCADE);";
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO: Add, remove, getAll, getFills...
    public boolean addPharmacistFillsPrescription(Pharmacist pharmacist, Prescription prescription)
    {
        try
        {
            String query = "INSERT INTO Fills (PharmacistID, PrescriptionID)" +
                    "VALUES ((SELECT IDNum FROM Pharmacist WHERE IDNUm = ?), (SELECT PrescriptionID FROM Prescription WHERE PrescriptionID = ?));";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, pharmacist.getId().toString());
            pState.setString(2, prescription.getPrescriptionID().toString());
            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
