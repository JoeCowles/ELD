package com.carriergistics.eld.mapping.loadShipment.itemGroup;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root
public class LoadShipmentPackageItemGroup{
    @Element
    int id;
    @Element
    String description;
    @Element
    ArrayList<LoadShipmentPackageItemGroupDimension> loadShipmentPackageItemGroupDimensions;
    @Element
    ArrayList<LoadShipmentPackageItemGroupQuantity> loadShipmentPackageItemGroupQuantities;
    @Element
    ArrayList<LoadShipmentPackageItemGroupWeight> loadShipmentPackageItemGroupWeights;
    @Element
    ArrayList<LoadShipmentPackageItemGroupLineItem> loadShipmentPackageItemGroupLineItems;


    public LoadShipmentPackageItemGroup(@Element(name ="id")int id, @Element(name ="description") String description, @Element(name ="loadShipmentItemGroupDimensions") ArrayList<LoadShipmentPackageItemGroupDimension> loadShipmentPackageItemGroupDimensions, @Element(name ="loadShipmentPackageItemGroupQuantities") ArrayList<LoadShipmentPackageItemGroupQuantity> loadShipmentPackageItemGroupQuantities, @Element(name ="loadShipmentPackageItemGroupWeights") ArrayList<LoadShipmentPackageItemGroupWeight> loadShipmentPackageItemGroupWeights, @Element(name ="loadShipmentPackageItemGroupLineItems") ArrayList<LoadShipmentPackageItemGroupLineItem> loadShipmentPackageItemGroupLineItems) {
        this.id = id;
        this.description = description;
        this.loadShipmentPackageItemGroupDimensions = loadShipmentPackageItemGroupDimensions;
        this.loadShipmentPackageItemGroupQuantities = loadShipmentPackageItemGroupQuantities;
        this.loadShipmentPackageItemGroupWeights = loadShipmentPackageItemGroupWeights;
        this.loadShipmentPackageItemGroupLineItems = loadShipmentPackageItemGroupLineItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<LoadShipmentPackageItemGroupDimension> getLoadShipmentPackageItemGroupDimensions() {
        return loadShipmentPackageItemGroupDimensions;
    }

    public void setLoadShipmentPackageItemGroupDimensions(ArrayList<LoadShipmentPackageItemGroupDimension> loadShipmentPackageItemGroupDimensions) {
        this.loadShipmentPackageItemGroupDimensions = loadShipmentPackageItemGroupDimensions;
    }

    public ArrayList<LoadShipmentPackageItemGroupQuantity> getLoadShipmentPackageItemGroupQuantities() {
        return loadShipmentPackageItemGroupQuantities;
    }

    public void setLoadShipmentPackageItemGroupQuantities(ArrayList<LoadShipmentPackageItemGroupQuantity> loadShipmentPackageItemGroupQuantities) {
        this.loadShipmentPackageItemGroupQuantities = loadShipmentPackageItemGroupQuantities;
    }

    public ArrayList<LoadShipmentPackageItemGroupWeight> getLoadShipmentPackageItemGroupWeights() {
        return loadShipmentPackageItemGroupWeights;
    }

    public void setLoadShipmentPackageItemGroupWeights(ArrayList<LoadShipmentPackageItemGroupWeight> loadShipmentPackageItemGroupWeights) {
        this.loadShipmentPackageItemGroupWeights = loadShipmentPackageItemGroupWeights;
    }

    public ArrayList<LoadShipmentPackageItemGroupLineItem> getLoadShipmentPackageItemGroupLineItems() {
        return loadShipmentPackageItemGroupLineItems;
    }

    public void setLoadShipmentPackageItemGroupLineItems(ArrayList<LoadShipmentPackageItemGroupLineItem> loadShipmentPackageItemGroupLineItems) {
        this.loadShipmentPackageItemGroupLineItems = loadShipmentPackageItemGroupLineItems;
    }
}
