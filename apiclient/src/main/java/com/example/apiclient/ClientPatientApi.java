package com.example.apiclient;

import com.example.apiabstractions.PatientApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import model.Appointment;
import model.Doctor;
import model.Dose;
import model.LoginCredentials;
import model.Medication;
import model.PatientMeasurement;

public class ClientPatientApi implements PatientApi {

    private static final String errorMessage = "Request failed in PatientApi.";
    private String hostPath;
    private LoginCredentials credentials;

    private ClientPatientApi() {
    }

    public static ClientPatientApi createOrThrow(String hostPath, LoginCredentials credentials) {
        if (!hostPath.endsWith("/")) hostPath += "/";
        ClientPatientApi api = new ClientPatientApi();
        api.hostPath = hostPath;
        api.credentials = credentials;
        api.validateCredentialsOrThrow(credentials);
        return api;
    }

    private void validateCredentialsOrThrow(LoginCredentials credentials) {
        try {
            HttpURLConnection connection = openGetConnection("patient/api/verifyCredentials");
            try (AutoCloseable ignored = connection::disconnect) {
                String response = readToString(connection);
                // Todo: Check for 500 error
                int a = 4;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("Failed to validate patient credentials.");
        }
    }

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
    public List<Dose> getTodaysDoses() {
        return null;
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

    private HttpURLConnection openGetConnection(String relativePath) throws IOException {
        return openConnection("GET", relativePath);
    }

    private HttpURLConnection openPostConnection(String relativePath) throws IOException {
        HttpURLConnection connection = openConnection("GET", relativePath);
        connection.setRequestProperty("Content-Type", "application/json");
        return connection;
    }

    private HttpURLConnection openConnection(String requestMethod, String relativePath) throws IOException {
        URL url = new URL(hostPath + relativePath);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);
        connection.setRequestProperty("username", credentials.getUsername());
        connection.setRequestProperty("password", credentials.getPassword());
        return connection;
    }

    private String readToString(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }
}
