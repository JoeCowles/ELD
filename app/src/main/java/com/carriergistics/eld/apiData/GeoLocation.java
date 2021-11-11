package com.carriergistics.eld.apiData;

import java.util.Date;

public class GeoLocation {
    double lat;
    double lon;
    Date timeStamp;

    public GeoLocation(double lat, double lon, Date timeStamp){
        this.lat = lat;
        this.lon = lon;
        this.timeStamp = timeStamp;
    }
    @Override
    public String toString(){
        return String.format("<GeoLocation> \n\t" +
                "<lat> %f </lat> \n\t" +
                "<long> %f </long> \n\t" +
                "<TimeStamp> %s <Timestamp> \n" +
                "</GeoLocation>", lat, lon, ApiDataFormatter.formatTimestamp(timeStamp));
    }

}
