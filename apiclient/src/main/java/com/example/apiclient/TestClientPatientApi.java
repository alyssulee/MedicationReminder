package com.example.apiclient;

        import com.example.apiabstractions.PatientApi;

        import model.LoginCredentials;

public class TestClientPatientApi {
    public static void main(String[] args) {
        LoginCredentials credentials = new LoginCredentials("myuser", "mypass");
        PatientApi api = ClientPatientApi.createOrThrow(
                "http://40.86.216.110:4567/",
                credentials);
    }
}
