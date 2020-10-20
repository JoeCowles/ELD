package com.carriergistics.eld.logging;

import java.util.ArrayList;

public class Driver {
    private String licenseNum;
    private String first_name;
    private String last_name;
    private ArrayList<HOSLog> hosLogs;
    public Driver(){}
    public void setLicenseNum(String licenseNum){
        this.licenseNum = licenseNum;
    }
    public void setFirst_name(String first_name){
        this.first_name = first_name;
    }
    public void setLast_name(String last_name){
        this.last_name = last_name;
    }
    public void setLogs(ArrayList<HOSLog> hosLogs){this.hosLogs = hosLogs;}

    public String getLicenseNum(){
        return licenseNum;
    }
    public String getFirst_name(){
        return first_name;
    }
    public String getLast_name(){
        return last_name;
    }
    public ArrayList<HOSLog> getHOSLogs(){return hosLogs;}
}
