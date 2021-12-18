package com.carriergistics.eld.mapping.loadShipment;

import com.carriergistics.eld.mapping.load.Weight;

import org.simpleframework.xml.Root;

@Root
public class LoadShipmentPackageWeight extends Weight {
    public LoadShipmentPackageWeight(int id, String type, double weight, String uom) {
        super(id, type, weight, uom);
    }
}
