package com.example.medicationreminderapp.ui.slideshow;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.medicationreminderapp.DoseFinder;
import com.example.medicationreminderapp.R;
import com.example.medicationreminderapp.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.PatientMeasurement;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);




            }
        });


        final TextView textView3 = root.findViewById(R.id.textView3);
        int streak = DoseFinder.patientApi.getCurrentStreak();
        textView3.setText("Current streak: " + streak + " Doses");
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        final TextView textView4 = root.findViewById(R.id.textView4);
        streak = DoseFinder.patientApi.getLongestStreak();
        textView4.setText("Longest streak:  " + streak + " Doses");

        //final TextView textView5 = root.findViewById(R.id.textView4);
        //List<PatientMeasurement> info = DoseFinder.patientApi.getAllMeasurements();
        //textView5.setText("");
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });


        return root;
    }
}