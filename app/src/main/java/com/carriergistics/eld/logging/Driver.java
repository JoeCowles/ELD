package com.carriergistics.eld.logging;

import android.graphics.Bitmap;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.logging.limits.TimeLimit;
import com.carriergistics.eld.utils.DataConverter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Driver {

    private String licenseNum;
    private String first_name;
    private String last_name;
    private String email;


    private boolean currentDriver;

    // Logging variables
    private int secsLeftDrivingToday;
    private int secsTillBreak;
    private double hrsInCycle;
    private int secsDrivenToday;
    private int concurrentSecsDriven;
    private int secsLeftInBreak;
    private int secsInShift;
    private int secsLeftInShift;

    private Trip trip;


    private TimePeriod currentTimePeriod;
    // Is a stack, meaning that the current day is at index = 0 and the latest day is at the last index
    private ArrayList<Day> days;
    private ArrayList<TimeLimit> timeLimits;

    private Status status;

    private String signaturePath;

    public Driver(){
        days = new ArrayList<Day>();
        timeLimits = new ArrayList<TimeLimit>();
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

    public Status getStatus(){return status;}
    public void setStatus(){this.status = status;}


    public ArrayList<Day> getDays() {
        return days;
    }

    public void addDay(Day day){
        days.add(0, day);
    }
    public Day getYesterday() throws ParseException {
        if(days.size() < 2){
            return null;
        }
        if(DataConverter.sameDayNoTime(DataConverter.addHoursToDate(DataConverter.removeTime(MainActivity.getTime()), -24), days.get(1).getDate())){
            return days.get(1);
        }
        return null;
    }
    // Searches for a day by its date and returns it
    public Day getDay(Date date) throws ParseException {
        for(Day day : days){
            if(DataConverter.sameDayNoTime(day.getDate(), date)){
                return day;
            }
        }
        return null;
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

    public String getSignaturePath() {
        return signaturePath;
    }

    public void setSignature(String signaturePath) {
        this.signaturePath = signaturePath;
    }

    public int getSecsLeftInBreak() {
        return secsLeftInBreak;
    }

    public void setSecsLeftInBreak(int secsLeftInBreak) {
        this.secsLeftInBreak = secsLeftInBreak;
    }

    public int getSecsInShift() {
        return secsInShift;
    }

    public void setSecsInShift(int secsInShift) {
        this.secsInShift = secsInShift;
    }

    public int getSecsLeftInShift() {
        return secsLeftInShift;
    }

    public void setSecsLeftInShift(int secsLeftInShift) {
        this.secsLeftInShift = secsLeftInShift;
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
    public String getTimeLeftDrivingToday(){
        return DataConverter.secsToTime(secsLeftDrivingToday);
    }
    public String getTimeLeftInShift(){
        if(secsLeftInShift <= 0){
            return DataConverter.secsToTime(0);
        }
        return DataConverter.secsToTime(secsLeftInShift);
    }
    public String getTimeInShift(){
        return DataConverter.secsToTime(secsInShift);
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public TimePeriod getCurrentTimePeriod() {
        return currentTimePeriod;
    }

    public void setCurrentTimePeriod(TimePeriod currentTimePeriod) {
        this.currentTimePeriod = currentTimePeriod;
    }

    public ArrayList<TimeLimit> getTimeLimits() {
        return timeLimits;
    }

    public void setTimeLimits(ArrayList<TimeLimit> timeLimits) {
        this.timeLimits = timeLimits;
    }
}
