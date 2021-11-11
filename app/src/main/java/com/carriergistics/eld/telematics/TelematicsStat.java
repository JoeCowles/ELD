package com.carriergistics.eld.telematics;

import androidx.annotation.NonNull;

public class TelematicsStat {
    String type;
    String data;
    public TelematicsStat(String type, String data){
        this.type = type;
        this.data = data;

    }

    @NonNull
    @Override
    public String toString() {
        return String.format("<Stat type =\"%s\" data=\"%s\"/>");
    }
}
