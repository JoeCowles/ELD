package com.carriergistics.eld.apiData;

import java.util.Date;

public class ApiGeolocationUpdate {
    String eldID;
    String driverID;
    Date timeStamp;

    GeoLocation location;
    public ApiGeolocationUpdate(String eldID, String driverID, Date timestamp, GeoLocation location){
        this.eldID = eldID;
        this.driverID = driverID;
        this.timeStamp = timestamp;

    }
    @Override
    public String toString(){
        return String.format("<GeoUpdate>\n\t<ELD-ID=\"%s\"/>" +
                "\n\t <Driver-ID=\"%s\">" +
                "\n\t <Timestamp> %s </Timestamp>" +
                "\n\t\t %s " +
                "\n</GeoUpdate>", eldID, driverID, ApiDataFormatter.formatTimestamp(timeStamp), location.toString());
    }
}
