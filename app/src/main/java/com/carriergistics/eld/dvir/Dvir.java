package com.carriergistics.eld.dvir;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Date;

public class Dvir {

    enum TripType{
        PRE_TRIP,
        POST_TRIP
    }
    enum Safety{
        UNSAFE,
        SAFE,
        RESOLVED
    }

    private TripType type;
    private Date date;
    private String remarks;
    private Safety safety;
    private ArrayList<Issue> issues;

    public Dvir(TripType type, Date date, Safety safety){
        this.type = type;
        this.date = date;
        this.safety = safety;
        issues = new ArrayList<>();
    }

    public TripType getType() {
        return type;
    }

    public void setType(TripType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Safety getSafety() {
        return safety;
    }

    public void setSafety(Safety safety) {
        this.safety = safety;
    }

    public ArrayList<Issue> getIssues() {
        return issues;
    }

    public void setIssues(ArrayList<Issue> issues) {
        this.issues = issues;
    }
}
