package com.carriergistics.eld.mapping.shipment;

public class LoadShipmentPackageItemGroupDimension extends GroupDim{
    
    public LoadShipmentPackageItemGroupDimension(int id, String type, String dimType, double dimValue, String unit) {
        super(id, type, dimType, dimValue, unit);
    }
        
    public String toString(){
        return super.toString().replaceAll("<loadPackageItemGroupDimension>", "<loadShipmentPackageItemGroupDimension>").replaceAll("</loadPackageItemGroupDimension>", "</loadShipmentPackageItemGroupDimension>");
    }

}
