package com.carriergistics.eld.mapping.loadPackage;

import com.carriergistics.eld.mapping.loadPackage.ItemGroup.LoadPackageItemGroup;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
@Root
public class LoadPackage {
    @Element
    int id;
    // Load package dims
    @ElementList
    ArrayList<LoadPackageDimension> loadPackageDimensions;
    // Load package weights
    @ElementList
    ArrayList<LoadPackageWeight> loadPackageWeights;
    // Load package Item groups
    @ElementList
    ArrayList<LoadPackageItemGroup> loadPackageItemGroups;

    public LoadPackage(@Element(name="id") int id,
                       @ElementList(name="loadPackageDimensions") ArrayList<LoadPackageDimension> loadPackageDimensions,
                       @ElementList(name="loadPackageWeights") ArrayList<LoadPackageWeight> loadPackageWeights,
                       @ElementList(name="loadPackageItemGroups") ArrayList<LoadPackageItemGroup> loadPackageItemGroups) {
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
