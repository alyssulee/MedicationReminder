package com.example.medicationreminderapp;

import android.view.View;

import com.example.apiabstractions.PatientApi;
import com.example.apiclient.ClientFamilyMemberApi;

import java.util.List;

import model.Dose;
import model.UserType;

public class DoseFinder {
    public static List<Dose> doses;
    public static PatientApi patientApi;
    public static ClientFamilyMemberApi clientFamilyMemberApi;
    public static View root;
    public static UserType userType;
}
