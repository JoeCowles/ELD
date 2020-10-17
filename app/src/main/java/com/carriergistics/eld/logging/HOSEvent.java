package com.carriergistics.eld.logging;

import java.util.Date;

public class HOSEvent {

    private Driver driver;
    private Date endTime;
    private int status;
    private int event_type;
    private double avgSpeed;
    private int miles;
    public HOSEvent(){}
    public HOSEvent(Date endTime, int status){
        this.endTime = endTime;
        this.status = status;
    }
    public HOSEvent(int status){
        this.status = status;
    }
    public Date getEndTime(){
        return endTime;
    }
    public void setEndTime(Date endTime){
        this.endTime = endTime;
    }
    public void setCode(int status ){
        this.status = status;
    }
    public int getCode(){
        return status;
    }
    public void setAvgSpeed(double avgSpeed){
        this.avgSpeed = avgSpeed;
    }
    public double getAvgSpeed(){
        return avgSpeed;
    }
    public int getMiles(){
        return miles;
    }
    public void setMiles(int miles){
        this.miles = miles;
    }
    public Driver getDriver(){
        return driver;
    }
    public void setDriver(Driver driver){
        this.driver = driver;
    }
    public void setType(int type){
        this.event_type = type;
    }
    public int getType(){
        return event_type;
    }

}
