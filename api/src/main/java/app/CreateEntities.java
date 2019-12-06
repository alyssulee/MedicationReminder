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
import repository.MedRepository;
import repository.UserRepository;

public class CreateEntities {


    public static void main(String[] args) throws SQLException {

        DbAccessible dbAccessible = new DbAccessible();
        dbAccessible.dropContents();

        MedRepository medRepo = new MedRepository();
        UserRepository userRepository = new UserRepository();

        userRepository.addUser(new Patient(UUID.randomUUID(), "PatientA", "patient", "patienta", "password"));
        userRepository.addUser(new Patient(UUID.randomUUID(), "PatientB", "patient", "patientb", "password"));
        userRepository.addUser(new FamilyMember(UUID.randomUUID(), "Mother", "patient", "family", "password", "Mother"));

        userRepository.addUser(new Doctor(UUID.randomUUID(), "DoctorA", "doctor", "doctora", "password"));
        userRepository.addUser(new Doctor(UUID.randomUUID(), "DoctorB", "doctor", "doctorb", "pbssword"));

        ArrayList<Doctor> doctorList = userRepository.getAllDoctors();
        ArrayList<Patient> patientList = userRepository.getAllPatients();

        userRepository.addAppointment(doctorList.get(0), patientList.get(0), new Date(2020, 3, 4), new Time(4, 4, 4));
        userRepository.addAppointment(doctorList.get(1), patientList.get(0), new Date(2020, 5, 6), new Time(5, 5, 5));

        {
            Prescription p = new Prescription(UUID.randomUUID(), new Medication("DB00005"), new Date(), 15, 2, 0.9, PrescriptionFrequency.BID, 15);
            Prescription p2 = new Prescription(UUID.randomUUID(), new Medication("DB01255"), new Date(), 10, 1, 1, PrescriptionFrequency.Daily,  15);

            userRepository.addPatientPrescription(patientList.get(0), p);
            userRepository.addPatientPrescription(patientList.get(0), p2);
        }

        {
            Prescription prescription = new Prescription(UUID.randomUUID(), "DB01255", new Date(), 10, 1, 3, PrescriptionFrequency.Daily, 10);
            userRepository.addPatientPrescription(patientList.get(0), prescription);
            userRepository.addDose(patientList.get(0), prescription, new Time(0), Date.from(Instant.now()));
        }

        {
            Prescription prescription = new Prescription(UUID.randomUUID(), "DB00005", new Date(), 10, 2, 3, PrescriptionFrequency.Daily, 10);
            userRepository.addPatientPrescription(patientList.get(0), prescription);
            userRepository.addDose(patientList.get(0), prescription, new Time(0), Date.from(Instant.now()));
        }

        {
            Prescription prescription = new Prescription(UUID.randomUUID(), "DB01001", new Date(), 10, 1, 3, PrescriptionFrequency.Daily, 10);
            userRepository.addPatientPrescription(patientList.get(0), prescription);
            userRepository.addDose(patientList.get(0), prescription, new Time(0), Date.from(Instant.now()));
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
