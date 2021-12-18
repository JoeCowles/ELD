package com.carriergistics.eld.mapping.loadPackage.ItemGroup;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
@Root
public class LoadPackageItemGroup {
    @Element
    int id;
    @Element
    ArrayList<LoadPackageItemGroupDimension> loadPackageItemGroupDimensions;
    @Element
    ArrayList<LoadPackageItemGroupQuantity> loadPackageItemGroupQuantities;
    @Element
    ArrayList<LoadPackageItemGroupWeight> loadPackageItemGroupWeights;
    @Element
    ArrayList<LoadPackageItemGroupLineItem> loadPackageItemGroupLineItems;

    public LoadPackageItemGroup(@Element(name="id")int id, @Element(name="loadPackageItemGroupDimensions") ArrayList<LoadPackageItemGroupDimension> loadPackageItemGroupDimensions, @Element(name="loadPackageItemGroupQuantities") ArrayList<LoadPackageItemGroupQuantity> loadPackageItemGroupQuantities, @Element(name="loadPackageItemGroupWeights") ArrayList<LoadPackageItemGroupWeight> loadPackageItemGroupWeights, @Element(name="loadPackageItemGroupLineItems") ArrayList<LoadPackageItemGroupLineItem> loadPackageItemGroupLineItems) {
        this.id = id;
        this.loadPackageItemGroupDimensions = loadPackageItemGroupDimensions;
        this.loadPackageItemGroupQuantities = loadPackageItemGroupQuantities;
        this.loadPackageItemGroupWeights = loadPackageItemGroupWeights;
        this.loadPackageItemGroupLineItems = loadPackageItemGroupLineItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<LoadPackageItemGroupDimension> getLoadPackageItemGroupDimensions() {
        return loadPackageItemGroupDimensions;
    }

    public void setLoadPackageItemGroupDimensions(ArrayList<LoadPackageItemGroupDimension> loadPackageItemGroupDimensions) {
        this.loadPackageItemGroupDimensions = loadPackageItemGroupDimensions;
    }

    public ArrayList<LoadPackageItemGroupQuantity> getLoadPackageItemGroupQuantities() {
        return loadPackageItemGroupQuantities;
    }

    public void setLoadPackageItemGroupQuantities(ArrayList<LoadPackageItemGroupQuantity> loadPackageItemGroupQuantities) {
        this.loadPackageItemGroupQuantities = loadPackageItemGroupQuantities;
    }

    public ArrayList<LoadPackageItemGroupWeight> getLoadPackageItemGroupWeights() {
        return loadPackageItemGroupWeights;
    }

    public void setLoadPackageItemGroupWeights(ArrayList<LoadPackageItemGroupWeight> loadPackageItemGroupWeights) {
        this.loadPackageItemGroupWeights = loadPackageItemGroupWeights;
    }

    public ArrayList<LoadPackageItemGroupLineItem> getLoadPackageItemGroupLineItems() {
        return loadPackageItemGroupLineItems;
    }

    public void setLoadPackageItemGroupLineItem(ArrayList<LoadPackageItemGroupLineItem> loadPackageItemGroupLineItems) {
        this.loadPackageItemGroupLineItems = loadPackageItemGroupLineItems;
    }
}
