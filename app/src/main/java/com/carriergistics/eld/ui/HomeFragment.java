package com.carriergistics.eld.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.R;
import com.carriergistics.eld.bluetooth.BluetoothConnector;
import com.carriergistics.eld.bluetooth.TelematicsData;
import com.carriergistics.eld.logging.HOSLogger;
import com.carriergistics.eld.logging.Status;
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
                if (speed > 5) {
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
                    // Todo: find a better way of determining if the driver is driving
                    if (speed > 5) {
                        checked = 2;
                    } else if(MainActivity.currentDriver.getStatus() == Status.OFF_DUTY){
                        checked = 0;
                    }
                    builder.setSingleChoiceItems(new String[]{"Off Duty", "On duty", "Driving", "Begin personal use"}, checked, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            //TODO: Check if owner allows personal use
                            switch(which){
                                case 0:
                                    //timeLabel.setText("Break:");
                                    HOSLogger.startOffDuty();
                                    break;
                                case 1:
                                    //timeLabel.setText("Break:");
                                    HOSLogger.startOnDuty();
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
                                    break;
                            }
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

            }
        });
        update();
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
        Log.d("DEBUGGING", "Update was called");
        if (MainActivity.getFragment().equals(HomeFragment.class.getName())) {

            if (MainActivity.currentDriver.getStatus() == Status.DRIVING) {
                timeLabel.setText("Time till break:");
                timeDrivenTv.setText(MainActivity.currentDriver.getTimeTillBreak().substring(0,5));
                // 28800 = 8 hrs
                timeGuage.setValue(((int) (((double) MainActivity.currentDriver.getSecsTillBreak() / 28800) * 100)));
            }else if(MainActivity.currentDriver.getStatus() == Status.ON_DUTY){
                // 1800 = 30 mins
                timeLabel.setText("Break:");
                if(MainActivity.currentDriver.getSecsLeftInBreak() <= 0){
                    timeGuage.setValue(100);
                }else{
                    timeGuage.setValue(((int) (((double) MainActivity.currentDriver.getSecsLeftInBreak()/1800) * 100)));
                }
                timeDrivenTv.setText(MainActivity.currentDriver.getTimeLeftInBreak().substring(3,5) + " mins");
            }else if(MainActivity.currentDriver.getStatus() == Status.OFF_DUTY){
                // 1800 = 30 mins
                timeLabel.setText("Break:");
                if(MainActivity.currentDriver.getSecsLeftInBreak() <= 0){
                    timeGuage.setValue(100);
                }else{
                    timeGuage.setValue(((int) (((double) MainActivity.currentDriver.getSecsLeftInBreak()/1800) * 100)));
                }
                timeDrivenTv.setText(MainActivity.currentDriver.getTimeLeftInBreak().substring(3,5) + " mins");
            }
            if (MainActivity.currentDriver.getStatus() == Status.DRIVING) {
                statusBtn.setImageResource(R.drawable.drivingbutton);
            } else if (MainActivity.currentDriver.getStatus() != null) {
                if(MainActivity.currentDriver.getStatus() == Status.ON_DUTY){
                    statusBtn.setImageResource(R.drawable.onbutton);
                }else if(MainActivity.currentDriver.getStatus() == Status.OFF_DUTY){
                    statusBtn.setImageResource(R.drawable.offbutton);
                }
            }
        }
    }

    private boolean checkCameraPerms() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
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
                        LocationServices.getFusedLocationProviderClient(getActivity()).removeLocationUpdates(this);
                        if(locationResult != null && locationResult.getLocations().size() > 0){
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
    //public static void offDuty(){
    //    statusBtn.setVisibility(View.INVISIBLE);
    //}
}