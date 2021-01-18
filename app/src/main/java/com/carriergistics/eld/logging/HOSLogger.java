package com.carriergistics.eld.logging;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.Ticker;
import com.carriergistics.eld.bluetooth.TelematicsData;
import com.carriergistics.eld.ui.HomeFragment;
import com.carriergistics.eld.ui.StoppedFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class HOSLogger {
    static HOSLog currentHOSLog;
    static ArrayList<TimePeriod> log;
    static TimePeriod currentTimePeriod;
    static HOSEvent currentHOSEvent;
    static TelematicsData data;
    static Date currentTime;
    static Date startTime;
    static int minutesDriven = 0;
    static int hoursDriven = 0;
    public static Handler handler;
    private static Driver currentDriver;
    private static int lastSpeed = 0;
    private static int secsDriven = 0;
    private static int rpmGlitched = 0;
    public static void init(Driver driver){
        if(currentDriver != null && !driver.equals(currentDriver)){
            currentDriver = driver;
            if(driver.getHosLog() != null){
                currentHOSLog = driver.getHosLog();
            }else{
                currentHOSLog = new HOSLog();
                currentDriver.setHosLog(currentHOSLog);
            }
            if(driver.getLog() != null){
                log = driver.getLog();
            }else{
                log = new ArrayList<TimePeriod>();
                currentTimePeriod = new TimePeriod();
                currentTimePeriod.setStartTime(getTime());
                currentTimePeriod.setStatus(HOSEventCodes.OFF_DUTY);
                log.add(currentTimePeriod);
                driver.setLog(log);
            }
        }else{
            if(driver.getHosLog() != null){
                currentHOSLog = driver.getHosLog();
            }else{
                currentHOSLog = new HOSLog();
            }
        }

        currentTime = getTime();
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                log((TelematicsData) msg.obj);
                checkAlerts();
            }
        };

    }
    public static void log(TelematicsData _data){
        currentTime = getTime();
        data = _data;
        // Throw out any glitched data
        // TODO: Remove
        if(data.getSpeed() == 6){
            data.setSpeed(lastSpeed);
        }
        //-------
        if(Math.abs(data.getSpeed() - lastSpeed) > 20){

            lastSpeed = data.getSpeed();

            return;
        }
        if(data.getRpm() <= 0){
            if(rpmGlitched < 4){
                rpmGlitched++;
                return;
            }
        }else{
            rpmGlitched = 0;
        }
        if(currentHOSEvent == null){
            // Initialize as off duty that way it will send an off duty event, then start driving
            currentHOSEvent = new HOSEvent(HOSEventCodes.OFF_DUTY);
        }

        if(currentHOSLog == null){
            currentHOSLog = new HOSLog();
        }
        if(data.getSpeed() >= 10) {
            // DRIVING

            if(currentHOSEvent.getCode() != HOSEventCodes.DRIVING){
                // Started driving
                Log.d("DEBUGGING", "Started driving ----------------------");
                sendOBDEvent(HOSEventCodes.DRIVING);

            }
        }else{
            //NOT DRIVING
            if(currentHOSEvent.getCode() == HOSEventCodes.DRIVING && data.getRpm() <=0){
                //Stopped driving
                //TODO: Prompt user to choose why they stopped
                Log.d("DEBUGGING", "Stopped driving ----------------------");
                sendOBDEvent(HOSEventCodes.OFF_DUTY);
                MainActivity.stoppedDriving();
            }
        }
    }
    private static void sendOBDEvent(int code){
        Log.d("DEBUGGING", "Sending event with code: " + code);
        currentHOSEvent.setEndTime(getTime());
        currentHOSEvent.setType(HOSEventCodes.CHANGE_EVENT_TYPE);
        currentHOSLog.getLog().add(currentHOSEvent);
        // TODO: Send Event
        currentHOSEvent = new HOSEvent(code);
        currentHOSEvent.setStartTime(getTime());
        resetTimer();
    }

    private static void sendOBDEvent(int code, long lat, long lon){
        Log.d("DEBUGGING", "Sending event with code: " + code);
        currentHOSEvent.setEndTime(getTime());
        currentHOSEvent.setType(HOSEventCodes.CHANGE_EVENT_TYPE);
        currentHOSLog.getLog().add(currentHOSEvent);
        // TODO: Send Event
        currentHOSEvent = new HOSEvent(code);
        currentHOSEvent.setLat(lat);
        currentHOSEvent.setLong(lon);
        currentHOSEvent.setStartTime(getTime());
        resetTimer();
    }

    private static Date getTime(){
        //TODO: Get the current time from an api
        return Calendar.getInstance().getTime();
    }

    // Reset the timer that is used to send periodic updates every hour
    private static void resetTimer(){
        minutesDriven=0;
        // TODO: Periodic events
    }
    public static HOSLog getHOSLog(){
        return currentHOSLog;
    }

    // Creates a debugging log
    public static HOSLog getDebuggingLog(){
        HOSLog log = new HOSLog();
        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        for(int i = 0; i < 20; i++){
            cal.add(Calendar.HOUR, 1);
            HOSEvent event = new HOSEvent();
            int code = ((i % 2) == 0) ? HOSEventCodes.DRIVING : HOSEventCodes.OFF_DUTY;
            event.setCode(code);
            event.setType(HOSEventCodes.CHANGE_EVENT_TYPE);
            event.setEndTime(cal.getTime());
            log.getLog().add(event);
        }
        return log;
    }
    /*
    *
    *            Method gets called every second by AlertChecker task. Checks for any upcoming violations
    *
     */
    public static void checkAlerts(){
        Log.d("DEBUGGING", "Checking alerts");
        checkTime();
        double timeDrivenToday = 0;
        // in hours
        int hours;
        int mins;
        int secs;
        Date currentTime = getTime();
        Date dayAgo = addHoursToDate(currentTime, -24);

        // Add the last driving times for the events
        for(HOSEvent event : currentHOSLog.getLog()){
            // Adds the hours driven within 24 hrs
            if(event.getType() == HOSEventCodes.DRIVING) {
                if(currentHOSEvent.equals(event)) {
                    // If the event was sent less than 24 hrs ago..
                    if (HOSEvent.minutesDiff(event.getEndTime(), currentTime) > (60 * 24)) {
                        // If the event started less than 24 hrs ago
                        if (HOSEvent.minutesDiff(event.getStartTime(), currentTime) > (60 * 24)) {
                            // Add the duration of the event
                            timeDrivenToday += event.getMins() / 60;
                        } else {
                            // If the event started more than 24 hrs ago, but ended Within 24 hours, then add the time from 24 hrs ago to the end time of the event
                            timeDrivenToday += HOSEvent.minutesDiff(dayAgo, event.getEndTime()) / 60;
                        }
                    }
                }
            }
        }

        hours = (int)timeDrivenToday;
        // Mod one to get the decimal of mins, then multiply by 60
        mins = (int)((timeDrivenToday % 1) * 60);
        // Mod one again and multiply by 60 again
        secs = (int) (((timeDrivenToday % 1) * 60) %1)*60 ;
        // Finished adding the time from previous events, add the current
        mins += minutesDriven;
        hours += hoursDriven;
        secs += secsDriven;

        String time = hours < 10 ? "0" : "";
        time += hours + ":";
        time += mins < 10 ? "0" : "";
        time += mins + ":";
        time += secs < 10 ? "0" : "";
        time += secs;
        data.setTime(time);
        Message msg = Message.obtain();
        msg.setTarget(HomeFragment.handler);
        msg.obj = data;
        HomeFragment.handler.sendMessage(msg);

    }
    public static Date addHoursToDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
    public static void checkTime(){
        if(startTime == null){
            startTime = Calendar.getInstance().getTime();
        }
        long millisDiff = Calendar.getInstance().getTime().getTime() - startTime.getTime();
        secsDriven = (int) millisDiff / 1000;
        minutesDriven = secsDriven / 60;
        secsDriven %= 60;
        hoursDriven = minutesDriven / 60;
        minutesDriven %= 60;
    }
    public static void newRoute(long lat, long lon){
        sendOBDEvent(HOSEventCodes.DRIVING, lat, lon);
    }


}
