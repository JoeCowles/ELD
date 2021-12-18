package com.carriergistics.eld.mapping.loadShipment;

import com.carriergistics.eld.mapping.load.Address;

import org.simpleframework.xml.Root;

@Root
public class ShipperAddress extends Address {
    public ShipperAddress(int id, String type, String locationCode, String name, String line1, String line2) {
        super(id, type, locationCode, name, line1, line2);
    }
}
