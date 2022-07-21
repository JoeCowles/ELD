package com.carriergistics.eld.telematics;

import android.location.Location;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.apiData.GeoLocation;
import com.carriergistics.eld.bluetooth.EngineData;
import com.carriergistics.eld.mapping.Gps;

import java.util.ArrayList;
import java.util.Date;

public class TelematicsLogger {
    static String driverID;
    static String eldID;
    static double maxSpeedMPH;
    // Random values for now
    static final double brakingThreshold = -12;
    static final double accelThreshold = 12;

    static int numReadings; // used for Avg stats
    static long totalEngineRpm;
    static long totalSpeedMph;
    static float mpg;

    private static double lastSpeed;
    private static int lastTime;

    public static ArrayList<TelematicsEvent> events;
    public static void init(String driver, String eld){

        driverID = driver;
        eldID = eld;

        maxSpeedMPH = 0;
        numReadings = 0;
        totalEngineRpm = 0;
        totalSpeedMph = 0;
    }
    // Logs events
    public static void log(EngineData data){
        // going to change the braking and accel events to be the ELD's responsibility.
        if(checkBraking(data.getSpeed(), data.getRunTime())){
            brakingEvent();
        }
        if(checkAccel(data.getSpeed(), data.getRunTime())){
            accelEvent();
        }
        // Update stats
        if(data.getSpeed() > maxSpeedMPH){
            maxSpeedMPH = data.getSpeed();
        }
        totalEngineRpm += data.getRpm();
        totalSpeedMph += data.getSpeed();
        numReadings++;
    }
    private static boolean checkBraking(double speed, int runtime){
        double diff = lastSpeed - speed;
        if(diff / (lastTime - runtime) < brakingThreshold){
            return true;
        }
        return false;
    }
    private static boolean checkAccel(double speed, int runtime){
        double diff = lastSpeed - speed;
        if(diff / (lastTime - runtime) > accelThreshold){
            return true;
        }
        return false;
    }
    private static void brakingEvent(){
        Date timestamp = MainActivity.getTime();
        double lat = 0, lon = 0;
        lat = Gps.getReading().getLat();
        lon = Gps.getReading().getLon();
        GeoLocation geoLoc = new GeoLocation(lat, lon, MainActivity.getTime());
        TelematicsEvent event = new TelematicsEvent("EB", "", geoLoc, timestamp);
    }
    private static void accelEvent(){
        Date timestamp = MainActivity.getTime();
        double lat = 0, lon = 0;
        lat = Gps.getReading().getLat();
        lon = Gps.getReading().getLon();
        GeoLocation geoLoc = new GeoLocation(lat, lon, MainActivity.getTime());
        TelematicsEvent event = new TelematicsEvent("EA", "", geoLoc, timestamp);
    }

    public double avgEngineRpm(){
        return totalEngineRpm / numReadings;
    }
    public double avgSpeedMph(){
        return totalSpeedMph / numReadings;
    }

    //is called upon completion of the drive
    public static void completedDrive(){

    }
    // Sends the telematic data to the api
    public void sendTelematics(){


    }


}
