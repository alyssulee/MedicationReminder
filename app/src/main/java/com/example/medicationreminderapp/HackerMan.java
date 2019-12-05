package com.example.medicationreminderapp;

import com.example.apiabstractions.PatientApi;
import com.example.apiclient.ClientFamilyMemberApi;

import java.util.ArrayList;
import java.util.List;

import model.Dose;

public class HackerMan {
    public static List<Dose> doses;
    public static PatientApi patientApi;
    public static ClientFamilyMemberApi clientFamilyMemberApi;
}
