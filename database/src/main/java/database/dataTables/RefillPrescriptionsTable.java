package database.dataTables;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import database.SQLDatabase;
import model.Patient;
import model.Prescription;
import model.RefillOrder;

public class RefillPrescriptionsTable extends SQLDatabase
{
    public RefillPrescriptionsTable()
    {
        super();
        createRefillPrescriptionsTable();
    }

    private void createRefillPrescriptionsTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "RefillPrescription", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE RefillPrescription " +
                        "(PatientID VARCHAR(255) NOT NULL," +
                        "OrderNum VARCHAR(255) NOT NULL," +
                        "PrescriptionID VARCHAR(255) NOT NULL," +
                        "PRIMARY KEY (PatientID, OrderNum, PrescriptionID)," +
                        "FOREIGN KEY (PrescriptionID) REFERENCES Prescription(PrescriptionID)" +
                        "ON DELETE CASCADE ON UPDATE CASCADE,"+
                        "FOREIGN KEY (PatientID, OrderNum) REFERENCES RefillOrder(PatientID, OrderNum)" +
                        "ON DELETE CASCADE ON UPDATE CASCADE);";
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addPrescriptionOrder(Patient patient, Prescription prescription, RefillOrder order)
    {
        try
        {
            String query = "INSERT INTO RefillPrescription (OrderNum, PatientID, PrescriptionID)" +
                    "VALUES ((SELECT OrderNum FROM RefillOrder WHERE OrderNum = ?),(SELECT PatientID FROM RefillOrder WHERE PatientID = ?), (SELECT PrescriptionID FROM Prescription WHERE PrescriptionID = ?));";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, order.getOrderID().toString());
            pState.setString(2, patient.getId().toString());
            pState.setString(3, prescription.getPrescriptionID().toString());
            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
