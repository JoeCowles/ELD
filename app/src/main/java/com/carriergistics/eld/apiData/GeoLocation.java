package com.carriergistics.eld.apiData;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;

@Root(name="Geolocation")
public class GeoLocation {
    @Element
    double lat;
    @Element
    double lon;
    @Element
    Date timeStamp;

    public GeoLocation(@Element(name="lat") double lat,
                       @Element(name="lon")double lon,
                       @Element(name="Timestamp") Date timeStamp){
        this.lat = lat;
        this.lon = lon;
        this.timeStamp = timeStamp;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
