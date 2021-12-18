package com.carriergistics.eld.mapping.load;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public abstract class Dimension {
    @Element
    int id;
    @Element
    String type;
    @Element
    String dim;
    @Element
    String uom = "";
    @Element
    double dimension;

    public Dimension(@Element(name = "id") int id, @Element(name = "type") String type, @Element(name = "dim") String dim, @Element(name = "dimension") double dimension, @Element(name = "uom") String uom) {
        this.id = id;
        this.type = type;
        this.dim = dim;
        this.dimension = dimension;
        this.uom = uom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDim() {
        return dim;
    }

    public void setDim(String dim) {
        this.dim = dim;
    }

    public double getDimension() {
        return dimension;
    }

    public void setDimension(double dimension) {
        this.dimension = dimension;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
}
