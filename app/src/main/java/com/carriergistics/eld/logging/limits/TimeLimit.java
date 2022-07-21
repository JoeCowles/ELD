package com.carriergistics.eld.logging.limits;

import com.carriergistics.eld.logging.Status;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TimeLimit {

    String name;

    // used to track the time that is actually performed
    int secsPerformed;
    int secsRenewed;

    // the constants that display the time that the time limit is actually for
    int secsToRenew;
    int secsToPerform;

    ArrayList<Status> renewingStatuses;
    ArrayList<Status> depletingStatuses;
    ArrayList<Alert> alerts;


    public TimeLimit(String name, int secsPerformed, int secsToRenew) {
        this.name = name;
        this.secsPerformed = secsPerformed;
        this.secsToRenew = secsToRenew;
    }

    public ArrayList<Status> getRenewingStatuses() {
        return renewingStatuses;
    }

    public void setRenewingStatuses(ArrayList<Status> renewingStatuses) {
        this.renewingStatuses = renewingStatuses;
    }

    public ArrayList<Status> getDepletingStatuses() {
        return depletingStatuses;
    }

    public void setDepletingStatuses(ArrayList<Status> depletingStatuses) {
        this.depletingStatuses = depletingStatuses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSecsPerformed() {
        return secsPerformed;
    }
    public int getSecsToRenew() {
        return secsToRenew;
    }
    public int getSecsToPerform(){
        return secsToPerform;
    }
    public int getSecsRenewed(){
        return secsRenewed;
    }
    public void setSecsPerformed(int secsPerformed){
        this.secsPerformed = secsPerformed;
    }
    public void setSecsRenewed(int secsRenewed){
        this.secsRenewed = secsRenewed;
    }

    public ArrayList<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(ArrayList<Alert> alerts) {
        this.alerts = alerts;
    }

    public void resetAlerts(){
        for(Alert alert : alerts){
            alert.executed = false;
        }
    }



}