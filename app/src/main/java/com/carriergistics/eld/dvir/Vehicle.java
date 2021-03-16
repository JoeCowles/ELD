package com.carriergistics.eld.dvir;

public class Vehicle {
    private String name;
    private String vin;
    private boolean safe;

    public Vehicle(String name, String vin, boolean safe){
        this.name = name;
        this.vin = vin;
        this.safe = safe;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }
}
