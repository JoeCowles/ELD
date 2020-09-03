package com.carriergistics.eld.utils;
import java.util.Date;

public class TimePeriod {
    private Date startTime;
    private Date endTime;
    private DriverStatus mType;
    private double avgSpeed;
    public TimePeriod(){};
    public TimePeriod(Date startTime, DriverStatus type){
        this.startTime = startTime;
        mType = type;
    }
    public Date getStartTime(){
        return startTime;
    }
    public Date getEndTime(){
        return endTime;
    }
    public void setStartTime(Date startTime){
        this.startTime = startTime;
    }
    public void setEndTime(Date endTime){
        this.endTime = endTime;
    }
    public void setRealStartTime(Date realStartTime){
        this.startTime = startTime;
    }
    public void setType(DriverStatus type){
        mType = type;
    }
    public DriverStatus getType(){
        return mType;
    }
    public void setAvgSpeed(double avgSpeed){
        this.avgSpeed = avgSpeed;
    }
    public double getAvgSpeed(){
        return avgSpeed;
    }
}
