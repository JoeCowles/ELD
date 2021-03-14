package com.carriergistics.eld.logging;

import android.util.Log;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.utils.DataConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Day {

    private Date date;

    private ArrayList<TimePeriod> timePeriods;

    private int secsDrivenToday = -1;

    public ArrayList<TimePeriod> getTimePeriods() {
        return timePeriods;
    }
    public Day(Date date){
        this.date = date;
        timePeriods = new ArrayList<>();
    }

    // @param Date in mm-dd-yyyy
    public Day(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("mm-dd-yy HH:mm:ss");
        this.date = sdf.parse(date + " 00:00:00");
        timePeriods = new ArrayList<>();
    }

    public void setTimePeriods(ArrayList<TimePeriod> timePeriods) {
        this.timePeriods = timePeriods;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSecsDrivenToday() {
        secsDrivenToday = 0;
        if(timePeriods != null){
            for(TimePeriod t : timePeriods){
                if(t.getStatus() == Status.DRIVING){
                    secsDrivenToday += t.getDuration();
                    if(t.getDuration() <= 0){
                        Log.d("LOGGING", "The current time period has length: " + ((MainActivity.getTime().getTime()/1000) - (t.getStartTime().getTime()/1000)));
                        secsDrivenToday += (MainActivity.getTime().getTime()/1000) - (t.getStartTime().getTime()/1000);
                    }
                }
            }
        }else{
           return 0;
        }
        return secsDrivenToday;
    }
    public boolean updateTimePeriods(){
        if(date != null){
            try {
                timePeriods = HOSLogger.getLog("  " + date.toString().substring(0,10) + ", " + date.toString().substring(24,28));
            } catch (ParseException e) {
                Log.d("LOGGING", "The update didnt work");
                return false;
            }
        }
        Log.d("LOGGING", "Got new times: " + timePeriods.toString());
        return true;
    }
    public void setSecsDrivenToday(int secsDrivenToday) {
        this.secsDrivenToday = secsDrivenToday;
    }
    public void addTimePeriod(TimePeriod t){
        timePeriods.add(t);
    }
}
