package com.example.apiclient;

import com.example.apiabstractions.GeneralApi;
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
import model.UserType;

public class ClientGeneralApi implements GeneralApi {

    private static final String errorMessage = "Request failed in GeneralApi.";
    private Gson gson = new Gson();
    private String hostPath;

    public ClientGeneralApi(String hostPath) {
        this.hostPath = hostPath;
    }

    @Override
    public UserType getUserTypeOrNull(LoginCredentials credentials) {
        try {
            HttpURLConnection connection = openGetConnection(
                    "api/general/getUserTypeOrNull",
                    credentials);
            try (AutoCloseable ignored = connection::disconnect) {
                String response = readToString(connection);
                return gson.fromJson(response, UserType.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(errorMessage);
        }
    }

    private HttpURLConnection openGetConnection(String relativePath, LoginCredentials credentials) throws IOException {
        return openConnection("GET", relativePath, credentials);
    }

    private HttpURLConnection openConnection(String requestMethod, String relativePath, LoginCredentials credentials) throws IOException {
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
