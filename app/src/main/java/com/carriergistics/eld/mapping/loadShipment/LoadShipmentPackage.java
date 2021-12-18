package com.carriergistics.eld.mapping.loadShipment;

import com.carriergistics.eld.mapping.loadShipment.itemGroup.LoadShipmentPackageItemGroup;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
//@Root
@Default
public class LoadShipmentPackage{
    //@Element
    int id;
    //@Element
    int loadShipmentId;
    //@Element
    ArrayList<LoadShipmentPackageDimension> loadShipmentPackageDimensions;
    //@Element
    ArrayList<LoadShipmentPackageWeight> loadShipmentPackageWeights;
    //@Element
    ArrayList<LoadShipmentPackageItemGroup> LoadShipmentPackageItemGroup;

    public LoadShipmentPackage(int id,  int loadShipmentId,  ArrayList<LoadShipmentPackageDimension> loadShipmentPackageDimensions,  ArrayList<LoadShipmentPackageWeight> loadShipmentPackageWeights, ArrayList<LoadShipmentPackageItemGroup> loadShipmentPackageItemGroup) {
        this.id = id;
        this.loadShipmentId = loadShipmentId;
        this.loadShipmentPackageDimensions = loadShipmentPackageDimensions;
        this.loadShipmentPackageWeights = loadShipmentPackageWeights;
        LoadShipmentPackageItemGroup = loadShipmentPackageItemGroup;
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

    public ArrayList<com.carriergistics.eld.mapping.loadShipment.itemGroup.LoadShipmentPackageItemGroup> getLoadShipmentPackageItemGroup() {
        return LoadShipmentPackageItemGroup;
    }

    public void setLoadShipmentPackageItemGroup(ArrayList<com.carriergistics.eld.mapping.loadShipment.itemGroup.LoadShipmentPackageItemGroup> loadShipmentPackageItemGroup) {
        LoadShipmentPackageItemGroup = loadShipmentPackageItemGroup;
    }
}
