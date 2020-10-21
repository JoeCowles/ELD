package com.carriergistics.eld.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DriversFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriversFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView driverName;
    private TextView driverName2;

    public DriversFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DriversFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DriversFragment newInstance(String param1, String param2) {
        DriversFragment fragment = new DriversFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drivers, container, false);
        driverName = view.findViewById(R.id.driverNameField1);
        driverName2 = view.findViewById(R.id.driverNameField2);
        driverName.setText(MainActivity.currentDriver.getFirst_name() + ", " + MainActivity.currentDriver.getLast_name());
        if(MainActivity.secondaryDriver != null && !MainActivity.secondaryDriver.getFirst_name().isEmpty()){
            driverName2.setText(MainActivity.secondaryDriver.getFirst_name() + ", " + MainActivity.secondaryDriver.getLast_name());
        }
        return view;
    }
}