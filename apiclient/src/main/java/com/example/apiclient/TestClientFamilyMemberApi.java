package com.example.apiclient;

import com.example.apiabstractions.FamilyMemberApi;

import java.util.List;
import java.util.UUID;

import model.LoginCredentials;
import model.Patient;

public class TestClientFamilyMemberApi {
    public static void main(String[] args) {
        LoginCredentials credentials = new LoginCredentials("myuser", "mypass");
        FamilyMemberApi api = ClientFamilyMemberApi.createOrThrow(
                "http://40.86.216.110:4567/",
                credentials);

        List<Patient> patients = api.getDependantPatients();
        LoginCredentials patientCredentials = api.getPatientCredentials(UUID.randomUUID());
    }
}
