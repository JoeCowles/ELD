package com.carriergistics.eld.mapping.loadShipment;

import com.carriergistics.eld.mapping.load.Weight;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class LoadShipmentPackageWeight extends Weight {
    public LoadShipmentPackageWeight(@Element(name="id") int id,
                                     @Element(name="type")String type,
                                     @Element(name="weight")double weight,
                                     @Element(name="uom")String uom) {
        super(id, type, weight, uom);
    }
}
