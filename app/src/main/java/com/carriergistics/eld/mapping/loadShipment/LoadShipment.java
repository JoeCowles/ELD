package com.carriergistics.eld.mapping.loadShipment;

import com.carriergistics.eld.mapping.loadEvent.LoadEventAttribute;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root
public class LoadShipment {
    @Element
    int id;
    @Element(required = false)
    ConsigneeAddress consigneeAddress;
    @Element(required = false)
    ShipperAddress shipperAddress;
    @Element(required = false)
    double actualWeight;
    @Element(required = false)
    String actualWeightUom;
    @Element(required = false)
    double deliveryWeight;
    @Element(required = false)
    String deliveryWeightUom;
    @Element(required = false)
    String dropActual;
    @Element(required = false)
    String dropEarliest;
    @Element(required = false)
    String dropLatest;

    @Element(required = false)
    double linearFeet;
    @Element(required = false)
    String linearFeetUom = "ft";

    @Element(required = false)
    double orderedWeight;
    @Element(required = false)
    String orderedWeightUom;
    @Element(required = false)
    String owner;
    @Element(required = false)
    String pickupActual;
    @Element(required = false)
    String pickupEarliest;
    @Element(required = false)
    String pickupLatest;

    @Element(required = false)
    double plannedWeight;
    @Element(required = false)
    String plannedWeightUom;
    @Element(required = false)
    String quantityType;
    @Element(required = false)
    double quantity;
    @Element(required = false)
    String quantityUom = "PLT";
    @Element(required = false)
    double ratingCount;
    @Element(required = false)
    String ratingCountUom;
    @Element(required = false)
    String specialInstructions;
    @Element(required = false)
    String status;
    @ElementList(required = false)
    ArrayList<LoadEventAttribute> loadShipmentAttributes;
    @ElementList
    ArrayList<LoadShipmentPackage> loadShipmentPackages;

