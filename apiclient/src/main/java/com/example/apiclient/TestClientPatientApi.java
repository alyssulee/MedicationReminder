package com.example.apiclient;

import com.example.apiabstractions.PatientApi;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import model.Appointment;
import model.BloodPressure;
import model.Doctor;
import model.Dose;
import model.LoginCredentials;
import model.Medication;
import model.PatientMeasurement;

public class TestClientPatientApi {
    public static void main(String[] args) {
        LoginCredentials credentials = new LoginCredentials("myuser", "mypass");
        PatientApi api = ClientPatientApi.createOrThrow(
                "http://40.86.216.110:4567/",
                credentials);

        List<Appointment> appointments = api.getUpcomingAppointments();
        int currentStreak = api.getCurrentStreak();
        int longestStreak = api.getLongestStreak();
        List<Dose> todaysDoses = api.getTodaysDoses();
        api.confirmDoseTaken(new Dose(UUID.randomUUID(), new Timestamp(0), 2, "medid", UUID.randomUUID(), UUID.randomUUID()));
        api.markDoseUntaken(new Dose(UUID.randomUUID(), new Timestamp(0), 2, "medid", UUID.randomUUID(), UUID.randomUUID()));
        List<PatientMeasurement> measurements = api.getAllMeasurements();
        api.addMeasurement(new PatientMeasurement(new BloodPressure(6, 5), 3, new Timestamp(0)));
        Doctor doctor = api.getDoctor(UUID.randomUUID());
        Medication medication = api.getMedication("medid");
    }
}
