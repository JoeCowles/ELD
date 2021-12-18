package com.carriergistics.eld.mapping.loadShipment;

import com.carriergistics.eld.mapping.load.Dimension;

import org.simpleframework.xml.Root;

@Root
public class LoadShipmentPackageDimension extends Dimension{
    public LoadShipmentPackageDimension(int id, String type, String dim, double dimension, String uom) {
        super(id, type, dim, dimension, uom);
    }
}
