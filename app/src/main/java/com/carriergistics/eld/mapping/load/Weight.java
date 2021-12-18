package com.carriergistics.eld.mapping.load;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public abstract class Weight {
    @Element
    int id;
    @Element
    String type;
    @Element
    double weight;
    @Element
    String uom;

    public Weight(@Element(name = "id")int id, @Element(name = "type")String type, @Element(name = "weight")double weight, @Element(name = "uom")String uom) {
        this.id = id;
        this.type = type;
        this.weight = weight;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
}
