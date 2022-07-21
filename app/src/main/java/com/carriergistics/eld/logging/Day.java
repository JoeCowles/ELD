package com.carriergistics.eld.logging;

import android.util.Log;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.editlog.Edit;
import com.carriergistics.eld.utils.DataConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Day {

    private Date date;

    private ArrayList<TimePeriod> timePeriods;
    private ArrayList<Edit> edits;

    private int secsDrivenToday = -1;


    public Day(Date date){
        this.date = date;
        timePeriods = new ArrayList<>();
    }
    public ArrayList<TimePeriod> getTimePeriods() {
        return timePeriods;
    }
    // @param Date follows mm-dd-yyy hh:mm:ss
    public Day(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("mm-dd-yy HH:mm:ss");
        this.date = DataConverter.removeTime(sdf.parse(date + " 00:00:00"));
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

    // Changed from secs driven today
    public int getSecsPerformed(Status status) {
        secsDrivenToday = 0;
        if(timePeriods != null){
            for(TimePeriod t : timePeriods){
                if(t.getStatus() == status){
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
    public void addTimePeriod(TimePeriod t){
        timePeriods.add(t);
    }

    public ArrayList<Edit> getEdits() {
        return edits;
    }

    public void setEdits(ArrayList<Edit> edits) {
        this.edits = edits;
    }

    public void addEdit(Edit edit){
        if(edits != null){
            edits.add(edit);
        }
    }
}
