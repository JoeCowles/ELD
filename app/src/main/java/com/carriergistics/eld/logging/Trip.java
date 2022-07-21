package com.carriergistics.eld.logging;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.apiData.GeoLocation;
import com.carriergistics.eld.mapping.load.Address;
import com.carriergistics.eld.mapping.load.Load;
import com.carriergistics.eld.mapping.loadEvent.LoadEvent;
import com.carriergistics.eld.mapping.loadShipment.LoadShipment;

import java.util.ArrayList;
import java.util.Date;

public class Trip {


    Date startTime;
    Date endTime;

    ArrayList<LoadEvent> events;
    ArrayList<Address> addresses;
    int currentEventIndex = 0;

    ArrayList<TimePeriod> hosLog;


    public Trip(Load load){

        addresses = new ArrayList<>();
        events = new ArrayList<>();

        for(LoadShipment shipment : load.getLoadShipments()){

            if(shipment.getConsigneeAddress() != null)
            addresses.add(shipment.getConsigneeAddress());

            if(shipment.getShipperAddress() != null);
            addresses.add(shipment.getShipperAddress());

        }
        // Add the load events
        for(LoadEvent e : load.getLoadEvents()){
            events.add(e);
        }
        // Sort the load events by their sequence number
        for(int i = 0; i < events.size(); i++){

            LoadEvent soonest = events.get(i);

            for(int x = i; x < events.size(); i++){

                if(events.get(x).getSequenceNumber() < soonest.getSequenceNumber()){

                    soonest = load.getLoadEvents().get(x);

                }

            }
            events.remove(soonest);
            events.add(i, soonest);

        }

    }

    // Returns the current load event
    public LoadEvent currentEvent(){

        return events.get(currentEventIndex);

    }

    // Returns the next event
    public LoadEvent nextEvent(){

        return events.get(currentEventIndex+1);

    }

    // Increments the event and returns
    public LoadEvent getIncEvent(){

        return events.get(currentEventIndex++);

    }

    // Gets the last event
    public LoadEvent getLastEvent(){

        return events.get(currentEventIndex-1);

    }

    // Get the address of the event
    public Address getEventAddress(LoadEvent event){

        for(Address address : addresses){

            if(event.getAddressId() == address.getId()){
                return address;
            }

        }
        return null;
    }

    public ArrayList<TimePeriod> getHosLog() {
        return hosLog;
    }

    public void setHosLog(ArrayList<TimePeriod> hosLog) {
        this.hosLog = hosLog;
    }

    // TODO: determine how to log the location (log the city, lat and long?)
    public void startTrip(){
        if(startTime != null) return;

        startTime = MainActivity.getTime();

        currentEventIndex = 0;

    }
    // TODO: ditto
    public void endTrip(){
        if(endTime != null) return;

        endTime = MainActivity.getTime();

    }

}
