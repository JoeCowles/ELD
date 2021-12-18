package com.carriergistics.eld.mapping.load;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Address {
    @Element
    int id;
    @Element
    String type;
    @Element
    String locationCode;
    @Element
    String name;
    @Element
    String line1;
    @Element
    String line2;

    public Address(@Element(name="id") int id, @Element(name="type")String type, @Element(name="locationCode")String locationCode, @Element(name="name")String name, @Element(name="line1") String line1, @Element(name="line2")String line2) {
        this.id = id;
        this.type = type;
        this.locationCode = locationCode;
        this.name = name;
        this.line1 = line1;
        this.line2 = line2;
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

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }
}
