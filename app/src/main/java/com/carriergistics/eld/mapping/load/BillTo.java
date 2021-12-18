package com.carriergistics.eld.mapping.load;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class BillTo {
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
    @Element
    String city;
    @Element
    String state;
    @Element
    int postalCode;
    @Element
    String countryCode;
    public BillTo(@Element(name = "id") int id, @Element(name = "type")String type, @Element(name = "locationCode")String locationCode, @Element(name = "name") String name, @Element(name = "line1") String line1, @Element(name = "line2") String line2, @Element(name = "city") String city, @Element(name = "state") String state, @Element(name = "postalCode") int postalCode, @Element(name = "countryCode") String countryCode) {
        this.id = id;
        this.type = type;
        this.locationCode = locationCode;
        this.name = name;
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.countryCode = countryCode;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }


}
