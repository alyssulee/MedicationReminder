package com.example.medicationreminderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import model.Dose;

public class DosesAdapter extends RecyclerView.Adapter<DosesAdapter.ViewHolder> {
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView prescriptionIdView;
        public TextView dosageTimeView;
        public Switch messageButton;
        public Context context;
        private final View.OnClickListener listener;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, Context context) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            this.context = context;
            this.listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UUID prescriptionId = UUID.fromString(prescriptionIdView.getText().toString());
                    Timestamp dosageTime = Timestamp.valueOf(dosageTimeView.getText().toString());

                    Dose dose = null;
                    for (Dose d : DoseFinder.doses) {
                        if (d.getPrescriptionId().equals(prescriptionId) && d.getDosageTime().equals(dosageTime)) {
                            dose = d;
                            break;
                        }
                    }

                    if (messageButton.isChecked()) {
                        DoseFinder.patientApi.confirmDoseTaken(dose);
                        System.out.println("Dose confirmed");
                        int num = DoseFinder.patientApi.getTodaysDoses().size();
                        int streak = DoseFinder.patientApi.getCurrentStreak();
                        final TextView textView2 = DoseFinder.root.findViewById(R.id.textView2);
                        textView2.setText(streak + "/" + num + " Doses");

                        //messageButton.setChecked(true);
                    } else {
                        DoseFinder.patientApi.markDoseUntaken(dose);
                        System.out.println("Undone");
                        int num = DoseFinder.patientApi.getTodaysDoses().size();
                        int streak = DoseFinder.patientApi.getCurrentStreak();
                        final TextView textView2 = DoseFinder.root.findViewById(R.id.textView2);
                        textView2.setText(streak + "/" + num + " Doses");
                        //messageButton.setChecked(false);
                    }
                }
            };
            nameTextView = itemView.findViewById(R.id.contact_name);
            prescriptionIdView = itemView.findViewById(R.id.prescriptionId);
            dosageTimeView = itemView.findViewById(R.id.dosageTime);
            messageButton = itemView.findViewById(R.id.message_button);
            //buttons++;

            messageButton.setOnClickListener(listener);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                // We can access the data within the views
                Toast.makeText(context, "Gyes + " + v.findViewById(R.id.message_button).getId(), Toast.LENGTH_SHORT).show();

            }
        }

    }


    // Store a member variable for the doseModels
    private List<DoseModel> mDoseModels;

    // Pass in the contact array into the constructor
    public DosesAdapter(List<DoseModel> doseModels) {
        mDoseModels = doseModels;
        //this.listener = listener;
    }

    @Override
    public DosesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_contact, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, context);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(DosesAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        DoseModel doseModel = mDoseModels.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(doseModel.getmAmount() + " " + doseModel.getName());
        Switch button = viewHolder.messageButton;
        button.setChecked(!doseModel.isOnline());
        button.setEnabled(true);

        viewHolder.prescriptionIdView.setText(doseModel.getPrescriptionId().toString());
        viewHolder.dosageTimeView.setText(doseModel.getDosageTime().toString());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mDoseModels.size();
    }


}





