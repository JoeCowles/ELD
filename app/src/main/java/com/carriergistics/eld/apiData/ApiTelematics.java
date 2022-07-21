package com.carriergistics.eld.apiData;

import androidx.annotation.NonNull;

import com.carriergistics.eld.telematics.TelematicsEvent;
import com.carriergistics.eld.telematics.TelematicsStat;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Date;

@Root(name="telematics")
public class ApiTelematics {
    @Element
    String eldID;
    @Element
    String driverID;
    @Element
    Date timeStamp;
    @ElementList
    ArrayList<TelematicsEvent> events = new ArrayList<TelematicsEvent>();
    @ElementList
    ArrayList<TelematicsStat> stats = new ArrayList<TelematicsStat>();

    public ApiTelematics(@Element(name="eldID") String eldID,
                         @Element(name="driverID") String driverID,
                         @Element(name="timeStamp") Date timeStamp,
                         @Element(name="events") ArrayList<TelematicsEvent> events,
                         @Element(name="stats") ArrayList<TelematicsStat> stats){
        this.eldID = eldID;
        this.driverID = driverID;
        this.timeStamp = timeStamp;
        this.events = events;
        this.stats = stats;
    }

    public String getEldID() {
        return eldID;
    }

    public void setEldID(String eldID) {
        this.eldID = eldID;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ArrayList<TelematicsEvent> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<TelematicsEvent> events) {
        this.events = events;
    }

    public ArrayList<TelematicsStat> getStats() {
        return stats;
    }

    public void setStats(ArrayList<TelematicsStat> stats) {
        this.stats = stats;
    }
}
