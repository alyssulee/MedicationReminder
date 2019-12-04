package database.dataTables;

import database.SQLDatabase;
import model.Doctor;
import model.Patient;

import java.sql.*;
import java.util.Date;
import java.util.UUID;

public class AppointmentTable extends SQLDatabase
{
    public AppointmentTable()
    {
        super();
        createAppointmentTable();
    }

    private void createAppointmentTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "Appointment", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE Appointment " +
                        "(DoctorID VARCHAR(255) NOT NULL," +
                        "PatientID VARCHAR(255) NOT NULL,"+
                        "Date DATE," +
                        "Time TIME,"+
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

    //TODO: Add, remove, getAll, getAppointmentBy...
    public boolean addAppointment(Doctor doctor, Patient patient, Date date, Time time)
    {
        try
        {
            String query = "INSERT INTO Appointment (DoctorID, PatientID, Date, Time)" +
                    "VALUES ((SELECT IDNum FROM Doctor WHERE IDNUm = ?), (SELECT IDNum FROM Patient WHERE IDNUm = ?), ?, ?);";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, doctor.getId().toString());
            pState.setString(2, patient.getId().toString());
            pState.setDate(3, new java.sql.Date(date.getTime()));
            pState.setTime(4, time);

            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAppointment(Doctor doctor, Patient patient, Date newDate, Time newTime)
    {
        try
        {
            String query = "UPDATE Appointment SET Date = ?, Time = ?" +
                    "WHERE DoctorID = ? AND PatientID = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setDate(1, new java.sql.Date(newDate.getTime()));
            pState.setTime(2, newTime);
            pState.setString(3, doctor.getId().toString());
            pState.setString(4, patient.getId().toString());

            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
