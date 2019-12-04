package database.dataTables;

import database.SQLDatabase;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import model.*;

public class MonitorsTable extends SQLDatabase
{
    public MonitorsTable()
    {
        super();
        createMonitorTable();
    }

    private void createMonitorTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "Monitors", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE Monitors " +
                        "(DoctorID VARCHAR(255) NOT NULL," +
                        "PatientID VARCHAR(255) NOT NULL,"+
                        "StartDate DATE, EndDate DATE," +
                        "PRIMARY KEY (DoctorID, PatientID)," +
                        "FOREIGN KEY (DoctorID) REFERENCES Doctor(IDNum)"+
                        "ON DELETE CASCADE ON UPDATE CASCADE,"+
                        "FOREIGN KEY (PatientID) REFERENCES Patient(IDNum)"+
                        "ON DELETE CASCADE ON UPDATE CASCADE);";
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addDoctorMonitorsPatient(Doctor doctor, Patient patient, Date startDate, Date endDate)
    {
        try
        {
            String query = "INSERT INTO Monitors (DoctorID, PatientID, StartDate, EndDate)" +
                    "VALUES ((SELECT IDNum FROM Doctor WHERE IDNUm = ?), (SELECT IDNum FROM Patient WHERE IDNUm = ?), ?, ?);";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, doctor.getId().toString());
            pState.setString(2, patient.getId().toString());
            pState.setDate(3, new java.sql.Date(startDate.getTime()));
            pState.setDate(4, new java.sql.Date(endDate.getTime()));

            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Patient> getMonitoredPatientsByDoctor(Doctor doctor)
    {
        ArrayList<Patient> patients = new ArrayList<>();
        try
        {
            String query = "SELECT AppUser.* FROM Monitors, Patient, AppUser WHERE Monitors.PatientID = Patient.IDNum AND Monitors.DoctorID = ? AND AppUser.IDNum = Patient.IDNum";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, doctor.getId().toString());

            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                UUID id = UUID.fromString(resultSet.getString("IDNum"));
                String firstname = resultSet.getString("Fname");
                String lastname = resultSet.getString("Lname");
                String username = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                patients.add(new Patient(id, firstname, lastname, username, password));
            }
            PatientTables pTable = new PatientTables();
            pTable.getAllPatientSymptoms(patients);
            pTable.getAllPatientMeasurement(patients);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return patients;
    }
}
