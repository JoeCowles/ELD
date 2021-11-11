package com.carriergistics.eld.logging;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.Ticker;
import com.carriergistics.eld.bluetooth.BluetoothConnector;
import com.carriergistics.eld.bluetooth.TelematicsData;
import com.carriergistics.eld.ui.HomeFragment;
import com.carriergistics.eld.ui.StoppedFragment;
import com.carriergistics.eld.utils.DataConverter;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class HOSLogger {

    static HOSLog currentHOSLog;
    public static  ArrayList<TimePeriod> log;
    static Day currentDay;
    private static Driver currentDriver;
    static TimePeriod driverStatus;
    static HOSEvent currentHOSEvent;

    public static TelematicsData data;
    static Date currentTime;


    static int secsDrivenToday;
    static double hoursDrivenThisWeek = 0;
    static int concurrentSecsDriven = 0;
    private static int secsTillBreak;
    public static Handler handler;
    private static int secsLeftBreak;
    private static int secsInShift;

    private static int lastSpeed = 0;
    private static int rpmGlitched = 0;
    private static int lastRpm = 0;

    private static int speed;
    // This is called when a driver goes on duty
    public static void init(Driver driver){
        if(driver == null){
            return;
        }
        if(!driver.equals(currentDriver)) {
            // Current driver went off duty.
            if(currentDriver != null){
                save(Status.OFF_DUTY);
            }
            currentDriver = driver;

        }
        if(driver.getHosLog() != null){
            currentHOSLog = driver.getHosLog();
        }else{
            currentHOSLog = new HOSLog();
            currentDriver.setHosLog(currentHOSLog);
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
                log((TelematicsData) msg.obj);
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
    }
    // Determines if the driver is driving, then deals with it accordingly
    public static void log(TelematicsData _data){
        
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
        }
        /*

                    Proccess the info coming from the ECU

         */
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
    }
    private static void sendOBDEvent(int code){

        Log.d("DEBUGGING", "Sending event with code: " + code);
        currentHOSEvent.setEndTime(currentTime);
        currentHOSEvent.setType(HOSEventCodes.CHANGE_EVENT_TYPE);
        currentHOSLog.getLog().add(currentHOSEvent);
        // TODO: Send TelematicsEvent
        currentHOSEvent = new HOSEvent(code);
        currentHOSEvent.setStartTime(currentTime);

    }
    public static int getSpeed(){
        return speed;
    }
    private static void sendOBDEvent(int code, long lat, long lon){

        Log.d("DEBUGGING", "Sending event with code: " + code);
        currentHOSEvent.setEndTime(currentTime);
        currentHOSEvent.setType(HOSEventCodes.CHANGE_EVENT_TYPE);
        currentHOSLog.getLog().add(currentHOSEvent);
        // TODO: Send TelematicsEvent
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
    public static void checkAlerts(){

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

    }

    public static Date addHoursToDate(Date date, int hours) {
        if(date == null){
            return MainActivity.getTime();
        }
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
        currentDriver.setStatus(status);
        if(log != null){
            log.add(driverStatus);
        }
    }

    public static ArrayList<TimePeriod> getLog(){
        ArrayList<TimePeriod> temp = new ArrayList<TimePeriod>();
        for(TimePeriod t : log){
            // Make sure that the event was within 24 hrs
            if(t.getEndTime() == null && (t.getStartTime() != null && t.getStartTime().after(addHoursToDate(getTime(), -24))) || (t.getEndTime()!= null && t.getEndTime().after(addHoursToDate(getTime(), -24)))){
                if(t.getStartTime().after(addHoursToDate(getTime(), -24))){
                    temp.add(t);
                }
            }
            Log.d("DEBUGGING", t.getStartTime() + "  " + t.getEndTime() + " " + t.getStatus());
        }
        return temp;
    }

    public static ArrayList<TimePeriod> getLog(String date) throws ParseException {
        Log.d("LOGGING", "The log was requested for day: " + date);
        ArrayList<TimePeriod> temp = new ArrayList<TimePeriod>();
        SimpleDateFormat sdf = new SimpleDateFormat("  EEE MMM dd, yyyy");
        Date day = sdf.parse(date);
        for(TimePeriod t : log){
            // Make sure that the event was within 24 hrs of the day
            if( t.getEndTime() != null && DataConverter.sameDayNoTime(day, t.getEndTime())){
                if(DataConverter.sameDayNoTime(t.getStartTime(), day)){
                    temp.add(t);
                }else{
                    TimePeriod tp = new TimePeriod();
                    tp.setStartTime(day);
                    tp.setEndTime(t.getEndTime());
                    tp.setStatus(t.getStatus());
                    temp.add(tp);
                }
            }
        }
        return temp;
    }

    public static ArrayList<TimePeriod> getLog(Date day) throws ParseException {
        ArrayList<TimePeriod> temp = new ArrayList<TimePeriod>();
        for(TimePeriod t : log){
            // Make sure that the event was within 24 hrs of the day
            if( t.getEndTime() != null && DataConverter.sameDayNoTime(day, t.getEndTime())){
                if(DataConverter.sameDayNoTime(t.getStartTime(), day)){
                    temp.add(t);
                }else{
                    TimePeriod tp = new TimePeriod();
                    tp.setStartTime(day);
                    tp.setEndTime(t.getEndTime());
                    tp.setStatus(t.getStatus());
                    temp.add(tp);
                }
            }
        }
        return temp;
    }
    // TODO: Not sure these are necessary, will delete later
    public static void startOnDuty(){
        save(Status.ON_DUTY);
    }
    public static void startOffDuty(){
        save(Status.OFF_DUTY);
    }
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
