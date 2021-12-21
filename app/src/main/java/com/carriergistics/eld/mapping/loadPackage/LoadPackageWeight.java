package com.carriergistics.eld.mapping.loadPackage;

import com.carriergistics.eld.mapping.load.Weight;

import org.simpleframework.xml.Element;

public class LoadPackageWeight extends Weight {

    public LoadPackageWeight(@Element(name="id") int id, @Element(name="type") String type, @Element(name="weight") double weight, @Element(name="uom") String uom) {
        super(id, type, weight, uom);
    }
}
