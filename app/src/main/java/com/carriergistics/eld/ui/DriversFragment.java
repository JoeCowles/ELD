package com.carriergistics.eld.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.R;

import pl.pawelkleczkowski.customgauge.CustomGauge;

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

    private static final int HRS_14 = 50400;
    private static final int HRS_11 = 39600;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView driverName;
    private CustomGauge breakGuage;
    private CustomGauge shiftGuage;
    private CustomGauge driveTimeGuage;
    private TextView driveTimeTv;
    private TextView shiftTv;
    private TextView breakTv;
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
        driverName = view.findViewById(R.id.driverNameTv);
        driverName.setText(MainActivity.currentDriver.getFirst_name() + " " + MainActivity.currentDriver.getLast_name());

        breakGuage = view.findViewById(R.id.driverBreakGuage);
        breakGuage.setStartValue(28800);
        breakGuage.setEndValue(0);
        breakGuage.setValue(28800 - MainActivity.currentDriver.getSecsTillBreak());
        if(MainActivity.currentDriver.getSecsTillBreak() <= 0){
            breakGuage.setValue(0);
        }
        shiftGuage = view.findViewById(R.id.driverShiftGuage);
        shiftGuage.setStartValue(0);
        shiftGuage.setEndValue(HRS_14);
        shiftGuage.setValue((HRS_14 - MainActivity.currentDriver.getSecsInShift()));
        if(MainActivity.currentDriver.getSecsLeftInShift() <= 0){
            shiftGuage.setValue(0);
        }
        driveTimeGuage = view.findViewById(R.id.driverDriveTimeGuage);
        driveTimeGuage.setStartValue(0);
        driveTimeGuage.setEndValue(HRS_11);
        driveTimeGuage.setValue(HRS_11 - MainActivity.currentDriver.getSecsDrivenToday());
        if(MainActivity.currentDriver.getSecsLeftDrivingToday() <= 0){
            driveTimeGuage.setValue(0);
        }
        breakTv = view.findViewById(R.id.driverBreakTv);

        breakTv.setText(MainActivity.currentDriver.getTimeTillBreak());

        shiftTv = view.findViewById(R.id.driverShiftTv);
        Log.d("DEBUGGING", (double)(HRS_14 - MainActivity.currentDriver.getSecsDrivenToday()) / HRS_14+ "------------");
        shiftTv.setText(MainActivity.currentDriver.getTimeLeftInShift());

        driveTimeTv = view.findViewById(R.id.driverDriveTimeTv);

        driveTimeTv.setText(MainActivity.currentDriver.getTimeLeftDrivingToday().substring(0,5));

        return view;
    }
}