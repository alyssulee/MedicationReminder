package database.dataTables;

import database.SQLDatabase;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

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
                        "DosageDate DATE NOT NULL," +
                        "TimeTaken TIME," +
                        "AmountPerDose INT," +
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
    public boolean addDose(Patient patient, Prescription prescription, Time dosageTime, Date date)
    {
        try
        {
            String query = "INSERT INTO Dose (PrescriptionID, DosageTime, DosageDate, AmountPerDose, PatientID)"+
                    "VALUES ((SELECT PrescriptionID FROM Prescription WHERE PrescriptionID = ?), ?, ?, ?, (SELECT IdNum FROM Patient WHERE IDNum = ?))";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, prescription.getPrescriptionID().toString());
            pState.setTime(2, dosageTime);
            pState.setDate(3, new java.sql.Date(date.getTime()));
            pState.setInt(4, prescription.getAmountPerDose());
            pState.setString(5, patient.getId().toString());
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
            String query = "UPDATE Dose SET ConfirmerID = ? WHERE PrescriptionID = ? AND DosageDate = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, confirmer.getId().toString());
            pState.setString(2, prescription.getPrescriptionID().toString());
            pState.setDate(3, new java.sql.Date(new Date().getTime()));
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

    public ArrayList<Dose> getPatientsDosesOn(UUID patientId, Date date){
        ArrayList<Dose> doses = new ArrayList<>();
        try {
            String query = "SELECT * FROM Dose, Prescription WHERE Dose.PatientID = ? AND DosageDate = ? AND Prescription.PatientID = Dose.PatientID  AND Prescription.PrescriptionID = Dose.PrescriptionID";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, patientId.toString());
            pState.setDate(2, new java.sql.Date(date.getTime()));
            ResultSet resultSet = pState.executeQuery();
            while (resultSet.next()) {
                UUID prescriptionId = UUID.fromString(resultSet.getString("PrescriptionID"));
                Timestamp dosageTime = resultSet.getTimestamp("DosageTime");
                int amountPerDose = resultSet.getInt("AmountPerDose");
                String medId = resultSet.getString("MedID");

                Dose dose = new Dose(prescriptionId, dosageTime, amountPerDose, medId, patientId);
                String confirmedId = resultSet.getString("ConfirmerID");
                if (confirmedId != null) dose.setConfirmerId(UUID.fromString(confirmedId));
                doses.add(dose);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doses;
    }
}
