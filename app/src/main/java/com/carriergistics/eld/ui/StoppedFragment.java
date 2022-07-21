package com.carriergistics.eld.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.R;
import com.carriergistics.eld.fueling.FuelingFragment;
import com.carriergistics.eld.logging.HOSLogger;
import com.carriergistics.eld.logging.Status;
import com.carriergistics.eld.mapping.Gps;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoppedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoppedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageView gasBtn, offBtn, onBtn, sleeperBtn;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StoppedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoppedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoppedFragment newInstance(String param1, String param2) {
        StoppedFragment fragment = new StoppedFragment();
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
        View view = inflater.inflate(R.layout.fragment_stopped, container, false);
        // Inflate the layout for this fragment
        gasBtn = view.findViewById(R.id.stoppedGasBtn);
        gasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPerms();
                ((MainActivity)getActivity()).switchToFragment(FuelingFragment.class);
            }
        });
        offBtn = view.findViewById(R.id.stoppedOffBtn);
        offBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Change these
                HOSLogger.log(Status.OFF_DUTY, Gps.getReading());
                ((MainActivity)getActivity()).switchToFragment(HomeFragment.class);
            }
        });
        onBtn = view.findViewById(R.id.stoppedOnBtn);
        onBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HOSLogger.log(Status.ON_DUTY, Gps.getReading());
                ((MainActivity)getActivity()).switchToFragment(HomeFragment.class);
            }
        });
        sleeperBtn = view.findViewById(R.id.stoppedSbBtn);
        sleeperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HOSLogger.log(Status.SLEEPING, Gps.getReading());
                ((MainActivity)getActivity()).switchToFragment(HomeFragment.class);
            }
        });
        return view;
    }

    private boolean checkCameraPerms(){
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        100);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        100);
            }
            return false;
        } else {
            return true;
        }
    }
}