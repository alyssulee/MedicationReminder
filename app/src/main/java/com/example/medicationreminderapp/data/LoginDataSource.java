package com.example.medicationreminderapp.data;

import com.example.apiclient.ClientPatientApi;
import com.example.medicationreminderapp.HackerMan;
import com.example.medicationreminderapp.data.model.LoggedInUser;

import java.io.IOException;

import model.LoginCredentials;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            HackerMan.patientApi = ClientPatientApi.createOrThrow("http://40.86.216.110:4567/", new LoginCredentials(username, password));
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
