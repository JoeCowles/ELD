package com.carriergistics.eld.logging;

import java.util.ArrayList;
import java.util.Date;

public class Day {

    private Date date;

    private ArrayList<TimePeriod> timePeriods;

    private  double secsDrivenToday = -1;

    public ArrayList<TimePeriod> getTimePeriods() {
        return timePeriods;
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

    public void endDay(){
        for(TimePeriod  t : timePeriods){
            secsDrivenToday += t.getDuration()/60;
        }
    }

    public double getSecsDrivenToday() {
        return secsDrivenToday;
    }

    public void setSecsDrivenToday(double secsDrivenToday) {
        this.secsDrivenToday = secsDrivenToday;
    }
}
