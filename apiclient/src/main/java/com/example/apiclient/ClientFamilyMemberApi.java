package com.example.apiclient;

import com.example.apiabstractions.FamilyMemberApi;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import model.LoginCredentials;
import model.Patient;

public class ClientFamilyMemberApi implements FamilyMemberApi {

    private static final String errorMessage = "Request failed in FamilyMemberApi.";
    private Gson gson = new Gson();
    private String hostPath;
    private LoginCredentials credentials;

    private ClientFamilyMemberApi() {
    }

    public static ClientFamilyMemberApi createOrThrow(String hostPath, LoginCredentials credentials) {
        if (!hostPath.endsWith("/")) hostPath += "/";
        ClientFamilyMemberApi api = new ClientFamilyMemberApi();
        api.hostPath = hostPath;
        api.credentials = credentials;
        api.validateCredentialsOrThrow();
        return api;
    }

    private void validateCredentialsOrThrow() {
        try {
            HttpURLConnection connection = openGetConnection("api/familyMember/verifyCredentials");
            try (AutoCloseable ignored = connection::disconnect) {
                connection.getResponseCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("Failed to validate family member credentials.");
        }
    }

    @Override
    public List<Patient> getDependantPatients() {
        try {
            HttpURLConnection connection = openGetConnection("api/familyMember/getDependantPatients");
            try (AutoCloseable ignored = connection::disconnect) {
                String response = readToString(connection);
                return Arrays.asList(gson.fromJson(response, Patient[].class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(errorMessage);
        }
    }

    @Override
    public LoginCredentials getPatientCredentials(UUID patientId) {
        try {
            HttpURLConnection connection = openGetConnection("api/familyMember/getPatientCredentials/" + patientId);
            try (AutoCloseable ignored = connection::disconnect) {
                String response = readToString(connection);
                return gson.fromJson(response, LoginCredentials.class);
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
