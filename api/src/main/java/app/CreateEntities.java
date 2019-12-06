package app;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import database.SQLDatabase;
import model.*;
import repository.UserRepository;

public class CreateEntities {


    public static void main(String[] args) throws SQLException {

        DbAccessible dbAccessible = new DbAccessible();
        dbAccessible.dropContents();

        UserRepository userRepository = new UserRepository();
        userRepository.addUser(new Patient(UUID.randomUUID(), "PatientA", "patient", "patienta", "password"), UserType.Patient);
        userRepository.addUser(new Patient(UUID.randomUUID(), "PatientB", "patient", "patientb", "password"), UserType.Patient);
        userRepository.addUser(new FamilyMember(UUID.randomUUID(), "Mother", "patient", "family", "password", "Mother"), UserType.FamilyMember);

        ArrayList<Doctor> doctorList = userRepository.getAllDoctors();
        ArrayList<Patient> patientList = userRepository.getAllPatients();

        userRepository.addAppointment(doctorList.get(0), patientList.get(0), new Date(2020, 3, 4), new Time(4, 4, 4));
        userRepository.addAppointment(doctorList.get(0), patientList.get(0), new Date(2020, 5, 6), new Time(5, 5, 5));

        {
            Prescription prescription = new Prescription(UUID.randomUUID(), "DB00316", new Date(), 10, 1, 3, PrescriptionFrequency.Daily, 10);
            userRepository.addPatientPrescription(patientList.get(0), prescription);
            userRepository.addDose(patientList.get(0), prescription, Timestamp.from(Instant.now()));
        }

        {
            Prescription prescription = new Prescription(UUID.randomUUID(), "DB00945", new Date(), 10, 2, 3, PrescriptionFrequency.Daily, 10);
            userRepository.addPatientPrescription(patientList.get(0), prescription);
            userRepository.addDose(patientList.get(0), prescription, Timestamp.from(Instant.now()));
        }

        {
            Prescription prescription = new Prescription(UUID.randomUUID(), "DB01001", new Date(), 10, 1, 3, PrescriptionFrequency.Daily, 10);
            userRepository.addPatientPrescription(patientList.get(0), prescription);
            userRepository.addDose(patientList.get(0), prescription, Timestamp.from(Instant.now()));
        }
    }
}

class DbAccessible extends SQLDatabase {
    void dropContents() throws SQLException {
        statement.executeUpdate("DELETE FROM Appointment;");
        statement.executeUpdate("DELETE FROM AppUser;");
        statement.executeUpdate("DELETE FROM Client;");
        statement.executeUpdate("DELETE FROM Doctor;");
        statement.executeUpdate("DELETE FROM Dose;");
        statement.executeUpdate("DELETE FROM FamilyMember;");
        statement.executeUpdate("DELETE FROM Fills;");
        statement.executeUpdate("DELETE FROM Medication;");
        statement.executeUpdate("DELETE FROM MedName;");
        statement.executeUpdate("DELETE FROM Monitors;");
        statement.executeUpdate("DELETE FROM Patient;");
        statement.executeUpdate("DELETE FROM PatientSymptom;");
        statement.executeUpdate("DELETE FROM PatientMeasurement;");
        statement.executeUpdate("DELETE FROM Pharmacist;");
        statement.executeUpdate("DELETE FROM Prescription;");
        statement.executeUpdate("DELETE FROM Receives;");
        statement.executeUpdate("DELETE FROM RefillOrder;");
        statement.executeUpdate("DELETE FROM ViewsData");
    }

}
