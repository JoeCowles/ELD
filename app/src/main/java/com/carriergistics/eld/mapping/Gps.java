package com.carriergistics.eld.mapping;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import com.carriergistics.eld.MainActivity;
import com.google.android.gms.maps.GoogleMap;

public class Gps {

    private static LocationManager locationManager;
    private static Location location;


    static int milesSinceLastRead;
    static double lat;
    static double lon;
    static int lastOdo = 0;

    public static void init() {

        locationManager = (LocationManager) MainActivity.instance.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(
                MainActivity.instance, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MainActivity.instance, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.instance, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {

            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }
    }
    public static void init(int odo) {

        locationManager = (LocationManager) MainActivity.instance.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(
                MainActivity.instance, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MainActivity.instance, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.instance, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {

            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }
        lastOdo = odo;
    }
    public static LocationReading getReading() {

        if (ActivityCompat.checkSelfPermission(MainActivity.instance, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.instance, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location == null) return null;
        lat = location.getLatitude();
        lon = location.getLongitude();
        return new LocationReading(lat, lon, 0);
    }
    public static LocationReading getReading(int odo){

        if(lastOdo == 0){
            lastOdo = odo;
        }
        //TODO: This system will not work
        milesSinceLastRead = odo - lastOdo;
        lastOdo = odo;
        if (ActivityCompat.checkSelfPermission(MainActivity.instance, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.instance, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        if(location == null) return null;
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        lat = location.getLatitude();
        lon = location.getLongitude();

        return new LocationReading(lat, lon, milesSinceLastRead);
    }

    private void updateGps(){



    }
    public double getLat() {
        return lat;
    }


    public double getLon() {
        return lon;
    }

}
