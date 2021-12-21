package com.carriergistics.eld.mapping.loadPackage.ItemGroup;

import com.carriergistics.eld.mapping.load.Dimension;

import org.simpleframework.xml.Element;

public class LoadPackageItemGroupDimension extends Dimension {

    public LoadPackageItemGroupDimension(@Element(name="id") int id,
                                         @Element(name="type")String type,
                                         @Element(name="dim")String dim,
                                         @Element(name="dimension")double dimension,
                                         @Element(name="uom")String uom) {
        super(id, type, dim, dimension, uom);
    }
}
