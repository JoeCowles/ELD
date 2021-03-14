package com.carriergistics.eld.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataConverter {
    static int lastSpeed;
    // Returns a speed from a hex string
    public static int speedMPH(String hex){
        //Log.d("DEBUGGING", "Got hex from speed: " + hex);
        int speed;
        if(hex != null && !hex.isEmpty()){
            speed = Integer.parseInt(hex);
            lastSpeed = (int)((speed/256) * 0.6214);
        }else{
            return lastSpeed;
        }
        return (int)((speed/256) * 0.6214);
    }
    public static int convertRPM(String hex){
        int rpm = 0;
        if(hex != null && !hex.isEmpty()){
            rpm = Integer.parseInt(hex);
            rpm = (int) ( rpm * .125);
        }
        return rpm;
    }
    public static float convertFuelLevel(String level){
        if(level == null || level.isEmpty()){
            return 0f;
        }
        return (float)(Double.parseDouble(level) / 256);
    }

    public static float convertFuelLevel(int level){
        return (float) (level / 256.00);
    }
    public static float convertFuelEconomy(String msg){
        if(msg == null || msg.isEmpty()){
            return 0f;
        }
        float econ = Float.parseFloat(msg);
        econ /= 512.000;
        // Econ = km / L
        econ /= 1.609344;
        // Econ = mi / L
        econ *= .26417;
        // Econ = mpg
        return econ;
    }
    public static boolean sameDayNoTime(Date day1, Date day2) throws ParseException {
        Date date1 = removeTime(day1);
        Date date2 = removeTime(day2);
        if(date1.compareTo(date2) == 0){
            return true;
        }else{
            return false;
        }
    }

    public static Date removeTime(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy");
        return sdf.parse(date.toString().substring(0,10) + date.toString().substring(23, 28));
    }

    public static String secsToTime(int secs){
        int mins = secs / 60;
        secs %= 60;
        int hrs = mins / 60;
        mins %= 60;

        String time = (hrs > 9) ? hrs  + ":": "0" + hrs + ":";
        time += (mins > 9) ? mins + ":": "0" + mins + ":";
        time += (secs > 9) ? secs: "0" + secs;

        return time;
    }



}
