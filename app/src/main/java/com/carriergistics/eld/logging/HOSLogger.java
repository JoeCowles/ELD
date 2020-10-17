package com.carriergistics.eld.logging;

import android.util.Log;

import com.carriergistics.eld.bluetooth.TelematicsData;

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
                sendOBDEvent(HOSEventCodes.DRIVING);
            }
        }else{
            //NOT DRIVING

            if(currentHOSEvent.getCode() == HOSEventCodes.DRIVING){
                //Stopped driving
                //TODO: Prompt user to choose why they stopped
                sendOBDEvent(HOSEventCodes.OFF_DUTY);
            }
        }
    }
    private static void sendOBDEvent(int code){
        Log.d("DEBUGGING", "Sending event with code: " + code);
        if(currentHOSEvent != null){
            HOSEvents.add(currentHOSEvent);
        }
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
    public static ArrayList<HOSEvent> getLog(){
        return HOSEvents;
    }
}
