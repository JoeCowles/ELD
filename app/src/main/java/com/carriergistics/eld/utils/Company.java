package com.carriergistics.eld.utils;

public class Company {
    private String name;
    private String dotNum;
    private String address;
    private String city;
    private String state;
    private int zip;
    private String phoneNum;

    public void setName(String name){
        this.name = name;
    }
    public void setDotNum(String dotNum){
        this.dotNum = dotNum;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setCity(String city){
        this.city = city;
    }
    public void setState(String state){
        this.state = state;
    }
    public void setZip(int zip){
        this.zip = zip;
    }
    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }
    public String getName(){
        return name;
    }
    public String getDotNum(){
        return dotNum;
    }
    public String getAddress(){
        return address;
    }
    public  String getCity(){
        return city;
    }
    public String getState(){
        return state;
    }
    public int getZip(){
        return zip;
    }
    public String getPhoneNum(){
        return phoneNum;
    }



}
