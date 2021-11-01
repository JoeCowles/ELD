package main.java.com.carriergistics.eld.mapping.shipment;

import main.java.com.carriergistics.eld.mapping.PackageWeight;

public class LoadShipmentPackageWeight extends PackageWeight{
    
    public LoadShipmentPackageWeight(int id, String type, double weight, String unit) {
        super(id, type, weight, unit);
    }

    @Override
    public String toString() {
        return super.toString().replaceAll("<loadPackageWeight>", "<loadShipmentPackageWeight>").replaceAll("</loadPackageWeight>", "</loadShipmentPackageWeight>");
    }
}
