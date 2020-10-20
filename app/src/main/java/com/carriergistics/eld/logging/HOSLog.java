package com.carriergistics.eld.logging;

import java.util.ArrayList;

public class HOSLog {
    private ArrayList<HOSEvent> log;
    public HOSLog(){log = new ArrayList<>();}
    public HOSLog(ArrayList<HOSEvent> log){
        setLog(log);
    }
    public void setLog(ArrayList<HOSEvent> log){
        this.log = log;
    }
    public ArrayList<HOSEvent> getLog(){
        return log;
    }

}
