package com.carriergistics.eld.logging;

import android.os.Message;
import android.util.Log;

import com.carriergistics.eld.bluetooth.TelematicsData;
import com.carriergistics.eld.ui.LogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HOSLogger {
    static ArrayList<HOSEvent> HOSEvents;
    static HOSEvent currentHOSEvent;
    public HOSLogger(ArrayList<HOSEvent> HOSEvents){
        this.HOSEvents = HOSEvents;
    }
    public static void log(TelematicsData data){
        if(currentHOSEvent == null){
            // Initialize as off duty that way it will send an off duty event, then start driving
            currentHOSEvent = new HOSEvent(HOSEventCodes.OFF_DUTY);
        }

        if(HOSEvents == null){
            HOSEvents = new ArrayList<HOSEvent>();
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

            if(currentHOSEvent.getCode() == HOSEventCodes.DRIVING){
                //Stopped driving
                //TODO: Prompt user to choose why they stopped
                Log.d("DEBUGGING", "Stopped driving ----------------------");
                sendOBDEvent(HOSEventCodes.OFF_DUTY);
            }
        }
    }
    private static void sendOBDEvent(int code){
        Log.d("DEBUGGING", "Sending event with code: " + code);
        currentHOSEvent.setEndTime(getTime());
        currentHOSEvent.setType(HOSEventCodes.CHANGE_EVENT_TYPE);
        HOSEvents.add(currentHOSEvent);
        // TODO: Send Event
        currentHOSEvent = new HOSEvent(code);
        resetTimer();
    }

    private static Date getTime(){
        //TODO: Get the current time from an api
        return Calendar.getInstance().getTime();
    }

    // Reset the timer that is used to send periodic updates every hour
    private static void resetTimer(){
        // TODO: Periodic events
    }
    public static HOSLog getLog(){
        return new HOSLog(HOSEvents);
    }
    public static HOSLog getDebuggingLog(){
        HOSLog log = new HOSLog();
        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        for(int i = 0; i < 20; i++){
            cal.add(Calendar.HOUR, 1);
            HOSEvent event = new HOSEvent();
            int code = ((i % 2) == 0) ? 1 : 3;
            event.setCode(code);
            event.setType(HOSEventCodes.CHANGE_EVENT_TYPE);
            event.setEndTime(cal.getTime());
            log.getLog().add(event);
        }
        return log;
    }

}
