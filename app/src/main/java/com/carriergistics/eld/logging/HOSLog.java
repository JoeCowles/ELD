package com.carriergistics.eld.logging;

import java.util.ArrayList;
import java.util.Date;

public class HOSLog {
    private ArrayList<HOSEvent> log;
    private Date date;

    public HOSLog(){
        log = new ArrayList<>();
    }

    public HOSLog(ArrayList<HOSEvent> log){
        setLog(log);
    }

    public void setLog(ArrayList<HOSEvent> log){
        this.log = log;
    }

    public ArrayList<HOSEvent> getLog(){
        return log;
    }

    public Date getStartDate(){
        return  date;
    }

    public void setStartDate(Date date){
        this.date = date;
    }

}

