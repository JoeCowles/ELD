package com.carriergistics.eld.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.R;
import com.carriergistics.eld.bluetooth.BlueToothStatus;
import com.carriergistics.eld.bluetooth.BluetoothConnector;
import com.carriergistics.eld.logging.HOSLogger;
import com.carriergistics.eld.logging.Status;
import com.carriergistics.eld.logging.limits.TimeLimit;
import com.carriergistics.eld.mapping.Gps;
import com.carriergistics.eld.utils.DataConverter;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import pl.pawelkleczkowski.customgauge.CustomGauge;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // Not used yet
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 100;


    private static TextView timeDrivenTv;
    private static CustomGauge timeGuage;
    private static ImageView statusBtn;
    private static TextView timeLabel;
    private static ImageView connectedIcon;

    private MapView mMapView;
    private GoogleMap googleMap;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int speed = 0;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        timeGuage = view.findViewById(R.id.homeTimeGuage);
        timeGuage.setStartValue(0);
        timeGuage.setEndValue(100);
        timeDrivenTv = view.findViewById(R.id.timeTv);
        statusBtn = view.findViewById(R.id.homeStatusBtn);
        timeLabel = view.findViewById(R.id.homeTimeLabel);
        connectedIcon = view.findViewById(R.id.connectedIcon);

        boolean perms = checkLocationPermission();
        while (!perms) {
            perms = checkLocationPermission();
        }

        mMapView = (MapView) view.findViewById(R.id.mapViewHome);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.setMyLocationEnabled(true);
                focusCamera();
                // For zooming functionality
                //CameraPosition cameraPosition = new CameraPosition.Builder().target(googleMap.).zoom(12).build();
                //googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
        statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder;
                if (MainActivity.currentDriver.getStatus() == Status.DRIVING) {
                    builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("You must stop the truck to change your status!")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("Warning!");
                    alert.show();
                } else {
                    builder = new AlertDialog.Builder(getActivity());
                    int checked = 1;
                    if (MainActivity.currentDriver.getStatus() == Status.DRIVING) {
                        checked = 2;
                    } else if(MainActivity.currentDriver.getStatus() == Status.OFF_DUTY){
                        checked = 0;
                    } else if(MainActivity.currentDriver.getStatus() == Status.SLEEPING){
                        checked = 3;
                    }
                    builder.setSingleChoiceItems(new String[]{"Off Duty", "On duty", "Driving", "Sleeper Berth", "Begin personal use"}, checked, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //TODO: Check if owner allows personal use
                            switch(which){
                                case 0:
                                    //timeLabel.setText("Break:");
                                    // TODO: this is temporary, there will be a screen that allows you to enter your location
                                    HOSLogger.log(Status.OFF_DUTY, Gps.getReading());
                                    MainActivity.instance.switchToFragment(StatusFragment.class);
                                    break;
                                case 1:
                                    //timeLabel.setText("Break:");
                                    // TODO: this is temporary, there will be a screen that allows you to enter your location
                                    HOSLogger.log(Status.ON_DUTY, Gps.getReading());
                                    break;
                                case 2:
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                    builder1.setMessage("Driving will be automatically logged!");
                                    builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    AlertDialog alert = builder1.create();
                                    alert.show();
                                    break;
                                case 3:
                                    HOSLogger.log(Status.SLEEPING, Gps.getReading());
                                    break;
                                case 4:
                                    HOSLogger.log(Status.PERSONAL_CONV, Gps.getReading());
                                    break;
                            }
                            MainActivity.instance.switchToFragment(StatusFragment.class);
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

            }
        });
        //update();
        return view;
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    public void update() {


        boolean onBreak = false;
        TimeLimit displayedLimit = null;

        // Check if the driver is on break
        for (TimeLimit limit : MainActivity.currentDriver.getTimeLimits()) {

            // If the driver is renewing a time period
            if (limit.getRenewingStatuses().contains(MainActivity.currentDriver.getStatus())
                    && limit.getSecsToRenew() - limit.getSecsRenewed() > 0) {

                // Then show the limit that is the closest to being renewed that hasn't been renewed yet:
                if (displayedLimit == null ||
                        displayedLimit.getSecsToRenew() - limit.getSecsRenewed() >
                                limit.getSecsToRenew() - limit.getSecsRenewed()
                ) {
                    displayedLimit = limit;
                    onBreak = true;
                }

            }
        }
        if (!onBreak) {

            for (TimeLimit limit : MainActivity.currentDriver.getTimeLimits()) {
                // Find limits that are being approached
                if (limit.getDepletingStatuses().contains(MainActivity.currentDriver.getStatus())) {

                    // Find the limit that is the closest to being expired
                    if (displayedLimit == null ||
                            displayedLimit.getSecsToPerform() - displayedLimit.getSecsToPerform() >
                                    limit.getSecsToPerform() - limit.getSecsToPerform()
                    ) {
                        displayedLimit = limit;
                    }

                }

            }
        }

        if (displayedLimit != null) {

            if (onBreak) {
                timeDrivenTv.setText(DataConverter.secsToTime(displayedLimit.getSecsToRenew() - displayedLimit.getSecsRenewed()));
                timeGuage.setValue((int) (((double) (displayedLimit.getSecsToRenew() - displayedLimit.getSecsRenewed()) / displayedLimit.getSecsToRenew()) * 100));
            } else {
                timeDrivenTv.setText(DataConverter.secsToTime(displayedLimit.getSecsToPerform() - displayedLimit.getSecsPerformed()));
                timeGuage.setValue((int) (((double) (displayedLimit.getSecsToPerform() - displayedLimit.getSecsPerformed()) / displayedLimit.getSecsToPerform()) * 100));
            }

        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (BluetoothConnector.getStatus() == BlueToothStatus.CONNECTED) {
                    connectedIcon.setImageResource(R.drawable.ic_dvir);
                } else {
                    connectedIcon.setImageResource(0);
                }
                switch (MainActivity.currentDriver.getStatus()){

                    case DRIVING:
                        statusBtn.setImageResource(R.drawable.drivingbutton);
                        break;
                    case ON_DUTY:
                        statusBtn.setImageResource(R.drawable.onbutton);
                        break;
                    case OFF_DUTY:
                        statusBtn.setImageResource(R.drawable.offbutton);
                        break;

                }

            }
        });

    }
    private void focusCamera(){
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        checkLocationPermission();
        LocationServices.getFusedLocationProviderClient(getActivity())
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        //LocationServices.getFusedLocationProviderClient(getActivity()).removeLocationUpdates(this);
                        if(locationResult != null && locationResult.getLocations().size() > 0){
                            // TODO: use the gps class
                            int latestLocationIndx = locationResult.getLocations().size() - 1;
                            double latitude = locationResult.getLocations().get(latestLocationIndx).getLatitude();
                            double longitude = locationResult.getLocations().get(latestLocationIndx).getLongitude();
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(12).build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        }
                    }
                }, Looper.getMainLooper());

    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        focusCamera();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}