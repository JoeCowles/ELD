package com.carriergistics.eld.mapping.loadPackage;

import com.carriergistics.eld.mapping.load.Dimension;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class LoadPackageDimension extends Dimension {

    public LoadPackageDimension(@Element(name="id") int id,@Element(name="type") String type, @Element(name="dim") String dim, @Element(name="dimension")double dimension, @Element(name="uom") String uom) {
        super(id, type, dim, dimension, uom);
    }

}
