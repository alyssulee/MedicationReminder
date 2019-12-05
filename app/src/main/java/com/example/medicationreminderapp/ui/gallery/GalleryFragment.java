
package com.example.medicationreminderapp.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.medicationreminderapp.R;
import com.example.medicationreminderapp.ui.AddAppointmentFragment;

import java.util.Date;

public class GalleryFragment extends Fragment
{
    private static final String DATE = "date";

    private GalleryViewModel galleryViewModel;
    CalendarView calendarView;
    TextView dateDisplay;
    Long date;
    Date selectedDate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_view_appointments, container, false);
        final TextView textView = root.findViewById(R.id.appointmentDates);
        galleryViewModel.getText().observe(this, new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)
            {
                textView.setText(s);
            }
        });

        calendarView = (CalendarView) root.findViewById(R.id.AppointmentCalendar);
        dateDisplay = (TextView) root.findViewById(R.id.SelectedDate);
        Button addAppointmentButton = (Button) root.findViewById(R.id.addAppointmentButton);
        dateDisplay.setText("Date: ");

        //Toast.makeText(calendarView.getContext(), "HEEEELP :" + date, Toast.LENGTH_LONG).show();

        addAppointmentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //getActivity().getSupportFragmentManager().popBackStack();
                AddAppointmentFragment addAppointmentFragment = new AddAppointmentFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, addAppointmentFragment,
                        "findthisFragment").addToBackStack("findthisFragment").commit();
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                selectedDate = new Date(year, month, dayOfMonth);
                String str = "Date: " +year + "/" + (month + 1) + "/" + dayOfMonth;
                //Todo: if date = appointment -> str += appointment information
                dateDisplay.setText(str);

                AddAppointmentFragment fragment = new AddAppointmentFragment();
                Bundle args = new Bundle();
                args.putString(DATE, "Date: " + selectedDate.toString());
                fragment.setArguments(args);
                //Toast.makeText(view.getContext(), "Year=" + year + " Month=" + month + " Day=" + dayOfMonth, Toast.LENGTH_LONG).show();
                //System.out.println("Year=" + year + " Month=" + (month+1) + " Day=" + dayOfMonth);
            }
        });

        return root;
    }

    public void replaceFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
