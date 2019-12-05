package app;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import model.*;
import repository.UserRepository;

public class CreateEntities {


    public static void main(String[] args){
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
            userRepository.addDose(patientList.get(0), prescription, new Time(0));
        }

        {
            Prescription prescription = new Prescription(UUID.randomUUID(), "DB00945", new Date(), 10, 2, 3, PrescriptionFrequency.Daily, 10);
            userRepository.addPatientPrescription(patientList.get(0), prescription);
            userRepository.addDose(patientList.get(0), prescription, new Time(0));
        }

        {
            Prescription prescription = new Prescription(UUID.randomUUID(), "DB01001", new Date(), 10, 1, 3, PrescriptionFrequency.Daily, 10);
            userRepository.addPatientPrescription(patientList.get(0), prescription);
            userRepository.addDose(patientList.get(0), prescription, new Time(0));
        }
    }
}
