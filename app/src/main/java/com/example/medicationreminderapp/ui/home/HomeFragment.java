package com.example.medicationreminderapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiabstractions.PatientApi;
import com.example.medicationreminderapp.DoseModel;
import com.example.medicationreminderapp.DosesAdapter;
import com.example.medicationreminderapp.DoseFinder;
import com.example.medicationreminderapp.R;
import java.util.ArrayList;
import model.Dose;

public class HomeFragment extends Fragment{

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        DoseFinder.root = root;
        final TextView textView = root.findViewById(R.id.text_home);



        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                int streak = DoseFinder.patientApi.getCurrentStreak();
                final TextView textView2 = root.findViewById(R.id.textView2);
                textView2.setText(streak + " Doses");
            }
        });


        int streak = DoseFinder.patientApi.getCurrentStreak();
        final TextView textView2 = root.findViewById(R.id.textView2);
        textView2.setText(streak + " Doses");

       homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                int streak = DoseFinder.patientApi.getCurrentStreak();
                int num = DoseFinder.patientApi.getTodaysDoses().size();
                final TextView textView2 = root.findViewById(R.id.textView2);
                textView2.setText(streak + "/" + num + " Doses");
            }
        });
        //textView2.setText();
        ArrayList<DoseModel> doses = new ArrayList<>();
        RecyclerView rvContacts = (RecyclerView) root.findViewById(R.id.rvList);
        // Initialize doseModels
        if(rvContacts != null) {
            PatientApi test = DoseFinder.patientApi;
            for (Dose dose : DoseFinder.doses = DoseFinder.patientApi.getTodaysDoses())
            {
                doses.add(new DoseModel(dose.getAmountPerDose(), dose.getMedId(), !dose.isTaken(), dose.getPrescriptionId(), dose.getDosageTime()));
            }

            getActivity().getSupportFragmentManager().popBackStack();

            // Create adapter passing in the sample user data
            DosesAdapter adapter = new DosesAdapter(doses);
            // Attach the adapter to the recyclerview to populate items
            rvContacts.setAdapter(adapter);
            // Set layout manager to position the items

            // Setup layout manager for items with orientation
            // Also supports `LinearLayoutManager.HORIZONTAL`
            LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
            // Optionally customize the position you want to default scroll to
            layoutManager.scrollToPosition(0);
            // Attach layout manager to the RecyclerView
            rvContacts.setLayoutManager(layoutManager);
        }

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL);
        rvContacts.addItemDecoration(itemDecoration);


        return root;
    }


}