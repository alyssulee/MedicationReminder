package com.example.medicationreminderapp.data;

import android.os.StrictMode;

import com.example.apiabstractions.GeneralApi;
import com.example.apiclient.ClientFamilyMemberApi;
import com.example.apiclient.ClientGeneralApi;
import com.example.apiclient.ClientPatientApi;
import com.example.medicationreminderapp.DoseFinder;
import com.example.medicationreminderapp.data.model.LoggedInUser;

import java.io.IOException;

import model.LoginCredentials;
import model.UserType;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");

            LoginCredentials credentials = new LoginCredentials(username, password);
            UserType userTypeOrNull = new ClientGeneralApi("http://104.210.55.244:4567/").getUserTypeOrNull(credentials);
            DoseFinder.userType = userTypeOrNull;
            if (userTypeOrNull == UserType.Patient){
                DoseFinder.patientApi = ClientPatientApi.createOrThrow("http://104.210.55.244:4567/", credentials);
            }
            else
                DoseFinder.clientFamilyMemberApi = ClientFamilyMemberApi.createOrThrow("http://104.210.55.244:4567/", credentials);
            //else
                //DoseFinder.clientFamilyMemberApi

            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
