package com.carriergistics.eld.mapping;

public abstract class Address {
    
    int id;
    String type;
    String locationCode;
    String name;
    String address1;
    String address2;
    String city;
    String state;
    String postalCode;
    String countryCode;

    public Address(int id, String type, String locationCode, String name, String address1, String address2, String city, String state, String postalCode, String countryCode) {
        this.id = id;
        this.type = type;
        this.locationCode = locationCode;
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.countryCode = countryCode;
    }

}
