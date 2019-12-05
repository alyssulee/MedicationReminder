package com.example.medicationreminderapp;

import com.example.apiabstractions.PatientApi;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.Appointment;
import model.Doctor;
import model.Dose;
import model.Medication;
import model.PatientMeasurement;

public class TestPatientApi implements PatientApi {
    @Override
    public List<Appointment> getUpcomingAppointments() {
        return null;
    }

    @Override
    public int getCurrentStreak() {
        return 0;
    }

    @Override
    public int getLongestStreak() {
        return 0;
    }

    @Override
    public ArrayList<Dose> getTodaysDoses() {
        ArrayList<Dose> doses = new ArrayList<>();
        doses.add(new Dose(UUID.randomUUID(), new Timestamp(0), 1, "medidhere1", UUID.randomUUID(), UUID.randomUUID()));
        doses.add(new Dose(UUID.randomUUID(), new Timestamp(0), 2, "medidhere2", UUID.randomUUID(), UUID.randomUUID()));
        doses.add(new Dose(UUID.randomUUID(), new Timestamp(0), 1, "medidhere1", UUID.randomUUID(), UUID.randomUUID()));
        return doses;
    }

    @Override
    public void confirmDoseTaken(Dose dose) {

    }

    @Override
    public void markDoseUntaken(Dose dose) {

    }

    @Override
    public List<PatientMeasurement> getAllMeasurements() {
        return null;
    }

    @Override
    public void addMeasurement(PatientMeasurement measurement) {

    }

    @Override
    public Doctor getDoctor(UUID doctorId) {
        return null;
    }

    @Override
    public Medication getMedication(String medId) {
        return null;
    }
}
