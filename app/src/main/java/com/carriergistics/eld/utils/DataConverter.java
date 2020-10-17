package com.carriergistics.eld.utils;

public class DataConverter {
    static int lastSpeed;
    // Returns a speed from a hex string
    public static int speedMPH(String hex){
        int speed;
        if(!hex.isEmpty()){
            speed = Integer.parseInt(hex, 16);
            lastSpeed = (int)((speed/256) * 0.6214);
        }else{
            return lastSpeed;
        }
        return (int)((speed/256) * 0.6214);
    }
}
