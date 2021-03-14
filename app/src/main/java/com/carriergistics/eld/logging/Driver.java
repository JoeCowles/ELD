package com.carriergistics.eld.logging;

import android.graphics.Bitmap;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.utils.DataConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Driver {

    private String licenseNum;
    private String first_name;
    private String last_name;
    private String email;

    // Might eliminate this, and use time periods, or might use this and eliminate time periods
    private HOSLog hosLog;

    private boolean currentDriver;

    // Logging variables
    private int secsLeftDrivingToday;
    private int secsTillBreak;
    private double hrsInCycle;
    private int secsDrivenToday;
    private int concurrentSecsDriven;
    private int secsLeftInBreak;

    private ArrayList<TimePeriod> log;
    private ArrayList<Day> days;

    // Used to convert secs to time Strings
    private int secs, mins, hrs;

    private Status status;

    private Bitmap signature;

    public Driver(){
        days = new ArrayList<Day>();
        log = new ArrayList<TimePeriod>();
    }
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

    public ArrayList<Day> getDays() {
        return days;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }

    public boolean isCurrentDriver() {
        return currentDriver;
    }

    public void setCurrentDriver(boolean currentDriver) {
        this.currentDriver = currentDriver;
    }

    public int getSecsDrivenToday() {
        return secsDrivenToday;
    }

    public void setSecsDrivenToday(int secsDrivenToday) {
        this.secsDrivenToday = secsDrivenToday;
    }

    public int getSecsTillBreak() {
        return secsTillBreak;
    }

    public void setSecsTillBreak(int secsTillBreak) {
        this.secsTillBreak = secsTillBreak;
    }

    public double getHrsInCycle() {
        return hrsInCycle;
    }

    public void setHrsInCycle(double hrsInCycle) {
        this.hrsInCycle = hrsInCycle;
    }

    public int getConcurrentSecsDriven() {
        return concurrentSecsDriven;
    }

    public void setConcurrentSecsDriven(int concurrentSecsDriven) {
        this.concurrentSecsDriven = concurrentSecsDriven;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getSecsLeftDrivingToday() {
        return secsLeftDrivingToday;
    }

    public void setSecsLeftDrivingToday(int secsLeftDrivingToday) {
        this.secsLeftDrivingToday = secsLeftDrivingToday;
    }

    public Bitmap getSignature() {
        return signature;
    }

    public void setSignature(Bitmap signature) {
        this.signature = signature;
    }

    public int getSecsLeftInBreak() {
        return secsLeftInBreak;
    }

    public void setSecsLeftInBreak(int secsLeftInBreak) {
        this.secsLeftInBreak = secsLeftInBreak;
    }
    public String getConcurrentTimeDriven(){
        return DataConverter.secsToTime(concurrentSecsDriven);
    }
    public String getTimeTillBreak(){
        return DataConverter.secsToTime(secsTillBreak);
    }
    public String getTimeLeftInBreak(){
        return DataConverter.secsToTime(secsLeftInBreak);
    }
    public String getTimeDrivenToday(){
        return DataConverter.secsToTime(secsDrivenToday);
    }



}
