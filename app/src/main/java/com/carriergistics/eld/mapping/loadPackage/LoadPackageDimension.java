package com.carriergistics.eld.mapping.loadPackage;

import com.carriergistics.eld.mapping.load.Dimension;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.Root;

@Default
public class LoadPackageDimension extends Dimension {

    public LoadPackageDimension(int id, String type, String dim, double dimension, String uom) {
        super(id, type, dim, dimension, uom);
    }

}
