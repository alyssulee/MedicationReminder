package com.example.apiabstractions;

import java.util.List;
import java.util.UUID;

import model.LoginCredentials;
import model.Patient;

public interface FamilyMemberApi {
    List<Patient> getDependantPatients();
    LoginCredentials getPatientCredentials(UUID patientId);
}
