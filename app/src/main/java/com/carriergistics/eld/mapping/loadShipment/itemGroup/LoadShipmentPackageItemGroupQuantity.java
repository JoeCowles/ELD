package com.carriergistics.eld.mapping.loadShipment.itemGroup;

import com.carriergistics.eld.mapping.load.Quantity;

import org.simpleframework.xml.Element;

public class LoadShipmentPackageItemGroupQuantity extends Quantity{

    public LoadShipmentPackageItemGroupQuantity(@Element(name="id") int id, @Element(name="type") String type, @Element(name="quantity") double quantity, @Element(name="uom") String uom) {
        super(id, type, quantity, uom);
    }
}
