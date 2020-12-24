package com.carriergistics.eld.logging;

import java.util.ArrayList;

public class Driver {
    private String licenseNum;
    private String first_name;
    private String last_name;
    private String email;
    private HOSLog log;
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
    public void setLog(HOSLog hosLog){this.log = hosLog;}
    public void setEmail(String email){this.email = email;}

    public String getLicenseNum(){
        return licenseNum;
    }
    public String getFirst_name(){
        return first_name;
    }
    public String getLast_name(){
        return last_name;
    }
    public String getEmail(){return email;}

    public HOSLog getLog(){return log;}
}
