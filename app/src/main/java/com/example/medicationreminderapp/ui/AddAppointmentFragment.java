package com.example.medicationreminderapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.medicationreminderapp.R;

import java.util.ArrayList;
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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView listView;
    TextView date;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_add_appointment, container, false);

        listView = (ListView)root.findViewById(R.id.listView);
        date = (TextView)root.findViewById(R.id.selectedDate2);

        ArrayList<Doctor> doctorList = new ArrayList<>();
        doctorList.add(new Doctor(UUID.randomUUID(), "Bill", "Nye", "The", "ScienceGuy"));
        doctorList.add(new Doctor(UUID.randomUUID(), "Real", "Doctor", "greg", "MargaretThatcher"));
        ArrayList<String> doctorStrings = new ArrayList<>();
        for(Doctor d : doctorList)
        {
            doctorStrings.add(d.toString());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(root.getContext(), android.R.layout.simple_list_item_1, doctorStrings);
        listView.setAdapter(arrayAdapter);

        return root;
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
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
