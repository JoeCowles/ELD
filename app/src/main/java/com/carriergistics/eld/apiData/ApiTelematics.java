package com.carriergistics.eld.apiData;

import androidx.annotation.NonNull;

import com.carriergistics.eld.telematics.TelematicsEvent;
import com.carriergistics.eld.telematics.TelematicsStat;

import java.util.ArrayList;
import java.util.Date;

public class ApiTelematics {
    String eldID;
    String driverID;
    Date timeStamp;
    ArrayList<TelematicsEvent> events = new ArrayList<TelematicsEvent>();
    ArrayList<TelematicsStat> stats = new ArrayList<TelematicsStat>();

    public ApiTelematics(String eldID, String driverID, Date timeStamp, ArrayList<TelematicsEvent> events, ArrayList<TelematicsStat> stats){
        this.eldID = eldID;
        this.driverID = driverID;
        this.timeStamp = timeStamp;
        this.events = events;
        this.stats = stats;
    }

    @Override
    public String toString() {
        String eventStr = "";
        for(TelematicsEvent e : events){
            eventStr += e.toString() + "\n";
        }
        String statStr = "";
        for(TelematicsStat s : stats){
            statStr += s.toString() + "\n";
        }
        return String.format("<Telematics>\n\t" +
                "<ELD-ID> %s </ELD-ID> \n\t" +
                "<Driver-ID> %s </Driver-ID> \n\t" +
                "<Timestamp> %s </Timestamp> \n\t" +
                "<Events> \n\t\t" +
                "%s \n\t" +
                "</Events> \n" +
                "<Stats> \n\t\t" +
                "%s \n\t" +
                "</Stats> \n" +
                "</Telematics>", eldID, driverID, ApiDataFormatter.formatTimestamp(timeStamp), eventStr, statStr);

    }
}
