package com.carriergistics.eld.logging;

import com.carriergistics.eld.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Driver {
    private String licenseNum;
    private String first_name;
    private String last_name;
    private String email;
    private HOSLog hosLog;
    private ArrayList<TimePeriod> log;
    private ArrayList<Day> week;
    private boolean currentDriver;

    private Status status;
    public Driver(){}
    public void setLicenseNum(String licenseNum){
        this.licenseNum = licenseNum;
    }
    public void setFirst_name(String first_name){
        this.first_name = first_name;
    }
    public void setLast_name(String last_name){
        this.last_name = last_name;
    }
    public void setHosLog(HOSLog hosLog){this.hosLog = hosLog;}
    public void setEmail(String email){this.email = email;}

    public String getLicenseNum(){
        return licenseNum;
    }
    public String getFirst_name(){
        return first_name;
    }
    public String getLast_name(){
        return last_name;
    }
    public String getEmail(){return email;}

    public HOSLog getHosLog(){return hosLog;}
    public Status getStatus(){return status;}
    public void setStatus(){this.status = status;}

    public ArrayList<TimePeriod> getLog() {
        return log;
    }

    public void setLog(ArrayList<TimePeriod> log) {
        this.log = log;
    }

    protected Date getTime(){
        // TODO: get time from api
        return MainActivity.getTime();
    }

    public ArrayList<Day> getWeek() {
        return week;
    }

    public void setWeek(ArrayList<Day> week) {
        this.week = week;
    }

    public boolean isCurrentDriver() {
        return currentDriver;
    }

    public void setCurrentDriver(boolean currentDriver) {
        this.currentDriver = currentDriver;
    }
}
