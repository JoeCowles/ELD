package com.carriergistics.eld.mapping.loadPackage.ItemGroup;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
@Root
public class LoadPackageItemGroup {

    @Element
    int id;
    @Element
    String description;
    @ElementList
    ArrayList<LoadPackageItemGroupDimension> loadPackageItemGroupDimensions;
    @ElementList
    ArrayList<LoadPackageItemGroupQuantity> loadPackageItemGroupQuantities;
    @ElementList
    ArrayList<LoadPackageItemGroupWeight> loadPackageItemGroupWeights;
    @ElementList
    ArrayList<LoadPackageItemGroupLineItem> loadPackageItemGroupLineItems;

    public LoadPackageItemGroup(@Element(name="id")int id,
                                @Element(name="description")String description,
                                @ElementList(name="loadPackageItemGroupDimensions")ArrayList<LoadPackageItemGroupDimension> loadPackageItemGroupDimensions,
                                @ElementList(name="loadPackageItemGroupQuantities")ArrayList<LoadPackageItemGroupQuantity> loadPackageItemGroupQuantities,
                                @ElementList(name="loadPackageItemGroupWeights")ArrayList<LoadPackageItemGroupWeight> loadPackageItemGroupWeights,
                                @ElementList(name="loadPackageItemGroupLineItems")ArrayList<LoadPackageItemGroupLineItem> loadPackageItemGroupLineItems) {
        this.id = id;
        this.description = description;
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
