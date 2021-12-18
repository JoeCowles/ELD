package com.carriergistics.eld.mapping.load;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public abstract class Quantity {
    @Element
    int id;
    @Element
    String type;
    @Element
    double quantity;
    @Element
    String uom;

    public Quantity(@Element(name = "id") int id, @Element(name = "type") String type, @Element(name = "quantity") double quantity, @Element(name = "uom") String uom) {
        this.id = id;
        this.type = type;
        this.quantity = quantity;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
}
