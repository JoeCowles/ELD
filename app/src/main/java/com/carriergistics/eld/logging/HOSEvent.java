package com.carriergistics.eld.logging;

import java.util.Date;

public class HOSEvent {

    private Driver driver;
    private Date endTime;
    private Date startTime;
    private int status;
    private int event_type;
    private double avgSpeed;
    private int miles;
    private int totalMins;
    private long lat;
    private long lon;

    public HOSEvent(){}
    public HOSEvent(Date startTime){
        this.startTime = startTime;
    }

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
        totalMins = minutesDiff(startTime, endTime);
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

    public int getMins(){
        return totalMins;
    }

    public Date getStartTime(){
        return startTime;
    }

    public void setStartTime(Date startTime){
        this.startTime = startTime;
    }

    public void setLat(long lat){
        this.lat = lat;
    }
    public void setLong(long lon){
        this.lon = lon;
    }
    public long getLat(){
        return lat;
    }
    public long getLong(){
        return lon;
    }

    /*
    *
    * Used to get the mins between two dates
    *
    * */
    public static int minutesDiff(Date earlierDate, Date laterDate) {
        if( earlierDate == null || laterDate == null ) return 0;

        return (int)((laterDate.getTime()/60000) - (earlierDate.getTime()/60000));
    }

}
