package com.example.medicationreminderapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import model.Patient;
import model.Prescription;
import model.PrescriptionFrequency;
import model.RefillOrder;

public class PharmacistActivity extends AppCompatActivity
{
    ListView orderListView;
    Button selectButton;

    List<RefillOrder> orderArrayList = new ArrayList<>();
    RefillOrder selectedOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        orderListView = (ListView)findViewById(R.id.OrderListView2);
        selectButton = (Button) findViewById(R.id.RefillButton2);
        ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();

        prescriptions.add(new Prescription(UUID.randomUUID(), "DB00005", new Date(), 5, 5, 5, PrescriptionFrequency.BID, 5));

        RefillOrder order = new RefillOrder(new Date(), UUID.randomUUID(), prescriptions);
        orderArrayList.add(order);
        ArrayList<String> orderStringList = new ArrayList<>();
        for (RefillOrder o : orderArrayList)
        {
            orderStringList.add(o.toString());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, orderStringList);
        orderListView.setAdapter(arrayAdapter);


        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                selectedOrder = orderArrayList.get(i);
                Toast.makeText(view.getContext(), "Selected: " + selectedOrder.toString(), Toast.LENGTH_LONG).show();
            }
        });

        selectButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //TODO refill order
            }
        });
    }
}
