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
                        "DosageTime TIME NOT NULL," +
                        "AmountPerDose DOUBLE," +
                        //"MedID VARCHAR(255),"+
                        "ConfirmerID VARCHAR(255),"+
                        "PatientID VARCHAR(255),"+
                        "PRIMARY KEY (PrescriptionID, DosageTime)," +
                        //"FOREIGN KEY (MedID) REFERENCES Medication(MedID)" +
                        //"ON DELETE CASCADE ON UPDATE CASCADE," +
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

    //Adds a dose to the database
    public boolean addDose(Patient patient, Prescription prescription, Time time)
    {
        try
        {
            String query = "INSERT INTO Dose (PrescriptionID, DosageTime, AmountPerDose, PatientID)"+
                    "VALUES ((SELECT PrescriptionID FROM Prescription WHERE PrescriptionID = ?), ?, ?, (SELECT IdNum FROM Patient WHERE IDNum = ?))";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, prescription.getPrescriptionID().toString());
            pState.setTime(2, time);
            pState.setDouble(3, prescription.getAmountPerDose());
            pState.setString(4, patient.getId().toString());
            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    //Confirms dose was taken, automatically increases streaks and decreases remaining amount
    public boolean confirmDose(Patient patient, Client confirmer, Time dosageTime, Prescription prescription)
    {
        try
        {
            String query = "UPDATE Dose SET ConfirmerID = ? WHERE PrescriptionID = ? AND DosageTime = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, confirmer.getId().toString());
            pState.setString(2, prescription.getPrescriptionID().toString());
            pState.setTime(3, dosageTime);
            pState.execute();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        PrescriptionTable pTable = new PrescriptionTable();
        pTable.decreaseRemainingAmount(prescription);

        PatientTables patientTable = new PatientTables();
        patientTable.increasePatientStreak(patient);
        patientTable.increaseSuccessfulDoses(patient);
        return true;
    }
}
