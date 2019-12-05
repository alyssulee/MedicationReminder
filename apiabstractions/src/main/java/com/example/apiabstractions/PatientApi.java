package com.example.apiabstractions;

import java.util.List;

import model.Appointment;
import model.Dose;
import model.PatientMeasurement;

public interface PatientApi {
    List<Appointment> getUpcomingAppointments();
    int getCurrentStreak();
    int getLongestStreak();
    List<Dose> getTodaysDoses();
    void confirmDoseTaken(Dose does);
    void markDoseUntaken(Dose dose);
    List<PatientMeasurement> getAllMeasurements();
    void addMeasurement(PatientMeasurement measurement);
}
