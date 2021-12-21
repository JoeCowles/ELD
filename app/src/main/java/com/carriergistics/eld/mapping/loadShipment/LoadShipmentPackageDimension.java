package com.carriergistics.eld.mapping.loadShipment;

import com.carriergistics.eld.mapping.load.Dimension;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class LoadShipmentPackageDimension extends Dimension{
    public LoadShipmentPackageDimension(@Element(name="id") int id,
                                        @Element(name="type")String type,
                                        @Element(name="dim")String dim,
                                        @Element(name="dimension")double dimension,
                                        @Element(name="uom")String uom) {
        super(id, type, dim, dimension, uom);
    }
}
