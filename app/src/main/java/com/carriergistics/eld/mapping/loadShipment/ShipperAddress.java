package com.carriergistics.eld.mapping.loadShipment;

import com.carriergistics.eld.mapping.load.Address;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Default
public class ShipperAddress extends Address {

    public ShipperAddress(@Element(name="id") int id,
                          @Element(name="type")String type,
                          @Element(name="locationCode")String locationCode,
                          @Element(name="name")String name,
                          @Element(name="line1")String line1,
                          @Element(name="line2")String line2,
                          @Element(name="city")String city,
                          @Element(name="state")String state,
                          @Element(name="postalCode")String postalCode,
                          @Element(name="countryCode")String countryCode) {
        super(id, type, locationCode, name, line1, line2, city, state, postalCode, countryCode);
    }
}
