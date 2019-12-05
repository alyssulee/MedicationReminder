package com.example.medicationreminderapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.medicationreminderapp.ui.AddAppointmentFragment;
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
import java.util.Date;
import java.util.UUID;

import model.Patient;
import model.Prescription;
import model.RefillOrder;

public class FamilyMemberActivity extends AppCompatActivity
{
    ListView patientListView;
    Button selectButton;

    ArrayList<Patient> patientArrayList;
    Patient selectedPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        patientListView = (ListView) findViewById(R.id.OrderListView);
        selectButton = (Button) findViewById(R.id.RefillButton);

        //Todo: read in from db
        patientArrayList = new ArrayList<Patient>();

        patientArrayList.add(new Patient(UUID.randomUUID(), "Patient", "patient", "p", "p"));

        ArrayList<String> patientStringList =new ArrayList<>();
        for(Patient p : patientArrayList)
        {
            patientStringList.add(p.toString());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, patientStringList);
        patientListView.setAdapter(arrayAdapter);

        patientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                selectedPatient = patientArrayList.get(i);
                Toast.makeText(view.getContext(), "Selected: " + selectedPatient.toString(), Toast.LENGTH_LONG).show();
            }
        });

        //Create appointment
        selectButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String str = "";
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }

}
