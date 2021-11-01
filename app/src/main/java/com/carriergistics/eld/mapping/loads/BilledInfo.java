package com.carriergistics.eld.mapping.loads;

public class BilledInfo {
    
    int id;
    String type, locationCode, name;
    String addressLn1, addressLn2, city, state, zip;
    String countryCode;

    public BilledInfo(int id, String type, String locationCode, String name, String addressLn1, String addressLn2, String city, String state, String zip, String countryCode) {
        this.id = id;
        this.type = type;
        this.locationCode = locationCode;
        this.name = name;
        this.addressLn1 = addressLn1;
        this.addressLn2 = addressLn2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.countryCode = countryCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    } 
    public String toString(){
        String str = "<billTo>";
        str += "<id>"+id+"</id>";
        str += "<type>"+type+"</type>";
        str += "<locationCode>"+locationCode+"</locationCode>";
        str += "<name>"+name+"</name>";
        str += "<line1>"+addressLn1+"</line1>";
        str += "<line2>"+addressLn2+"</line2>";
        str += "<city>"+city+"</city>";
        str += "<state>"+state+"</state>";
        str += "<postalCode>"+zip+"</postalCode>";
        str += "<countryCode>"+countryCode+"</countryCode>";
        str += "</billTo>";
        return str;
    }
}
