package com.example.medicationreminderapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.apiclient.ClientPatientApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.Appointment;
import model.LoginCredentials;
import model.Patient;
import model.RefillOrder;

public class DoctorActivity extends AppCompatActivity
{
    ListView patientListView;
    Button selectButton;

    List<Appointment> appointmentArrayList;
    Appointment selectedAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        patientListView = (ListView) findViewById(R.id.DoctorListView);

        //patientArrayList = DoseFinder.
        appointmentArrayList = new ArrayList<>();

        ArrayList<String> patientStringList = new ArrayList<>();
        for (Appointment p : appointmentArrayList)
        {
            patientStringList.add(p.toString());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, patientStringList);
        patientListView.setAdapter(arrayAdapter);


    }
}

