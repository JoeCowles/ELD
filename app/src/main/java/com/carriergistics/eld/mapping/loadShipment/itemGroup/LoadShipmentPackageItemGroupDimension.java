package com.carriergistics.eld.mapping.loadShipment.itemGroup;

import com.carriergistics.eld.mapping.load.Dimension;

import org.simpleframework.xml.Element;

public class LoadShipmentPackageItemGroupDimension extends Dimension {
    public LoadShipmentPackageItemGroupDimension(@Element(name="id") int id, @Element(name="type") String type, @Element(name="dim") String dim, @Element(name="dimension") double dimension, @Element(name="uom") String uom) {
        super(id, type, dim, dimension, uom);
    }
}