    public LoadShipment(@Element(name = "id")int id, @Element(name = "consigneeAddress") ConsigneeAddress consigneeAddress, @Element(name = "shipperAddress") ShipperAddress shipperAddress, @Element(name = "actualWeight") double actualWeight, @Element(name = "actualWeightUom") String actualWeightUom, @Element(name = "deliveryWeight") double deliveryWeight, @Element(name = "deliveryWeightUom") String deliveryWeightUom, @Element(name = "dropActual") String dropActual, @Element(name = "dropEarliest") String dropEarliest, @Element(name = "dropLatest") String dropLatest, @Element(name = "linearFeet") double linearFeet, @Element(name = "linearFeetUom") String linearFeetUom,
                        @Element(name = "orderedWeight") double orderedWeight, @Element(name = "orderedWeightUom") String orderedWeightUom, @Element(name = "owner") String owner, @Element(name = "pickupActual") String pickupActual, @Element(name = "pickupEarliest") String pickupEarliest, @Element(name = "pickupLatest") String pickupLatest, @Element(name = "plannedWeight") double plannedWeight, @Element(name = "plannedWeightUom") String plannedWeightUom, @Element(name = "quantityType") String quantityType, @Element(name = "quantity") double quantity, @Element(name = "quantityUom") String quantityUom, @Element(name = "ratingCount") double ratingCount,
                        @Element(name = "ratingCountUom") String ratingCountUom, @Element(name = "specialInstructions") String specialInstructions, @Element(name = "status") String status, @ElementList(name = "loadShipmentAttributes") ArrayList<LoadEventAttribute> loadShipmentAttributes, @ElementList(name = "loadShipmentPackages") ArrayList<LoadShipmentPackage> loadShipmentPackages) {
        this.id = id;
        this.consigneeAddress = consigneeAddress;
        this.shipperAddress = shipperAddress;
        this.actualWeight = actualWeight;
        this.actualWeightUom = actualWeightUom;
        this.deliveryWeight = deliveryWeight;
        this.deliveryWeightUom = deliveryWeightUom;
        this.dropActual = dropActual;
        this.dropEarliest = dropEarliest;
        this.dropLatest = dropLatest;
        this.linearFeet = linearFeet;
        this.linearFeetUom = linearFeetUom;
        this.orderedWeight = orderedWeight;
        this.orderedWeightUom = orderedWeightUom;
        this.owner = owner;
        this.pickupActual = pickupActual;
        this.pickupEarliest = pickupEarliest;
        this.pickupLatest = pickupLatest;
        this.plannedWeight = plannedWeight;
        this.plannedWeightUom = plannedWeightUom;
        this.quantityType = quantityType;
        this.quantity = quantity;
        this.quantityUom = quantityUom;
        this.ratingCount = ratingCount;
        this.ratingCountUom = ratingCountUom;
        this.specialInstructions = specialInstructions;
        this.status = status;
        this.loadShipmentAttributes = loadShipmentAttributes;
        this.loadShipmentPackages = loadShipmentPackages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ConsigneeAddress getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(ConsigneeAddress consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public ShipperAddress getShipperAddress() {
        return shipperAddress;
    }

    public void setShipperAddress(ShipperAddress shipperAddress) {
        this.shipperAddress = shipperAddress;
    }

    public double getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(double actualWeight) {
        this.actualWeight = actualWeight;
    }

    public String getActualWeightUom() {
        return actualWeightUom;
    }

    public void setActualWeightUom(String actualWeightUom) {
        this.actualWeightUom = actualWeightUom;
    }

    public double getDeliveryWeight() {
        return deliveryWeight;
    }

    public void setDeliveryWeight(double deliveryWeight) {
        this.deliveryWeight = deliveryWeight;
    }

    public String getDeliveryWeightUom() {
        return deliveryWeightUom;
    }

    public void setDeliveryWeightUom(String deliveryWeightUom) {
        this.deliveryWeightUom = deliveryWeightUom;
    }

    public String getDropActual() {
        return dropActual;
    }

    public void setDropActual(String dropActual) {
        this.dropActual = dropActual;
    }

    public String getDropEarliest() {
        return dropEarliest;
    }

    public void setDropEarliest(String dropEarliest) {
        this.dropEarliest = dropEarliest;
    }

    public String getDropLatest() {
        return dropLatest;
    }

    public void setDropLatest(String dropLatest) {
        this.dropLatest = dropLatest;
    }

    public double getLinearFeet() {
        return linearFeet;
    }

    public void setLinearFeet(double linearFeet) {
        this.linearFeet = linearFeet;
    }

    public String getLinearFeetUom() {
        return linearFeetUom;
    }

    public void setLinearFeetUom(String linearFeetUom) {
        this.linearFeetUom = linearFeetUom;
    }

    public double getOrderedWeight() {
        return orderedWeight;
    }

    public void setOrderedWeight(double orderedWeight) {
        this.orderedWeight = orderedWeight;
    }

    public String getOrderedWeightUom() {
        return orderedWeightUom;
    }

    public void setOrderedWeightUom(String orderedWeightUom) {
        this.orderedWeightUom = orderedWeightUom;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPickupAcutal() {
        return pickupActual;
    }

    public void setPickupAcutal(String pickupAcutal) {
        this.pickupActual = pickupAcutal;
    }

    public String getPickupEarliest() {
        return pickupEarliest;
    }

    public void setPickupEarliest(String pickupEarliest) {
        this.pickupEarliest = pickupEarliest;
    }

    public String getPickupLatest() {
        return pickupLatest;
    }

    public void setPickupLatest(String pickupLatest) {
        this.pickupLatest = pickupLatest;
    }

    public double getPlannedWeight() {
        return plannedWeight;
    }

    public void setPlannedWeight(double plannedWeight) {
        this.plannedWeight = plannedWeight;
    }

    public String getPlannedWeightUom() {
        return plannedWeightUom;
    }

    public void setPlannedWeightUom(String plannedWeightUom) {
        this.plannedWeightUom = plannedWeightUom;
    }

    public String getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(String quantityType) {
        this.quantityType = quantityType;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getQuantityUom() {
        return quantityUom;
    }

    public void setQuantityUom(String quantityUom) {
        this.quantityUom = quantityUom;
    }

    public double getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(double ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getRatingCountUom() {
        return ratingCountUom;
    }

    public void setRatingCountUom(String ratingCountUom) {
        this.ratingCountUom = ratingCountUom;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<LoadEventAttribute> getLoadShipmentAttributes() {
        return loadShipmentAttributes;
    }

    public void setLoadShipmentAttributes(ArrayList<LoadEventAttribute> loadShipmentAttributes) {
        this.loadShipmentAttributes = loadShipmentAttributes;
    }

    public ArrayList<LoadShipmentPackage> getLoadShipmentPackages() {
        return loadShipmentPackages;
    }

    public void setLoadShipmentPackages(ArrayList<LoadShipmentPackage> loadShipmentPackages) {
        this.loadShipmentPackages = loadShipmentPackages;
    }
}
