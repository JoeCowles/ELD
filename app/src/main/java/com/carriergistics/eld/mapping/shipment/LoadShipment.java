package com.carriergistics.eld.mapping.shipment;

import com.carriergistics.eld.mapping.Address;

public class LoadShipment {
    int id;

    double actualWeight;
    String actualWeightUom;
    double deliveryWeight;
    String deliveryWeightUom;
    

    Address consigneeAddress;
    Address shipperAddress;

    String dropActualTime;
    String dropEarliestTime;
    String dropLatestTime;

    double linearFeet;
    String linearFeetUom;

    String owner;

    String pickupActualTime;
    String pickupEarliestTime;
    String pickupLatestTime;

    double plannedWeight;
    String plannedWeightUom;
    String quantityType;
    double quantity;
    String quantityUom;

    double rating;
    String ratingUom;

    String specialInstructions;

    String status;

    ArrayList<main.java.com.carriergistics.eld.mapping.loads.Attribute> attributes;

    public loadShipment(int id, double actualWeight, String actualWeightUom, double deliveryWeight, String deliveryWeightUom, Address consigneeAddress, Address shipperAddress, String dropActualTime, String dropEarliestTime, String dropLatestTime, double linearFeet, String linearFeetUom, String owner, String pickupActualTime, String pickupEarliestTime, String pickupLatestTime, double plannedWeight, String plannedWeightUom, String quantityType, double quantity, String quantityUom, double rating, String ratingUom, String specialInstructions, String status, ArrayList<Attribute> attributes) {
        this.id = id;
        this.actualWeight = actualWeight;
        this.actualWeightUom = actualWeightUom;
        this.deliveryWeight = deliveryWeight;
        this.deliveryWeightUom = deliveryWeightUom;
        this.consigneeAddress = consigneeAddress;
        this.shipperAddress = shipperAddress;
        this.dropActualTime = dropActualTime;
        this.dropEarliestTime = dropEarliestTime;
        this.dropLatestTime = dropLatestTime;
        this.linearFeet = linearFeet;
        this.linearFeetUom = linearFeetUom;
        this.owner = owner;
        this.pickupActualTime = pickupActualTime;
        this.pickupEarliestTime = pickupEarliestTime;
        this.pickupLatestTime = pickupLatestTime;
        this.plannedWeight = plannedWeight;
        this.plannedWeightUom = plannedWeightUom;
        this.quantityType = quantityType;
        this.quantity = quantity;
        this.quantityUom = quantityUom;
        this.rating = rating;
        this.ratingUom = ratingUom;
        this.specialInstructions = specialInstructions;
        this.status = status;
        this.attributes = attributes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String toString(){

        String str = "<loadShipment> <id> %d </id>";
        
        str += consigneeAddress.toString();
        str += " " + shipperAddress.toString();
          str += "\n<actualWeight>%d</actualWeight>";
          str += "\n<actualWeightUom>%s</actualWeightUom>";
          str += "\n<deliveryWeight>%d</deliveryWeight>";
          str += "\n<deliveryWeightUom>%s</deliveryWeightUom>";
          str += "\n<dropActual>%s</dropActual>";
          str += "\n<dropEarliest>%s</dropEarliest>";
          str += "\n<dropLatest>%s</dropLatest>";
          str += "\n<linearFeet>%d</linearFeet>";
          str += "\n<linearFeetUom>%s</linearFeetUom>";
          str += "\n<orderedWeight>%d</orderedWeight>";
          str += "\n<orderedWeightUom>%s</orderedWeightUom>";
          str += "\n<owner>%s</owner>";
          str += "\n<pickupActual>%s</pickupActual>";
          str += "\n<pickupEarliest>%s</pickupEarliest>";
          str += "\n<pickupLatest>%s</pickupLatest>";
          str += "\n<plannedWeight>%f</plannedWeight>";
          str += "\n<plannedWeightUom>%s</plannedWeightUom>";
          str += "\n<quantityType>%s</quantityType>";
          str += "\n<quantity>%f</quantity>";
          str += "\n<quantityUom>%s</quantityUom>";
          str += "\n<ratingCount>%f</ratingCount>";
          str += "\n<ratingCountUom>%s</ratingCountUom>";
          str += "\n<specialInstructions> %s </specialInstructions>";
          str += "\n<status>%s</status>"; 
        for(Attribut atr : attributes){
            str += "\n" + atr.toString();
        }
        str = String.format(str, id, actualWeight, actualWeightUom, deliveryWeight, 
        deliveryWeightUom, dropActualTime, dropEarliestTime, dropLatestTime, linearFeet, 
        linearFeetUom, plannedWeight, plannedWeightUom, quantityType, quantity, quantityUom, 
        rating, ratingUom, specialInstructions, status);

        return str;
    }

}
