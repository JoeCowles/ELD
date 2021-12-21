package com.carriergistics.eld.mapping.loadShipment;

import com.carriergistics.eld.mapping.loadShipment.itemGroup.LoadShipmentPackageItemGroup;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
//@Root
@Root
public class LoadShipmentPackage{
    @Element
    int id;
    @Element
    int loadShipmentId;
    @ElementList
    ArrayList<LoadShipmentPackageDimension> loadShipmentPackageDimensions;
    @ElementList
    ArrayList<LoadShipmentPackageWeight> loadShipmentPackageWeights;
    @ElementList
    ArrayList<LoadShipmentPackageItemGroup> loadShipmentPackageItemGroups;

    public LoadShipmentPackage(@Element(name="id")int id,
                               @Element(name="loadShipmentId")int loadShipmentId,
                               @ElementList(name="loadShipmentPackageDimensions") ArrayList<LoadShipmentPackageDimension> loadShipmentPackageDimensions,
                               @ElementList(name="loadShipmentPackageWeights") ArrayList<LoadShipmentPackageWeight> loadShipmentPackageWeights,
                               @ElementList(name="loadShipmentPackageItemGroups") ArrayList<LoadShipmentPackageItemGroup> loadShipmentPackageItemGroups) {
        this.id = id;
        this.loadShipmentId = loadShipmentId;
        this.loadShipmentPackageDimensions = loadShipmentPackageDimensions;
        this.loadShipmentPackageWeights = loadShipmentPackageWeights;
        this.loadShipmentPackageItemGroups = loadShipmentPackageItemGroups;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoadShipmentId() {
        return loadShipmentId;
    }

    public void setLoadShipmentId(int loadShipmentId) {
        this.loadShipmentId = loadShipmentId;
    }

    public ArrayList<LoadShipmentPackageDimension> getLoadShipmentPackageDimensions() {
        return loadShipmentPackageDimensions;
    }

    public void setLoadShipmentPackageDimensions(ArrayList<LoadShipmentPackageDimension> loadShipmentPackageDimensions) {
        this.loadShipmentPackageDimensions = loadShipmentPackageDimensions;
    }

    public ArrayList<LoadShipmentPackageWeight> getLoadShipmentPackageWeights() {
        return loadShipmentPackageWeights;
    }

    public void setLoadShipmentPackageWeights(ArrayList<LoadShipmentPackageWeight> loadShipmentPackageWeights) {
        this.loadShipmentPackageWeights = loadShipmentPackageWeights;
    }

    public ArrayList<LoadShipmentPackageItemGroup> getLoadShipmentPackageItemGroup() {
        return loadShipmentPackageItemGroups;
    }

    public void setLoadShipmentPackageItemGroup(ArrayList<LoadShipmentPackageItemGroup> loadShipmentPackageItemGroups) {
        this.loadShipmentPackageItemGroups = loadShipmentPackageItemGroups;
    }
}
