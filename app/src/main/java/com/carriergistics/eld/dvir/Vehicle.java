package com.carriergistics.eld.dvir;

import java.util.ArrayList;

public class Vehicle {
    private String name;
    private String vin;
    private boolean safe;
    private int odo;
    private ArrayList<Dvir> dvirLog;

    public Vehicle(String name, String vin, boolean safe){
        this.name = name;
        this.vin = vin;
        this.safe = safe;
        dvirLog = new ArrayList<Dvir>();
    }
    public void setOdo(int odo){
        this.odo = odo;
    }
    public int getOdo(){
        return odo;
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

    public ArrayList<Dvir> getDvirLog() {
        return dvirLog;
    }

    public void setDvirLog(ArrayList<Dvir> dvirLog) {
        this.dvirLog = dvirLog;
    }
}
