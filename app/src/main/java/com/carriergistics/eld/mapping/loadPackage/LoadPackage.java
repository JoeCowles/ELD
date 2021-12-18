package com.carriergistics.eld.mapping.loadPackage;

import com.carriergistics.eld.mapping.loadPackage.ItemGroup.LoadPackageItemGroup;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
@Default
public class LoadPackage {
    //@Element
    int id;
    // Load package dims
    //@Element
    ArrayList<LoadPackageDimension> loadPackageDimensions;
    // Load package weights
    @Element
    ArrayList<LoadPackageWeight> loadPackageWeights;
    // Load package Item groups
    @Element
    ArrayList<LoadPackageItemGroup> loadPackageItemGroups;

    public LoadPackage(@Element(name="id") int id, @Element(name="loadPackageDimensions") ArrayList<LoadPackageDimension> loadPackageDimensions, @Element(name="loadPackageWeights") ArrayList<LoadPackageWeight> loadPackageWeights, @Element(name="loadPackageItemGroups") ArrayList<LoadPackageItemGroup> loadPackageItemGroups) {
        this.id = id;
        this.loadPackageDimensions = loadPackageDimensions;
        this.loadPackageWeights = loadPackageWeights;
        this.loadPackageItemGroups = loadPackageItemGroups;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<LoadPackageDimension> getLoadPackageDimensions() {
        return loadPackageDimensions;
    }

    public void setLoadPackageDimensions(ArrayList<LoadPackageDimension> loadPackageDimensions) {
        this.loadPackageDimensions = loadPackageDimensions;
    }

    public ArrayList<LoadPackageWeight> getLoadPackageWeights() {
        return loadPackageWeights;
    }

    public void setLoadPackageWeights(ArrayList<LoadPackageWeight> loadPackageWeights) {
        this.loadPackageWeights = loadPackageWeights;
    }

    public ArrayList<LoadPackageItemGroup> getLoadPackageItemGroups() {
        return loadPackageItemGroups;
    }

    public void setLoadPackageItemGroups(ArrayList<LoadPackageItemGroup> loadPackageItemGroups) {
        this.loadPackageItemGroups = loadPackageItemGroups;
    }
}
