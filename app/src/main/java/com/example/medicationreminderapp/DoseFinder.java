package com.example.medicationreminderapp;

import com.example.apiabstractions.PatientApi;
import com.example.apiclient.ClientFamilyMemberApi;

import java.util.List;

import model.Dose;

public class DoseFinder {
    public static List<Dose> doses;
    public static PatientApi patientApi;
    public static ClientFamilyMemberApi clientFamilyMemberApi;
}
