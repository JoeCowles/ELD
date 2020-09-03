package com.carriergistics.eld.utils;
import com.github.mikephil.charting.data.Entry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DataLogger {
    static ArrayList<TimePeriod> timePeriods;
    static TimePeriod currentTimePeriod;
    public DataLogger(ArrayList<TimePeriod> timePeriods){
        this.timePeriods = timePeriods;
        if(timePeriods == null){
            this.timePeriods = new ArrayList<TimePeriod>();
        }
    }
    public static void log(TelematicsData data){
        if(data.getRpm() > 10 && currentTimePeriod.getType() != DriverStatus.Driving){
            //Start a driving time period
            changeTimePeriod(DriverStatus.Driving);
        }else if(data.getRpm() < 10 && (currentTimePeriod.getType() != DriverStatus.OffDutyNotDriving || currentTimePeriod.getType() != DriverStatus.Fueling)){
            //TODO: Determine if the driver is fueling or going off duty
        }
    }
    public static void offDuty(boolean offDuty){
        if(offDuty && currentTimePeriod.getType() == DriverStatus.Driving){
            changeTimePeriod(DriverStatus.OffDuty);
        }else if(!offDuty && currentTimePeriod.getType() == DriverStatus.OffDutyNotDriving) {
            changeTimePeriod(DriverStatus.Driving);
        } else if(offDuty && currentTimePeriod.getType() == DriverStatus.OffDuty){
            // Do Nothing
        }
    }
    private static void changeTimePeriod(DriverStatus status){
        currentTimePeriod.setEndTime(getTime());
        timePeriods.add(currentTimePeriod);
        currentTimePeriod = new TimePeriod(getTime(), status);
    }
    public static ArrayList<Entry> requestGraph(){
        //TODO: Return data points
        return null;
    }
    private static Date getTime(){
        //TODO: Get the current time from an api
        Date time = Calendar.getInstance().getTime();
        return time;
    }
}
