package com.example.medicationreminderapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {

    ArrayList<DoseModel> doseModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ...
        // Lookup the recyclerview in activity layout

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        // Initialize doseModels
        if(rvContacts != null)
        {
            //doseModels = DoseModel.createDosessList(20);
        // Create adapter passing in the sample user data
        DosesAdapter adapter = new DosesAdapter(doseModels);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items

        // Setup layout manager for items with orientation
// Also supports `LinearLayoutManager.HORIZONTAL`
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
// Optionally customize the position you want to default scroll to
        layoutManager.scrollToPosition(0);
// Attach layout manager to the RecyclerView
        rvContacts.setLayoutManager(layoutManager);

        // That's all!
    }}
}