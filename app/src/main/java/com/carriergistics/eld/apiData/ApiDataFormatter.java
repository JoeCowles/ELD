package com.carriergistics.eld.apiData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ApiDataFormatter {

    public static String formatTimestamp(Date timeStamp){
        SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy hh:mm:ss");
        final TimeZone utc = TimeZone.getTimeZone("UTC");
        sdf.setTimeZone(utc);
        return sdf.format(timeStamp);
    }
}
