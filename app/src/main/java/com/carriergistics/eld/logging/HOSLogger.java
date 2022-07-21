package com.carriergistics.eld.logging;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.apiData.GeoLocation;
import com.carriergistics.eld.bluetooth.BluetoothConnector;
import com.carriergistics.eld.bluetooth.EngineData;
import com.carriergistics.eld.logging.limits.Alert;
import com.carriergistics.eld.logging.limits.TimeLimit;
import com.carriergistics.eld.mapping.Gps;
import com.carriergistics.eld.mapping.LocationReading;
import com.carriergistics.eld.ui.StatusFragment;
import com.carriergistics.eld.utils.DataConverter;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HOSLogger {

    static Day currentDay;
    private static Driver currentDriver;

    public static EngineData data;

    public static final int MPH_5 = 5;

    public static Handler handler;

    static int secsDrivenToday;
    static double hoursDrivenThisWeek = 0;
    static int concurrentSecsDriven = 0;
    private static int secsTillBreak;

    private static int secsLeftBreak;
    private static int secsInShift;

    private static int lastSpeed = 0;
    private static int rpmGlitched = 0;
    private static int lastRpm = 0;

    private static int speed;


    public static void init(Driver driver){

        currentDriver = driver;
        for(Day d : driver.getDays()){

            try {
                if(DataConverter.sameDayNoTime(d.getDate(), MainActivity.getTime())){
                    currentDay = d;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(currentDay == null){
            try {
                currentDay = new Day(DataConverter.removeTime(MainActivity.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                processEngineData((EngineData) msg.obj);
            }
        };

    }

    public static void processEngineData(EngineData _data){
        data = _data;
        // If the driver is not driving
        if(currentDriver.getStatus() != Status.DRIVING && currentDriver.getStatus() != Status.PERSONAL_CONV){
            // But the truck is moving
            if(data.getSpeed() > MPH_5){
                // The driver is driving
                log(Status.DRIVING, Gps.getReading(BluetoothConnector.getOdo()));

            }

        }else{

            if(data.getSpeed() == 0 && data.getRpm() == 0){

                // TODO: Ask the driver why they stopped
                if(currentDriver.getStatus() == Status.DRIVING){
                    if(!MainActivity.getFragment().equals(StatusFragment.class.getName())) {
                        MainActivity.instance.switchToFragment(StatusFragment.class);
                    }
                }

            }

        }

    }
    public static void log(Status status, LocationReading location){

        TimePeriod currentPeriod = currentDriver.getCurrentTimePeriod();

        if(currentPeriod == null){
            currentPeriod = new TimePeriod();
            currentPeriod.setStatus(currentDriver.getStatus());
            currentPeriod.setStartTime(MainActivity.getTime());
        }
        currentPeriod.setEndLoc(location);
        currentPeriod.setEndTime(MainActivity.getTime());
        Log.d("DEBUGGING", "ENDING LOG EVENT WITH STATUS " + currentPeriod.getStatus() + " and end time: " + currentPeriod.getEndTime());
        if(currentDriver.getTrip() != null){

            currentDriver.getTrip().getHosLog().add(currentPeriod);

        }
        try {
            currentDriver.getDay(MainActivity.getTime()).addTimePeriod(currentPeriod);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        currentPeriod = new TimePeriod();

        currentPeriod.setStartLoc(location);
        currentPeriod.setStartTime(MainActivity.getTime());

        currentPeriod.setStatus(status);

        currentDriver.setCurrentTimePeriod(currentPeriod);

        Log.d("DEBUGGING", "LOGGING EVENT WITH STATUS " + currentPeriod.getStatus() + " and start time: " + currentPeriod.getStartTime());

        currentDriver.setStatus(status);


    }
    // Allows the specification of a time
    public static void log(Status status, LocationReading location, Date time){

        TimePeriod currentPeriod = currentDriver.getCurrentTimePeriod();

        currentPeriod.setEndLoc(location);
        currentPeriod.setEndTime(time);

        currentDriver.getTrip().getHosLog().add(currentPeriod);

        currentPeriod = new TimePeriod();

        currentPeriod.setStartLoc(location);
        currentPeriod.setStartTime(time);

        currentPeriod.setStatus(status);

        currentDriver.setCurrentTimePeriod(currentPeriod);


    }

    // Called periodically, checks if the driver is approaching their limits
    public static void checkAlerts(){

        // Zero the time variables
        concurrentSecsDriven = 0;
        // List containing today and the last day's time periods
        ArrayList<TimePeriod> timePeriods = new ArrayList<>();

        for(TimePeriod period : currentDay.getTimePeriods()){
            timePeriods.add(period);
        }
        try {
            if(currentDriver.getYesterday() != null){
                for(TimePeriod period : currentDriver.getYesterday().getTimePeriods()){
                    timePeriods.add(period);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Calculate if the driver is approaching a time limit
        for(TimeLimit limit : currentDriver.getTimeLimits()){
            int concurrentSecs = 0;
            int breakSecs = 0;
            // Loop through the time periods in the past few days
            for(int i = 0; i < timePeriods.size(); i++){
                TimePeriod period = timePeriods.get(i);
                // If this time period depletes the time, then add it to the counter
                if(limit.getDepletingStatuses().contains(period.getStatus())){
                    concurrentSecs += period.getDuration();
                    // otherwise, if it renews it, add it to the break
                }else if(limit.getRenewingStatuses().contains(period.getStatus())){
                    breakSecs += period.getDuration();
                }
                // If the break is satisfied
                if(breakSecs >= limit.getSecsToRenew()){
                    // If the driver just renewed the limit
                    if(limit.getRenewingStatuses().contains(currentDriver.getCurrentTimePeriod().getStatus())){
                        // TODO: let the driver know they can drive again
                    }
                    break;
                }
            }
            // Set the times in the TimeLimits
            limit.setSecsPerformed(concurrentSecs);
            limit.setSecsRenewed(breakSecs);
            // Check if any of the alerts should be executed:
            for(Alert alert : limit.getAlerts()){
                // If the driver should be alerted
                if(!alert.isExecuted() && alert.getSecsLeft() >= Math.abs(limit.getSecsPerformed() - concurrentSecs) ){

                    // TODO: execute alerts

                }

            }

        }

    }

    // Determines if the driver is driving, then deals with it accordingly
    /*public static void log(EngineData _data){
        
        currentTime = getTime();
        data = _data;
        speed = data.getSpeed();

        if(driverStatus == null){

            driverStatus = new TimePeriod();
            driverStatus.setStartTime(getTime());
            driverStatus.setStatus(Status.ON_DUTY);
            log.add(driverStatus);

        }

        //if(data.getRpm() <= 0){
            //if(rpmGlitched < 4){
             //   rpmGlitched++;
             //   data.setRpm(lastRpm);
           // }
       // }else{
         //   rpmGlitched = 0;
         //   lastRpm = data.getRpm();
       // }

        // Init variables that are not already set
        if(currentHOSEvent == null){
            // Initialize as off duty that way it will send an off duty event, then start driving
            currentHOSEvent = new HOSEvent(HOSEventCodes.OFF_DUTY);
        }

        if(driverStatus == null){
            driverStatus = new TimePeriod();
            driverStatus.setStartTime(currentTime);
            driverStatus.setStatus(Status.ON_DUTY);
            log.add(driverStatus);
        }*/

        /*

                    Process the info coming from the ECU

         *//*
        if(data.getSpeed() >= 5) {

            // DRIVING

            if(driverStatus.getStatus() != Status.DRIVING){
                // Started driving
                Log.d("DEBUGGING", "Started driving -------------->>>>>><<<<<<<>>>>><<<<<>>>--------");
                sendOBDEvent(HOSEventCodes.DRIVING);
                driverStatus.setEndTime(currentTime);
                driverStatus = new TimePeriod();
                driverStatus.setStartTime(currentTime);
                driverStatus.setStatus(Status.DRIVING);
                currentDriver.setStatus(Status.DRIVING);
                log.add(driverStatus);
                currentDay.addTimePeriod(driverStatus);
                if(currentDriver.getDays() != null && currentDriver.getDays().size() > 0){
                    if(currentDriver.getDays().get(currentDriver.getDays().size() -1).getTimePeriods() == null){
                        currentDriver.getDays().get(currentDriver.getDays().size() -1).setTimePeriods(new ArrayList<TimePeriod>());
                    }
                    currentDriver.getDays().get(currentDriver.getDays().size() -1).addTimePeriod(driverStatus);

                }
            }
            MainActivity.save();
        }else{

            //Truck turned off

            if(driverStatus.getStatus() == Status.DRIVING && data.getRpm() <=0){

                //TODO: Prompt user to choose why they stopped
                Log.d("DEBUGGING", "Stopped driving ----------------------");
                //sendOBDEvent(HOSEventCodes.OFF_DUTY);
                driverStatus.setEndTime(currentTime);
                driverStatus = new TimePeriod();
                driverStatus.setStatus(Status.ON_DUTY);
                driverStatus.setStartTime(currentTime);
                currentDay.addTimePeriod(driverStatus);

                log.add(driverStatus);
                if(MainActivity.currentVehicle != null){
                    MainActivity.currentVehicle.setOdo(BluetoothConnector.getOdo());
                    Log.d("DEBUGGING", "ODO: " + MainActivity.currentVehicle.getOdo());
                }
                MainActivity.stoppedDriving();
                currentDriver.setStatus(Status.ON_DUTY);
                if(currentDriver.getDays() != null && currentDriver.getDays().size() > 0){
                    currentDriver.getDays().get(currentDriver.getDays().size() -1).addTimePeriod(driverStatus);
                }
                MainActivity.save();
            }
        }
    }*/

    public static int getSpeed(){
        return speed;
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
            Status code = ((i % 2) == 0) ? Status.DRIVING : Status.ON_DUTY;
            event.setStatus(code);
            event.setEndTime(cal.getTime());
            log.add(event);
        }
        return log;
    }
    /*
    *
    *            Method gets called periodically
    *
     */
    /*public static void checkAlerts(){

        Date dayAgo = addHoursToDate(currentTime, -24);
        // TODO: Constants
        // 8 hrs
        secsTillBreak = 28800;
        // 30 mins
        secsLeftBreak = 1800;

        // 11 hrs
        int secsLeftDrivingToday = 39600;

        // TODO: Find what cycle the driver is on, and alert them accordingly
        //  Check for Day limit approaching


        secsDrivenToday = 0;
        secsInShift = 0;
        if(log != null && log.size() >= 1) {
            for (TimePeriod period : log) {

                // if the event started within 24hrs ago, then add it
                // Period is within 24hrs driving
                if (period.getStartTime().after(dayAgo) && period.getDuration() > 0 && period.getStatus() == Status.DRIVING) {
                    secsDrivenToday += period.getDuration();
                    secsInShift += period.getDuration();

                    // Current period is driving and it is driving
                } else if (period.getStartTime().after(dayAgo) && period.getStatus() == Status.DRIVING) {
                    secsDrivenToday += (currentTime.getTime() - period.getStartTime().getTime()) / 1000;
                    secsInShift += (currentTime.getTime() - period.getStartTime().getTime()) / 1000;

                    // If a past period was spent driving that ended within 24 hrs, but started later
                } else if (period.getEndTime() != null && period.getEndTime().after(dayAgo) && period.getStatus() == Status.DRIVING) {
                    secsDrivenToday += Math.abs(currentTime.getTime() - dayAgo.getTime()) / 1000;
                    secsInShift += Math.abs(currentTime.getTime() - dayAgo.getTime()) / 1000;

                    // If the period started within 24 hrs and the driver was on duty
                } else if (period.getStartTime().after(dayAgo) && period.getDuration() > 0 && period.getStatus() == Status.ON_DUTY) {
                    secsInShift += period.getDuration();

                    // If the period started outside 24 hrs, but ended within 24 hrs
                } else if (period.getStartTime().after(dayAgo) && period.getStatus() == Status.ON_DUTY) {
                    secsInShift += (currentTime.getTime() - period.getStartTime().getTime()) / 1000;

                    // If the period ended within 24 hrs, but started before 24 hrs ago
                } else if (period.getEndTime() != null && period.getEndTime().after(dayAgo) && period.getStatus() == Status.DRIVING) {
                    secsInShift += Math.abs(currentTime.getTime() - dayAgo.getTime()) / 1000;

                }
            }
        }
        if((double)(secsDrivenToday / 3600) >= 10.5){
            // TODO:  alert the driver that they can only drive for 1/2 hour more

        }

        // Check for weekly hour limit, don't if the driver hasn't even driven 3 days in the past week
        if(currentDriver.getDays() != null && currentDriver.getDays().size() > 3){
            for(Day day : currentDriver.getDays()){
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
        // If the driver is not driving, then calculate how long is left in their break

        if(driverStatus.getStatus() != Status.DRIVING){
            for(int i = currentDriver.getLog().size()-1; i > 0; i--){
                if(currentDriver.getLog().get(i).getStatus() != Status.DRIVING){
                    if(currentDriver.getLog().get(i).getDuration() != 0){
                        secsLeftBreak -= currentDriver.getLog().get(i).getDuration();
                    }else if(currentDriver.getLog().get(i).getEndTime() == null){
                        secsLeftBreak -= getTime().getTime()/1000 - (currentDriver.getLog().get(i).getStartTime().getTime()/1000);
                    }
                }else{
                    break;
                }
            }
        }

        secsTillBreak -= concurrentSecsDriven;

        currentDriver.setSecsTillBreak(secsTillBreak);


        secsLeftDrivingToday -= secsDrivenToday;

        secsLeftDrivingToday = (secsLeftDrivingToday < 0) ? 0 : secsLeftDrivingToday;
        secsLeftBreak = (secsLeftBreak < 0) ? 0 : secsLeftBreak;

        currentDriver.setSecsInShift(secsInShift);
        currentDriver.setSecsLeftInShift(50400 - secsInShift);
        currentDriver.setSecsDrivenToday(secsDrivenToday);
        currentDriver.setConcurrentSecsDriven(concurrentSecsDriven);
        currentDriver.setSecsLeftDrivingToday(secsLeftDrivingToday);
        currentDriver.setSecsLeftInBreak(secsLeftBreak);

    }*/
    // This is called when a driver goes on duty
    /*public static void init(Driver driver){
        if(driver == null) return;

        if(!driver.equals(currentDriver)) {
            // Current driver went off duty.
            if(currentDriver != null){
                save(Status.OFF_DUTY);
            }
            currentDriver = driver;

        }

        if(driver.getDays() == null){
            currentDriver.setDays(new ArrayList<Day>());
        }

        if(driver.getLog() != null){
            log = driver.getLog();
            // The last time period was never terminated
            if(log.size() > 0 && log.get(log.size() - 1).getDuration() == 0){
                driverStatus = log.get(log.size() - 1);
            }else{
                driverStatus = new TimePeriod();
                driverStatus.setStartTime(getTime());
                driverStatus.setStatus(Status.OFF_DUTY);
                log.add(driverStatus);
            }
            Date twoDaysAgo = addHoursToDate(MainActivity.getTime(), -48);
            // TODO: Watch out for this
            for(int i = log.size() -1; i >= 0; i--){
                TimePeriod tp = log.get(i);
                if(tp.getEndTime() != null && !tp.getEndTime().after(twoDaysAgo)){
                    log.remove(tp);
                }
            }
        }else{
            log = new ArrayList<TimePeriod>();
            driverStatus = new TimePeriod();
            driverStatus.setStartTime(getTime());
            driverStatus.setStatus(Status.ON_DUTY);
            log.add(driverStatus);
            driver.setLog(log);
        }


        currentTime = getTime();
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                log((EngineData) msg.obj);
            }
        };
        boolean sameDay = false;
        for(Day day : driver.getDays()){
            try {
                    if(DataConverter.sameDayNoTime(MainActivity.getTime(), day.getDate())){
                        sameDay = true;
                        currentDay = day;
                    }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(!sameDay){
            currentDay = new Day(getTime());
        }
    }*/



    /*
            Getters for finding HOS info such as time till next break, time in cycle, etc
     */
    public static int getConcurrentSecsDriven() {
        return concurrentSecsDriven;
    }

    public static int getSecsTillBreak() {
        return secsTillBreak;
    }

    public static int getSecsDrivenToday() {
        return secsDrivenToday;
    }

    public static double getHoursDrivenThisWeek() {
        return hoursDrivenThisWeek;
    }


}
