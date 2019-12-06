package database.dataTables;

import database.SQLDatabase;
import model.*;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class PrescriptionTable extends SQLDatabase
{
    public PrescriptionTable()
    {
        super();
        createPrescriptionTable();
    }

    private void createPrescriptionTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "Prescription", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE Prescription " +
                        "(PrescriptionID VARCHAR(255) NOT NULL," +
                        "PrescribedDate DATE," +
                        "BaseAmount INT," +
                        "AmountPerDose INT," +
                        "MedRemaining INT," +
                        "Frequency VARCHAR(255)," +
                        "Strength DOUBLE,"+
                        "MedID VARCHAR(255),"+
                        "PatientID VARCHAR(255),"+
                        "PRIMARY KEY (PrescriptionID)," +
                        "FOREIGN KEY (MedID) REFERENCES Medication(MedID)" +
                        "ON DELETE CASCADE ON UPDATE CASCADE,"+
                        "FOREIGN KEY (PatientID) REFERENCES PATIENT(IDNum)" +
                        "ON DELETE CASCADE ON UPDATE CASCADE);";

                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addPatientPrescription(Patient patient, Prescription prescription)
    {
        try
        {
            String query = "INSERT INTO Prescription (PrescriptionID, PrescribedDate, BaseAmount, AmountPerDose, MedRemaining, Frequency, Strength, MedID, PatientID)"+
                            "VALUES (?,?,?,?,?,?,?,(SELECT MedID FROM Medication WHERE MedID = ?), (SELECT IDNum FROM Patient WHERE IDNum = ?))";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, prescription.getPrescriptionID().toString());
            pState.setDate(2, new java.sql.Date(prescription.getPrescribedDate().getTime()));
            pState.setInt(3, prescription.getBaseAmount());
            pState.setInt(4, prescription.getAmountPerDose());
            pState.setInt(5, prescription.getRemainingAmount());
            pState.setString(6, prescription.getFrequency().toString());
            pState.setDouble(7, prescription.getStrength());
            pState.setString(8, prescription.getMedicationId());
            pState.setString(9, patient.getId().toString());
            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Prescription> getAllPrescriptionsByPatient(Patient patient)
    {
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        try
        {
            String query = "SELECT Prescription.* FROM Prescription WHERE PatientID = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, patient.getId().toString());
            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                UUID prescriptionID = UUID.fromString(resultSet.getString("PrescriptionID"));
                String medID = resultSet.getString("medID");
                Date prescribedDate = resultSet.getDate("PrescribedDate");
                int baseAmount = resultSet.getInt("BaseAmount");
                PrescriptionFrequency frequency = PrescriptionFrequency.valueOf(resultSet.getString("Frequency"));
                double strength = resultSet.getDouble("Strength");
                int remainingAmount = resultSet.getInt("MedRemaining");
                int amountPerDose = resultSet.getInt("AmountPerDose");

                Medication m = new Medication(medID);
                MedicationTable medTable = new MedicationTable();
                medTable.getAllMedicationNames(m);

                prescriptions.add(new Prescription(prescriptionID, m, prescribedDate, baseAmount, amountPerDose, strength, frequency, remainingAmount));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return prescriptions;
    }

    public Prescription getPrescriptionByID(UUID pID)
    {
        Prescription prescription = null;
        try
        {
            String query = "SELECT Prescription.* FROM Prescription WHERE PrescriptionID = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, pID.toString());

            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                UUID prescriptionID = UUID.fromString(resultSet.getString("PrescriptionID"));
                String medID = resultSet.getString("medID");
                Date prescribedDate = resultSet.getDate("PrescribedDate");
                int baseAmount = resultSet.getInt("BaseAmount");
                PrescriptionFrequency frequency = PrescriptionFrequency.valueOf(resultSet.getString("Frequency"));
                double strength = resultSet.getDouble("Strength");
                int remainingAmount = resultSet.getInt("MedRemaining");
                int amountPerDose = resultSet.getInt("AmountPerDose");

                Medication m = new Medication(medID);
                MedicationTable medTable = new MedicationTable();
                medTable.getAllMedicationNames(m);

                prescription = new Prescription(prescriptionID, m, prescribedDate, baseAmount, amountPerDose, strength, frequency, remainingAmount);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return (prescription);

    }


    public ArrayList<Prescription> getPatientPrescriptionsByMedication(Patient patient, Medication med)
    {
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        try
        {
            String query = "SELECT Prescription.* FROM Prescription WHERE PatientID = ? AND MedID = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, patient.getId().toString());
            pState.setString(2, med.getMedID().toString());

            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                UUID prescriptionID = UUID.fromString(resultSet.getString("PrescriptionID"));
                String medID = resultSet.getString("medID");
                Date prescribedDate = resultSet.getDate("PrescribedDate");
                int baseAmount = resultSet.getInt("BaseAmount");
                PrescriptionFrequency frequency = PrescriptionFrequency.valueOf(resultSet.getString("Frequency"));
                double strength = resultSet.getDouble("Strength");
                int remainingAmount = resultSet.getInt("MedRemaining");
                int amountPerDose = resultSet.getInt("AmountPerDose");

                Medication m = new Medication(medID);
                MedicationTable medTable = new MedicationTable();
                medTable.getAllMedicationNames(m);

                prescriptions.add(new Prescription(prescriptionID, m, prescribedDate, baseAmount, amountPerDose, strength, frequency, remainingAmount));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return prescriptions;
    }

    public boolean decreaseRemainingAmount(UUID prescriptionId)
    {
        try
        {
            String query = "UPDATE Prescription SET MedRemaining = MedRemaining - 1 WHERE PrescriptionID = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, prescriptionId.toString());
            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean refillPrescription(Prescription prescription)
    {
        try
        {
            String query = "UPDATE Prescription SET MedRemaining = BaseAmount WHERE PrescriptionID = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, prescription.getPrescriptionID().toString());
            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
