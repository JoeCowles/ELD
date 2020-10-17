package com.carriergistics.eld.bluetooth;

public class TelematicsData {
    private int speed;
    private int milesDriven;
    private float mpg;
    private int rpm;
    private String gear;
    private String runTime;
    public TelematicsData(){

    }
    public TelematicsData(int speed, int rpm, int milesDriven, float mpg, String runTime){
        this.rpm = rpm;
        this.speed = speed;
        this.milesDriven = milesDriven;
        this.mpg = mpg;
        this.runTime = runTime;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
    public int getSpeed(){
        return speed;
    }
    public void setMilesDriven(int milesDriven){
        this.milesDriven = milesDriven;
    }
    public double getMilesDriven(){
        return milesDriven;
    }
    public void setMpg(){
        this.mpg = mpg;
    }
    public void setRpm(int rpm){
        this.rpm = rpm;
    }
    public int getRpm(){
        return rpm;
    }
    public void setGear(String gear){
        this.gear = gear;
    }
    public String getGear(){
        return gear;
    }
    public void setRunTime(String runTime){
        this.runTime = runTime;
    }
    public int getRunTime(){
        //TODO: Make sure that runtime is only a number with no prefix/suffix
        return Integer.parseInt(runTime);
    }
}
