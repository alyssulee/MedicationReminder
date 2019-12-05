package com.example.medicationreminderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import model.User;

public class DosesAdapter extends RecyclerView.Adapter<DosesAdapter.ViewHolder> {
    //private final OnItemClickListener listener;
        //static int buttons = 0;
        // Provide a direct reference to each of the views within a data item
        // Used to cache the views within the item layout for fast access
        public class ViewHolder extends RecyclerView.ViewHolder {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            public TextView nameTextView;
            public Button messageButton;
            public Context context;


            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView, Context context) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);
                this.context = context;
                //this.listener = listener;
                nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
                messageButton = (Button) itemView.findViewById(R.id.message_button);
                //buttons++;
                //messageButton.setOnClickListener(nameTextView.getText(), this.context);


            }



            /*@Override
            public void onClick(String name) {
                int position = getAdapterPosition(); // gets item position
                if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                    // We can access the data within the views
                    Toast.makeText(context, nameTextView.getText(), Toast.LENGTH_SHORT).show();

                }
            }*/

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
        Button button = viewHolder.messageButton;
        button.setText(doseModel.isOnline() ? "Confirm" : "Taken");
        button.setEnabled(doseModel.isOnline());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mDoseModels.size();
    }



}





