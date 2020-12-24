package com.carriergistics.eld.utils;

public class Settings {
    private String deviceAddress = "";
    private String devicePassword = "";
    private String deviceName = "";
    public void setDeviceName(String deviceName){
        this.deviceName = deviceName;
    }
    public void setDevicePassword(String password){
        this.devicePassword = password;
    }
    public void setDeviceAddress(String deviceAddress){
        this.deviceAddress = deviceAddress;
    }
    public String getDeviceName(){
        return deviceName;
    }
    public String getDevicePassword(){
        return devicePassword;
    }
    public String getDeviceAddress(){
        return deviceAddress;
    }

}
