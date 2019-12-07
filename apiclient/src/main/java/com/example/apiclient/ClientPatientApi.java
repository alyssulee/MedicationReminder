package com.example.apiclient;

import com.example.apiabstractions.PatientApi;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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
    private Gson gson = new Gson();
    private String hostPath;
    private LoginCredentials credentials;

    private ClientPatientApi() {
    }

    public static ClientPatientApi createOrThrow(String hostPath, LoginCredentials credentials) {
        if (!hostPath.endsWith("/")) hostPath += "/";
        ClientPatientApi api = new ClientPatientApi();
        api.hostPath = hostPath;
        api.credentials = credentials;
        api.validateCredentialsOrThrow();
        return api;
    }

    private void validateCredentialsOrThrow() {
        try {

            HttpURLConnection connection = openGetConnection("api/patient/verifyCredentials");
            try (AutoCloseable ignored = connection::disconnect) {
                readToString(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("Failed to validate patient credentials.");
        }
    }

    @Override
    public List<Appointment> getUpcomingAppointments() {
        try {
            HttpURLConnection connection = openGetConnection("api/patient/getUpcomingAppointments");
            try (AutoCloseable ignored = connection::disconnect) {
                String response = readToString(connection);
                return Arrays.asList(gson.fromJson(response, Appointment[].class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(errorMessage);
        }
    }

    @Override
    public int getCurrentStreak() {
        try {
            HttpURLConnection connection = openGetConnection("api/patient/getCurrentStreak");
            try (AutoCloseable ignored = connection::disconnect) {
                String response = readToString(connection);
                return gson.fromJson(response, int.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(errorMessage);
        }
    }

    @Override
    public int getLongestStreak() {
        try {
            HttpURLConnection connection = openGetConnection("api/patient/getLongestStreak");
            try (AutoCloseable ignored = connection::disconnect) {
                String response = readToString(connection);
                return gson.fromJson(response, int.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(errorMessage);
        }
    }

    @Override
    public List<Dose> getTodaysDoses() {
        try {
            HttpURLConnection connection = openGetConnection("api/patient/getTodaysDoses");
            try (AutoCloseable ignored = connection::disconnect) {
                String response = readToString(connection);
                return Arrays.asList(gson.fromJson(response, Dose[].class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(errorMessage);
        }
    }

    @Override
    public void confirmDoseTaken(Dose dose) {
        try {
            HttpURLConnection connection = openPostConnection("api/patient/confirmDoseTaken");
            writeToBody(connection, gson.toJson(dose));
            try (AutoCloseable ignored = connection::disconnect) {
                readToString(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(errorMessage);
        }
    }

    @Override
    public void markDoseUntaken(Dose dose) {
        try {
            HttpURLConnection connection = openPostConnection("api/patient/markDoseUntaken");
            writeToBody(connection, gson.toJson(dose));
            try (AutoCloseable ignored = connection::disconnect) {
                readToString(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(errorMessage);
        }

    }

    @Override
    public List<PatientMeasurement> getAllMeasurements() {
        try {
            HttpURLConnection connection = openGetConnection("api/patient/getAllMeasurements");
            try (AutoCloseable ignored = connection::disconnect) {
                String response = readToString(connection);
                return Arrays.asList(gson.fromJson(response, PatientMeasurement[].class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(errorMessage);
        }
    }

    @Override
    public void addMeasurement(PatientMeasurement measurement) {
        try {
            HttpURLConnection connection = openPostConnection("api/patient/addMeasurement");
            writeToBody(connection, gson.toJson(measurement));
            try (AutoCloseable ignored = connection::disconnect) {
                readToString(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(errorMessage);
        }

    }

    @Override
    public Doctor getDoctor(UUID doctorId) {
        try {
            HttpURLConnection connection = openGetConnection("api/patient/getDoctor/" + doctorId.toString());
            try (AutoCloseable ignored = connection::disconnect) {
                String response = readToString(connection);
                return gson.fromJson(response, Doctor.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(errorMessage);
        }
    }

    @Override
    public Medication getMedication(String medId) {
        try {
            HttpURLConnection connection = openGetConnection("api/patient/getMedication/" + medId);
            try (AutoCloseable ignored = connection::disconnect) {
                String response = readToString(connection);
                return gson.fromJson(response, Medication.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(errorMessage);
        }

    }

    private HttpURLConnection openGetConnection(String relativePath) throws IOException {
        return openConnection("GET", relativePath);
    }

    private HttpURLConnection openPostConnection(String relativePath) throws IOException {
        HttpURLConnection connection = openConnection("POST", relativePath);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
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
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) response.append(responseLine.trim());
            return response.toString();
        }
    }

    private void writeToBody(HttpURLConnection connection, String body) throws IOException {
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = body.getBytes();
            os.write(input, 0, input.length);
        }
    }
}
