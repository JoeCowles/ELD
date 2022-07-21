package com.carriergistics.eld.apiData;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;

@Root(name="GeoUpdate")
public class ApiGeolocationUpdate {

    @Element
    String eldID;
    @Element
    String driverID;
    @Element
    Date timeStamp;
    @Element
    GeoLocation location;

    public ApiGeolocationUpdate(@Element(name="ELD-ID")String eldID,
                                @Element(name="Driver-ID")String driverID,
                                @Element(name="Timestamp")Date timestamp,
                                @Element(name="Geolocation")GeoLocation location){
        this.eldID = eldID;
        this.driverID = driverID;
        this.timeStamp = timestamp;
        this.location = location;

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

    public GeoLocation getLocation() {
        return location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }
}
