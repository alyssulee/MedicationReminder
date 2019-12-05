package com.example.medicationreminderapp.ui;

import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.medicationreminderapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import model.Doctor;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddAppointmentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddAppointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAppointmentFragment extends Fragment
{
    private static final String DATE = "date";
    ListView listView;
    TextView date;
    static String selectedDate;
    Doctor selectedDoctor;
    ArrayList<Doctor> doctorList;
    Button addApptButton;

    TextView displayTime;
    Calendar currentTime;
    int hour, minute;
    String format;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_add_appointment, container, false);

        listView = (ListView)root.findViewById(R.id.listView);
        date = (TextView)root.findViewById(R.id.selectedDate2);
        addApptButton = (Button) root.findViewById(R.id.addAppointmentButton2);

        displayTime = (TextView) root.findViewById(R.id.appointment_time_pick);
        currentTime = Calendar.getInstance();

        hour = currentTime.get(Calendar.HOUR);
        minute = currentTime.get(Calendar.MINUTE);

        selectedTimeFormat(hour);
        displayTime.setText(hour + " : " + minute + " " +format);
        displayTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener()
                {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOfDay)
                    {
                        hour = hourOfDay;
                        minute = minuteOfDay;
                        selectedTimeFormat(hour);
                        if(minuteOfDay < 10)
                        {
                            displayTime.setText(hour + " : 0" + minute );
                        }
                        else
                            displayTime.setText(hour + " : " + minute );


                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        final ArrayList<Doctor> doctorList = new ArrayList<>();
        doctorList.add(new Doctor(UUID.randomUUID(), "Bill", "Nye", "The", "ScienceGuy"));
        doctorList.add(new Doctor(UUID.randomUUID(), "Real", "Doctor", "greg", "MargaretThatcher"));
        ArrayList<String> doctorStrings = new ArrayList<>();
        for(Doctor d : doctorList)
        {
            doctorStrings.add(d.toString());
        }

        Bundle bundle = this.getArguments();
        if(bundle != null)
        {
            selectedDate = bundle.getString(DATE);
            date.setText(selectedDate);
            Toast.makeText(root.getContext(), selectedDate, Toast.LENGTH_LONG).show();
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(root.getContext(), android.R.layout.simple_list_item_1, doctorStrings);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                selectedDoctor = doctorList.get(i);
                System.out.println(selectedDoctor.toString());
            }
        });

        //Create appointment
        addApptButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Todo add appointment through server
                Toast.makeText(view.getContext(), "Appointment set with:" + selectedDoctor.toString() + "at Time: " + hour + " : " + minute, Toast.LENGTH_LONG).show();
                getFragmentManager().beginTransaction().remove(AddAppointmentFragment.this).commit();
            }
        });

        return root;
    }

    public void selectedTimeFormat(int hour)
    {
        if(hour == 0)
        {
            hour += 12;
            format = "AM";
        } else if (hour == 12)
        {
            format = "PM";
        }else if(hour > 12)
        {
            hour -= 12;
            format = "PM";
        }else
        {
            format = "AM";
        }
    }

    public void updateDate(String textDate)
    {
        date.setText(textDate);
    }


    private OnFragmentInteractionListener mListener;

    public AddAppointmentFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddAppointmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddAppointmentFragment newInstance(String param1, String param2)
    {
        AddAppointmentFragment fragment = new AddAppointmentFragment();
        Bundle args = new Bundle();
        args.putString(DATE, selectedDate);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
