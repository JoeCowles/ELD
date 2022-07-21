package com.carriergistics.eld.logging;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.mapping.LocationReading;

import java.util.Date;

public class TimePeriod {

    private Status status;

    private Date startTime;
    private Date endTime;

    private LocationReading startLoc;
    private LocationReading endLoc;

    // duration of the time period in seconds
    private long duration = 0;

    private String note = "";


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
        calcDuration();
    }

    public long getDuration() {
        if(endTime == null){
            return Math.abs((getStartTime().getTime() - MainActivity.getTime().getTime())/1000);
        }
        return duration;
    }

    private void setDuration(long duration) {
        this.duration = duration;
    }

    private void calcDuration(){
        if(startTime != null){
            setDuration(Math.abs(getStartTime().getTime() - getEndTime().getTime())/1000);
        }
    }

    public LocationReading getStartLoc() {
        return startLoc;
    }

    public void setStartLoc(LocationReading startLoc) {
        this.startLoc = startLoc;
    }

    public LocationReading getEndLoc() {
        return endLoc;
    }

    public void setEndLoc(LocationReading endLoc) {
        this.endLoc = endLoc;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
