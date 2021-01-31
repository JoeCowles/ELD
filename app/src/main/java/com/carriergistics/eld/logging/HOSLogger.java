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

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class HOSLogger {

    static HOSLog currentHOSLog;
    static ArrayList<TimePeriod> log;
    private static Driver currentDriver;
    static TimePeriod driverStatus;
    static HOSEvent currentHOSEvent;

    static TelematicsData data;
    static Date currentTime;
    static int secsDrivenToday;
    static double hoursDrivenThisWeek = 0;
    static int concurrentSecsDriven = 0;

    public static Handler handler;


    private static int lastSpeed = 0;
    private static int rpmGlitched = 0;

    // This is called when a driver goes on duty
    public static void init(Driver driver){
        if(driver == null){
            return;
        }
        if(currentDriver != null && !driver.equals(currentDriver)){
            // Current driver went off duty
            // TODO: Current driver went off duty
        }
        if(driver != null && !driver.equals(currentDriver)) {

            currentDriver = driver;

        }
        if(driver.getHosLog() != null){
            currentHOSLog = driver.getHosLog();
        }else{
            currentHOSLog = new HOSLog();
            currentDriver.setHosLog(currentHOSLog);
        }
        if(driver.getLog() != null){
            log = driver.getLog();

            // The last time period was never terminated
            if(log.get(log.size() - 1).getDuration() == 0){
                driverStatus = log.get(log.size() - 1);
            }else{
                driverStatus = new TimePeriod();
                driverStatus.setStartTime(getTime());
                driverStatus.setStatus(Status.STOPPED);
                log.add(driverStatus);
            }
        }else{
            log = new ArrayList<TimePeriod>();
            driverStatus = new TimePeriod();
            driverStatus.setStartTime(getTime());
            driverStatus.setStatus(Status.STOPPED);
            log.add(driverStatus);
            driver.setLog(log);
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
    // Determines if the driver is driving, then deals with it accordingly
    public static void log(TelematicsData _data){
        
        currentTime = getTime();
        data = _data;
        // Throw out any glitched data
        // TODO: Remove
        if(data.getSpeed() == 6){
            data.setSpeed(lastSpeed);
        }
        // TD -----------------
        if(driverStatus == null){
            driverStatus = new TimePeriod();
            driverStatus.setStartTime(getTime());
            driverStatus.setStatus(Status.STOPPED);
            log.add(driverStatus);
        }
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


        // Init variables that are not already set
        if(currentHOSEvent == null){
            // Initialize as off duty that way it will send an off duty event, then start driving
            currentHOSEvent = new HOSEvent(HOSEventCodes.OFF_DUTY);
        }
        if(driverStatus == null){
            driverStatus = new TimePeriod();
            driverStatus.setStartTime(currentTime);
            driverStatus.setStatus(Status.STOPPED);
        }
        /*

                    Proccess the info coming from the ECU

         */
        if(data.getSpeed() >= 10) {

            // DRIVING

            if(driverStatus.getStatus() != Status.DRIVING){
                // Started driving
                Log.d("DEBUGGING", "Started driving -------------->>>>>><<<<<<<>>>>><<<<<>>>--------");
                sendOBDEvent(HOSEventCodes.DRIVING);
                driverStatus.setEndTime(currentTime);
                driverStatus = new TimePeriod();
                driverStatus.setStartTime(currentTime);
                driverStatus.setStatus(Status.DRIVING);
                log.add(driverStatus);
            }
        }else{

            //Truck turned off

            if(driverStatus.getStatus() == Status.DRIVING && data.getRpm() <=0){

                //TODO: Prompt user to choose why they stopped
                Log.d("DEBUGGING", "Stopped driving ----------------------");
                sendOBDEvent(HOSEventCodes.OFF_DUTY);
                driverStatus.setEndTime(currentTime);
                driverStatus = new TimePeriod();
                driverStatus.setStatus(Status.STOPPED);
                driverStatus.setStartTime(currentTime);
                log.add(driverStatus);
                MainActivity.stoppedDriving();

            }
        }
    }
    private static void sendOBDEvent(int code){

        Log.d("DEBUGGING", "Sending event with code: " + code);
        currentHOSEvent.setEndTime(currentTime);
        currentHOSEvent.setType(HOSEventCodes.CHANGE_EVENT_TYPE);
        currentHOSLog.getLog().add(currentHOSEvent);
        // TODO: Send Event
        currentHOSEvent = new HOSEvent(code);
        currentHOSEvent.setStartTime(currentTime);

    }

    private static void sendOBDEvent(int code, long lat, long lon){

        Log.d("DEBUGGING", "Sending event with code: " + code);
        currentHOSEvent.setEndTime(currentTime);
        currentHOSEvent.setType(HOSEventCodes.CHANGE_EVENT_TYPE);
        currentHOSLog.getLog().add(currentHOSEvent);
        // TODO: Send Event
        currentHOSEvent = new HOSEvent(code);
        currentHOSEvent.setLat(lat);
        currentHOSEvent.setLong(lon);
        currentHOSEvent.setStartTime(currentTime);

    }

    private static Date getTime(){
        //TODO: Get the current time from an api
        return MainActivity.getTime();
    }

    public static HOSLog getHOSLog(){
        return currentHOSLog;
    }

    // Creates a debugging log
    public static ArrayList<TimePeriod> getDebuggingLog(){
        ArrayList<TimePeriod> log = new ArrayList<TimePeriod>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        TimePeriod event;
        for(int i = 0; i < 20; i++){
            event = new TimePeriod();
            event.setStartTime(cal.getTime());
            cal.add(Calendar.MINUTE, 30);
            Status code = ((i % 2) == 0) ? Status.DRIVING : Status.STOPPED;
            event.setStatus(code);
            event.setEndTime(cal.getTime());
            log.add(event);
        }
        return log;
    }
    /*
    *
    *            Method gets called every second by AlertChecker task. Checks for any upcoming violations
    *
     */
    public static void checkAlerts(){

        int hours = 0;
        int mins = 0;
        int secs = 0;
        Date dayAgo = addHoursToDate(currentTime, -24);

        //  Check for Day limit approaching
        secsDrivenToday = 0;
        for(TimePeriod period : log){
            if(period.getStartTime().after(dayAgo) && period.getDuration() > 0){
                secsDrivenToday += period.getDuration();
            }else if(period.getStartTime().after(dayAgo)){
                secsDrivenToday += (currentTime.getTime() - period.getStartTime().getTime())/1000;
            }
        }
        if((double)(secsDrivenToday / 3600) >= 10.5){
            // TODO:  alert the driver that they can only drive for 1/2 hour more
        }

        // Check for weekly hour limit, don't if the driver hasn't even driven 3 days in the past week
        if(currentDriver.getWeek() != null && currentDriver.getWeek().size() > 3){
            for(Day day : currentDriver.getWeek()){
                hoursDrivenThisWeek += day.getSecsDrivenToday() / 3600;
            }
            // TODO: Alert driver about time driven this week
        }


        // Check for 8-hour break
        concurrentSecsDriven = 0;
        //if((secsDrivenToday / 3600) >= 7){
            for(int i = currentDriver.getLog().size() - 1; i > 0; i--){
                TimePeriod timePeriod = currentDriver.getLog().get(i);
                if(timePeriod.getEndTime() != null && !timePeriod.getEndTime().after(dayAgo)){
                    break;
                }
                if(timePeriod.getStatus() == Status.DRIVING){

                    if (timePeriod.getDuration() > 0) {
                        if(timePeriod.getStartTime().before(dayAgo)){
                            concurrentSecsDriven += (timePeriod.getEndTime().getTime() - dayAgo.getTime())/1000;
                        }else{
                            concurrentSecsDriven += timePeriod.getDuration();
                        }
                    }else{
                        concurrentSecsDriven += (currentTime.getTime() - timePeriod.getStartTime().getTime())/ 1000;
                    }
                    // If The driver has taken a break of longer than 1800 secs (30 mins), then stop counting
                }else if(timePeriod.getStatus() != Status.DRIVING && timePeriod.getDuration() >= 1800){
                    break;
                }
            }
        //}
        //TODO: Alert driver of necessary upcoming break


        secs = concurrentSecsDriven;
        mins = secs / 60;
        secs %= 60;
        hours = mins / 60;
        mins %= 60;

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

    public static void newRoute(long lat, long lon){

        sendOBDEvent(HOSEventCodes.DRIVING, lat, lon);

    }

    public static void save(Status status){
        if(driverStatus != null){
            driverStatus.setEndTime(getTime());
        }
        driverStatus = new TimePeriod();
        driverStatus.setStatus(status);
        driverStatus.setStartTime(getTime());
        if(log != null){
            log.add(driverStatus);
            currentDriver.setLog(log);
            currentDriver.setHosLog(currentHOSLog);
        }
    }

    public static ArrayList<TimePeriod> getLog(){
        ArrayList<TimePeriod> temp = new ArrayList<TimePeriod>();
        for(TimePeriod t : log){
            if(t.getEndTime() == null || t.getEndTime().after(addHoursToDate(getTime(), -24))){
                temp.add(t);
            }
        }
        return temp;
    }

}
