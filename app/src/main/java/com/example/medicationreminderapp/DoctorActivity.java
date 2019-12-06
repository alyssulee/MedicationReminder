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

import model.LoginCredentials;
import model.Patient;
import model.RefillOrder;

public class DoctorActivity extends AppCompatActivity
{
    ListView patientListView;
    Button selectButton;

    List<Patient> patientArrayList;
    Patient selectedPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        patientListView = (ListView) findViewById(R.id.DoctorListView);
        selectButton = (Button) findViewById(R.id.AddPatientButtonDoctor);

        //patientArrayList = DoseFinder.
        patientArrayList = new ArrayList<>();

        ArrayList<String> patientStringList = new ArrayList<>();
        for (Patient p : patientArrayList)
        {
            patientStringList.add(p.toString());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, patientStringList);
        patientListView.setAdapter(arrayAdapter);

        patientListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                selectedPatient = patientArrayList.get(i);
                Toast.makeText(view.getContext(), "Selected: " + selectedPatient.toString(), Toast.LENGTH_LONG).show();
            }
        });

        //get patient login.
        selectButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String str = "";
                DoseFinder.patientApi = ClientPatientApi.createOrThrow("http://104.210.55.244:4567/", new LoginCredentials(selectedPatient.getUsername(), selectedPatient.getPassword()));
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

