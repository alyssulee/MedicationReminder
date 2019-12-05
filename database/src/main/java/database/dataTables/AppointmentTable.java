package database.dataTables;

import database.SQLDatabase;
import model.Appointment;
import model.Doctor;
import model.Medication;
import model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class AppointmentTable extends SQLDatabase {
    public AppointmentTable() {
        super();
        createAppointmentTable();
    }

    private void createAppointmentTable() {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "Appointment", null);

            if (rs.next() == false) {
                String query = "CREATE TABLE Appointment " +
                        "(DoctorID VARCHAR(255) NOT NULL," +
                        "PatientID VARCHAR(255) NOT NULL," +
                        "Date DATE," +
                        "Time TIME," +
                        "PRIMARY KEY (DoctorID, PatientID)," +
                        "FOREIGN KEY (DoctorID) REFERENCES Doctor(IDNum)" +
                        "ON DELETE CASCADE ON UPDATE CASCADE," +
                        "FOREIGN KEY (PatientID) REFERENCES Patient(IDNum)" +
                        "ON DELETE CASCADE ON UPDATE CASCADE);";
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO: Add, remove, getAll, getAppointmentBy...
    public boolean addAppointment(Doctor doctor, Patient patient, Date date, Time time) {
        try {
            String query = "INSERT INTO Appointment (DoctorID, PatientID, Date, Time)" +
                    "VALUES ((SELECT IDNum FROM Doctor WHERE IDNUm = ?), (SELECT IDNum FROM Patient WHERE IDNUm = ?), ?, ?);";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, doctor.getId().toString());
            pState.setString(2, patient.getId().toString());
            pState.setDate(3, new java.sql.Date(date.getTime()));
            pState.setTime(4, time);

            pState.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAppointment(Doctor doctor, Patient patient, Date newDate, Time newTime) {
        try {
            String query = "UPDATE Appointment SET Date = ?, Time = ?" +
                    "WHERE DoctorID = ? AND PatientID = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setDate(1, new java.sql.Date(newDate.getTime()));
            pState.setTime(2, newTime);
            pState.setString(3, doctor.getId().toString());
            pState.setString(4, patient.getId().toString());

            pState.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeAppointment(Doctor doctor, Patient patient) {
        try {
            String query = "DELETE FROM Appointment WHERE DoctorID = ? AND PatientID = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, doctor.getId().toString());
            pState.setString(2, patient.getId().toString());

            pState.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Appointment> getPatientsAppointments(UUID patientId) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        try {
            String query = "SELECT * FROM Appointment WHERE PatientID = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, patientId.toString());
            ResultSet resultSet = pState.executeQuery();
            while (resultSet.next()) {
                UUID doctorId = UUID.fromString(resultSet.getString("DoctorID"));
                Date date = resultSet.getDate("Date");
                Time time = resultSet.getTime("Time");
                Appointment appointment = new Appointment(doctorId, patientId, date, time);
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }
}
