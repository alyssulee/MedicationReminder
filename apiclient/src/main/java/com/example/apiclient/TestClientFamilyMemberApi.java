package com.example.apiclient;

import com.example.apiabstractions.FamilyMemberApi;
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
import model.Patient;
import model.PatientMeasurement;

public class TestClientFamilyMemberApi {
    public static void main(String[] args) {
        LoginCredentials credentials = new LoginCredentials("myuser", "mypass");
        FamilyMemberApi api = ClientFamilyMemberApi.createOrThrow(
                "http://40.86.216.110:4567/",
                credentials);

        List<Patient> patients = api.getDependantPatients();
        LoginCredentials patientCredentials = api.getPatientCredentias(UUID.randomUUID());
    }
}
