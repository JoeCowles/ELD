package com.carriergistics.eld.logging;

import java.util.Date;

public class TimePeriod {

    private Status status;
    private Date startTime;
    private Date endTime;
    // duration of the time period in seconds
    private long duration = 0;


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

}
