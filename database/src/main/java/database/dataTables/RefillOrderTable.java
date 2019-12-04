package database.dataTables;

import database.SQLDatabase;
import model.Medication;
import model.Patient;
import model.Prescription;
import model.PrescriptionFrequency;
import model.RefillOrder;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

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
                        "PrescriptionID VARCHAR(255) NOT NULL," +
                        "Date DATE," +
                        "PRIMARY KEY (PatientID, OrderNum)," +
                        "FOREIGN KEY (PrescriptionID) REFERENCES Prescription(PrescriptionID)" +
                        "ON DELETE CASCADE ON UPDATE CASCADE,"+
                        "FOREIGN KEY (PatientID) REFERENCES Patient(IDNum)" +
                        "ON DELETE CASCADE ON UPDATE CASCADE);";
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO: Add, remove, getAll, getRefillOrderByPatient...
    public boolean addRefillOrder(Patient patient, RefillOrder order)
    {
        ArrayList<Prescription> prescriptions = order.getPrescriptionsToFill();
        for(Prescription prescription : prescriptions)
        {
            try
            {
                String query = "INSERT INTO RefillOrder (PatientID, OrderNum, PrescriptionID, Date)" +
                        "VALUES ((SELECT IDNum FROM Patient WHERE IDNUm = ?), ?, (SELECT PrescriptionID FROM Prescription WHERE PrescriptionID = ?), ?);";
                PreparedStatement pState = connection.prepareStatement(query);
                pState.setString(1, patient.getId().toString());
                pState.setString(2, order.getOrderID().toString());
                pState.setString(3, prescription.getPrescriptionID().toString());
                pState.setDate(4, new java.sql.Date(new Date().getTime()));
                pState.execute();
            } catch (SQLException e)
            {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public ArrayList<Prescription> getOrderPrescriptions(RefillOrder order)
    {
        ArrayList<Prescription> prescriptionList = new ArrayList<>();

        try
        {
            String query = "SELECT Prescription.* FROM Prescription, RefillOrder WHERE RefillOrder.OrderNum = ? AND Prescription.PrescriptionID = RefillOrder.PrescriptionID";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, order.getOrderID().toString());
            resultSet = pState.executeQuery();
            while(resultSet.next())
            {
                UUID prescriptionID = UUID.fromString(resultSet.getString("PrescriptionID"));
                String medID = resultSet.getString("medID");
                Date prescribedDate = resultSet.getDate("PrescribedDate");
                int baseAmount = resultSet.getInt("BaseAmount");
                PrescriptionFrequency frequency = PrescriptionFrequency.valueOf(resultSet.getString("Frequency"));
                double strength = resultSet.getDouble("Strength");
                int remainingAmount = resultSet.getInt("MedRemaining");
                double amountPerDose = resultSet.getDouble("AmountPerDose");

                Medication m = new Medication(medID);
                MedicationTable medTable = new MedicationTable();
                medTable.getAllMedicationNames(m);

                prescriptionList.add(new Prescription(prescriptionID, m, prescribedDate, baseAmount, amountPerDose, strength, frequency, remainingAmount));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return prescriptionList;
    }

}
