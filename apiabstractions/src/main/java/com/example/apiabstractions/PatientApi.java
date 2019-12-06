package com.example.apiabstractions;

import java.util.List;
import java.util.UUID;

import model.Appointment;
import model.Doctor;
import model.Dose;
import model.Medication;
import model.PatientMeasurement;

public interface PatientApi {
    List<Appointment> getUpcomingAppointments();
    int getCurrentStreak();
    int getLongestStreak();
    List<Dose> getTodaysDoses();
    void confirmDoseTaken(Dose dose);
    void markDoseUntaken(Dose dose);
    List<PatientMeasurement> getAllMeasurements();
    void addMeasurement(PatientMeasurement measurement);
    Doctor getDoctor(UUID doctorId);
    Medication getMedication(String medId);
//    List<Doctor> getALlDoctors();
//    void addAppointment(Appointment appointment);
}
