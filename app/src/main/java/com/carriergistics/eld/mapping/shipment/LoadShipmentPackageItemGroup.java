package com.carriergistics.eld.mapping.shipment;

import java.util.ArrayList;

import main.java.com.carriergistics.eld.mapping.ItemGroup.PackageItemGroup;

public class LoadShipmentPackageItemGroup extends PackageItemGroup{
    
    public LoadShipmentPackageItemGroup(int id, String description, ArrayList<LoadShipmentPackageItemGroupDimension> dims, ArrayList<loadShipmentPackageItemGroupQuantity> quantities, ArrayList<LoadShipmentPackageItemGroupWeight> weights, ArrayList<LineItem> lineItems){
        super(id, description, dims, quantities, lineItems);
    }
    @Override 
    public String toString(){

        String str = "<loadShipmentPackageItemGroup> <id>%f<id>";
        str += "<description>%s<description>";
        str += "<loadShipmentPackageItemGroupDimensions>";
        for(LoadShipmentPackageItemGroupDimension dim : this.getDims()){
            str += dim.toString();
        }
        str += "</loadShipmentPackageItemGroupDimensions>";
        str += "<loadShipmentPackageItemGroupQuantities>";
        for(loadShipmentPackageItemGroupQuantity qty : this.getQuantities()){
            str += qty.toString();
        }
        str += "</loadShipmentPackageItemGroupQuantities>";
        str += "<loadShipmentPackageItemGroupWeights>";
        for(LoadShipmentPackageItemGroupWeight weight : this.getWeights()){
            str += weight.toString();
        }
        str += "</loadShipmentPackageItemGroupWeights>";
        str += "<loadShipmentPackageItemGroupLineItems>";
        for(LineItem lineItem : this.getLineItems()){
            str += lineItem.toString();
        }
        str += "</loadShipmentPackageItemGroupLineItems>";
        str += "</loadShipmentPackageItemGroup>";
        return String.format(str, this.getId(), this.getDescription());

    }

}
