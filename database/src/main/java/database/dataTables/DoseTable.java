package database.dataTables;

import database.SQLDatabase;

import java.sql.*;
import java.time.Instant;

import model.*;

public class DoseTable extends SQLDatabase
{
    public DoseTable()
    {
        super();
        createDoseTable();
    }

    private void createDoseTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "Dose", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE Dose " +
                        "(PrescriptionID VARCHAR(255) NOT NULL," +
                        "DosageTime TIMESTAMP NOT NULL," +
                        "AmountPerDose DOUBLE," +
                        "MedID VARCHAR(255),"+
                        "ConfirmerID VARCHAR(255),"+
                        "PatientID VARCHAR(255),"+
                        "PRIMARY KEY (PrescriptionID, DosageTime)," +
                        "FOREIGN KEY (MedID) REFERENCES Medication(MedID)" +
                        "ON DELETE CASCADE ON UPDATE CASCADE," +
                        "FOREIGN KEY (ConfirmerID) REFERENCES Client (IDNum)" +
                        "ON DELETE SET NULL ON UPDATE CASCADE," +
                        "FOREIGN KEY (PatientID) REFERENCES Patient (IDNum)" +
                        "ON DELETE CASCADE ON UPDATE CASCADE);";
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO: Add, remove, getAll, getDoseByMed...

    /**
     * Adds a dose to the database and automatically increases streak, and decreases remaining amount.
     * @param confirmer
     * @param patient
     * @param prescription
     * @return
     */
    public boolean addDose(Client confirmer, Patient patient, Prescription prescription)
    {
        try
        {
            String query = "INSERT INTO Dose (PrescriptionID, DosageTime, AmountPerDose, MedID, ConfirmerID, PatientID)"+
                    "VALUES ((SELECT PrescriptionID FROM Prescription WHERE PrescriptionID = ?), ?, ?, (SELECT MedID FROM Prescription WHERE MedID = ?),(SELECT Idnum FROM Client WHERE IdNum = ?), (SELECT IdNum FROM Patient WHERE IDNum = ?))";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, prescription.getPrescriptionID().toString());
            pState.setTimestamp(2, Timestamp.from(Instant.now()));
            pState.setDouble(3, prescription.getAmountPerDose());
            pState.setString(4, prescription.getMedication().getMedID());
            pState.setString(5, confirmer.getId().toString());
            pState.setString(6, patient.getId().toString());
            pState.execute();

            PrescriptionTable pTable = new PrescriptionTable();
            pTable.decreaseRemainingAmount(prescription);

            PatientTables patientTable = new PatientTables();
            patientTable.increasePatientStreak(patient);
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
