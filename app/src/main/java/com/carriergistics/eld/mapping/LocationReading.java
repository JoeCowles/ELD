package com.carriergistics.eld.mapping;

public class LocationReading {

    double lat;
    double lon;
    double milesSinceLast;
    boolean enteredByUser;


    public LocationReading(double lat, double lon, double milesSinceLast){
        this.lat = lat;
        this.lon = lon;
        this.milesSinceLast = milesSinceLast;
        enteredByUser = false;
    }

    public LocationReading(double lat, double lon, double milesSinceLast, boolean enteredByUser){
        this.lat = lat;
        this.lon = lon;
        this.milesSinceLast = milesSinceLast;
        this.enteredByUser = enteredByUser;
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

    public double getMilesSinceLast() {
        return milesSinceLast;
    }

    public void setMilesSinceLast(double milesSinceLast) {
        this.milesSinceLast = milesSinceLast;
    }

    public boolean isEnteredByUser() {
        return enteredByUser;
    }

    public void setEnteredByUser(boolean enteredByUser) {
        this.enteredByUser = enteredByUser;
    }
}
