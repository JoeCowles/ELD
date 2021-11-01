package main.java.com.carriergistics.eld.mapping.shipment;


public class LoadShipmentPackageItemGroupWeight {
    
    public LoadShipmentPackageItemGroupWeight(int id, String type, double weight, String unit) {
        super(id, type, weight, unit);
    }

    public String toString(){

        return super.toString().replaceAll("<loadPackageItemGroupWeight>", "<loadShipemntPackageItemGroupWeight>").replaceAll("</loadPackageItemGroupWeight>", "</loadShipemntPackageItemGroupWeight>");

    }
}
