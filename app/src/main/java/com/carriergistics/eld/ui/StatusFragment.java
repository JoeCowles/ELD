package com.carriergistics.eld.ui;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.R;
import com.carriergistics.eld.logging.Day;
import com.carriergistics.eld.logging.Driver;
import com.carriergistics.eld.logging.Status;
import com.carriergistics.eld.logging.TimePeriod;
import com.carriergistics.eld.mapping.LocationReading;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatusFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText locationEt;
    EditText noteEt;

    ImageView locationBtn;

    Button saveBtn;
    Spinner statusSpinner;

    Geocoder geocoder;
    // Is compared to the edit text area to tell if the location was entered by the user
    String locStr;

    public StatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatusFragment newInstance(String param1, String param2) {
        StatusFragment fragment = new StatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        Driver driver = MainActivity.currentDriver;
        TimePeriod currentTimePeriod = driver.getCurrentTimePeriod();
        locationEt = view.findViewById(R.id.statusLocationEt);
        noteEt = view.findViewById(R.id.statusNoteET);
        saveBtn = view.findViewById(R.id.statusSaveBtn);
        statusSpinner = view.findViewById(R.id.statusSpinner);
        locationBtn = view.findViewById(R.id.statusLocationBtn);

        locStr = "";

        geocoder = new Geocoder(getActivity());
        Address location = null;
        try {
            if(currentTimePeriod.getStartLoc() != null){
                location = geocoder.getFromLocation(currentTimePeriod.getStartLoc().getLat(), currentTimePeriod.getStartLoc().getLon(), 1).get(0);
                locStr = location.getLocality() + "," + location.getAdminArea();
                locationEt.setText(location.getLocality() + ", " + location.getAdminArea());
            }else{
                locationEt.setText("Couldn't get location");
            }
        } catch (IOException e) {
            locationEt.setText("Couldn't get your location");
            e.printStackTrace();
        }
        String selected = "";
        switch (MainActivity.currentDriver.getStatus()){
            case OFF_DUTY:
                selected = "Off Duty";
                break;
            case PERSONAL_CONV:
                selected = "Personal conveyance";
                break;
            default:
                selected = "On Duty";
                break;
        }
        String[] statuses = new String[]{
                "On Duty", "Off Duty", "Personal conveyance"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, statuses);
        statusSpinner.setAdapter(adapter);
        statusSpinner.setSelection(adapter.getPosition(selected));

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address address;
                if((address = lookupAddress(locationEt.getText().toString()))!= null){
                    Log.d("DEBUGGING", "Found the address at lat: " + address.getLatitude() + " long: " + address.getLongitude());
                    try {
                        TimePeriod editing = MainActivity.currentDriver.getDay(MainActivity.getTime()).getTimePeriods().get(0);
                        editing.setEndLoc(new LocationReading(address.getLatitude(), address.getLongitude(), 0, locStr.equals(locationEt.getText().toString())));
                        editing.setNote(noteEt.getText().toString());
                        Status status = Status.OFF_DUTY;
                        switch ((String)statusSpinner.getSelectedItem()){
                            case "On Duty":
                                status = Status.ON_DUTY;
                                break;
                            case "Personal conveyance":
                                status = Status.PERSONAL_CONV;
                                break;
                        }
                        editing.setStatus(status);
                        MainActivity.currentDriver.setStatus(status);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }else{

                    try {
                        TimePeriod editing = MainActivity.currentDriver.getDay(MainActivity.getTime()).getTimePeriods().get(0);
                        editing.setNote(noteEt.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                MainActivity.instance.switchToFragment(HomeFragment.class);
            }
        });
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Address location = null;
                try {
                    if(currentTimePeriod.getStartLoc() != null){
                        location = geocoder.getFromLocation(currentTimePeriod.getStartLoc().getLat(), currentTimePeriod.getStartLoc().getLon(), 1).get(0);
                        locStr = location.getLocality() + "," + location.getAdminArea();
                        locationEt.setText(location.getLocality() + ", " + location.getAdminArea());
                    }else{
                        locationEt.setText("Couldn't get location");
                    }
                } catch (IOException e) {
                    locationEt.setText("Couldn't get your location");
                    e.printStackTrace();
                }

            }
        });
        return view;
    }

    public Address lookupAddress(String address){
        Address location = null;
        try {
            location = geocoder.getFromLocationName(address, 1).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }
}