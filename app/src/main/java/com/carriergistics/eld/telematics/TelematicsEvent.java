package com.carriergistics.eld.telematics;


import com.carriergistics.eld.apiData.ApiDataFormatter;
import com.carriergistics.eld.apiData.GeoLocation;

import java.util.Date;

public class TelematicsEvent {
    String type;
    String data;
    GeoLocation location;
    Date timeStamp;
    public TelematicsEvent(String type, String data, GeoLocation location, Date timestamp){
        this.type = type;
        this.data = data;
        this.location = location;
        this.timeStamp = timestamp;
    }
    @Override
    public String toString() {
        return String.format("<TelematicsEvent type=\"%s\", data=\"%s\">" +
                "\n\t %s" +
                "\n\t <Timestamp> %s </Timestamp> \n" +
                "</TelematicsEvent>", type, data, location.toString(), ApiDataFormatter.formatTimestamp(timeStamp));
    }
}
