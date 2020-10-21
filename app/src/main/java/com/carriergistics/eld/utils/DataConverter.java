package com.carriergistics.eld.utils;

import android.util.Log;

public class DataConverter {
    static int lastSpeed;
    // Returns a speed from a hex string
    public static int speedMPH(String hex){
        Log.d("DEBUGGING", "Got hex from speed: " + hex);
        int speed;
        if(hex != null && !hex.isEmpty()){
            speed = Integer.parseInt(hex, 16);
            lastSpeed = (int)((speed/256) * 0.6214);
        }else{
            return lastSpeed;
        }
        return (int)((speed/256) * 0.6214);
    }
    public static int convertRPM(String hex){
        int rpm = 0;
        if(hex != null && !hex.isEmpty()){
            rpm = Integer.parseInt(hex, 16);
            rpm = (int) ( rpm * .125);
        }
        return rpm;
    }


}
