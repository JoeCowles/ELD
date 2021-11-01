package main.java.com.carriergistics.eld.mapping.shipment;


import main.java.com.carriergistics.eld.mapping.PackageAttribute;

public class LoadShipmentPackageDimension extends PackageAttribute{
    
    public LoadShipmentPackageDimension(int id, String type, String dimType, double weight, String uom) {
        super(id, type, dimType, weight, uom);
    }
    @Override
    public String toString(){
        String str = super.toString();
        str.replaceAll("<loadPackageDimension>", "<loadShipmentPackageDimension>").replaceAll("</loadPackageDimension>", "</loadShipmentPackageDimension>");
        return str;
    }
}
