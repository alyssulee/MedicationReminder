package com.example.apiclient;

import com.example.apiabstractions.PatientApi;

import java.util.List;

import model.Dose;
import model.LoginCredentials;

public class TestRemoteApi
{
    public static void main(String[] args) {
        LoginCredentials patientCredentials = new LoginCredentials("PatientA", "patient");
        PatientApi patientApi = ClientPatientApi.createOrThrow("http://104.210.55.244:4567/", patientCredentials);

        List<Dose> todaysDoses = patientApi.getTodaysDoses();
        System.out.println(todaysDoses);
    }
}