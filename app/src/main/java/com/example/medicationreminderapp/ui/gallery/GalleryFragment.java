
package com.example.medicationreminderapp.ui.gallery;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.medicationreminderapp.DoseFinder;
import com.example.medicationreminderapp.MainActivity;
import com.example.medicationreminderapp.R;
import com.example.medicationreminderapp.ui.AddAppointmentFragment;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.roomorama.caldroid.CaldroidFragment;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import model.Appointment;

public class GalleryFragment extends Fragment
{
    private static final String DATE = "date";
    private GalleryViewModel galleryViewModel;
    CalendarView calendarView;
    List<Appointment> appointmentsList;
    //CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    //private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());


    TextView dateDisplay;
    TextView displayMonth;
    Long date;
    Date selectedDate;
    Button addAppointmentButton;
    String saveDate;

    DataPassListener mCallBack;

    public interface DataPassListener
    {
        public void passData(String str);
    }

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
        //compactCalendarView = (CompactCalendarView) root.findViewById(R.id.AppointmentCalendar);
        //compactCalendarView.setUseThreeLetterAbbreviation(true);
        dateDisplay = (TextView) root.findViewById(R.id.SelectedDate);
        addAppointmentButton = (Button) root.findViewById(R.id.addAppointmentButton);
        dateDisplay.setText("Date: ");

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

                if (view.getId() == R.id.addAppointmentButton)
                {
                    mCallBack.passData(saveDate);
                }
            }
        });

        appointmentsList = new ArrayList<Appointment>();
        appointmentsList.add(new Appointment(UUID.randomUUID(), UUID.randomUUID(), new Date(2019, 12, 10), new Time(10, 20, 0)));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                selectedDate = new Date(year, month, dayOfMonth);
                saveDate = year + "/" + (month + 1) + "/" + dayOfMonth;
                String str = "Date: " + saveDate;
                //Todo: if date = appointment -> str += appointment information

                dateDisplay.setText(saveDate);

                for(Appointment a : appointmentsList)
                {
                    if(selectedDate.compareTo(a.getDate()) == 0);
                    {
                        Toast.makeText(view.getContext(), "Appointment: " + a.toString(), Toast.LENGTH_LONG).show();
                        break;
                    }
                }
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


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try
        {
            mCallBack = (MainActivity) context;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement OnImageClickListener");
        }
    }

    public void replaceFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
