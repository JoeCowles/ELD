package main.java.com.carriergistics.eld.mapping.shipment;

public class LoadShipmentPackageItemGroupQuantity extends GroupQuantity{
    
    public LoadShipmentPackageItemGroupQuantity(int id, String type, double quantity, String unit){

        super(id, type, quantity, unit);

    }

    public String toString(){

        return super.replaceAll("<loadPackageItemGroupQuantity>", "<loadShipemntPackageItemGroupQuantity>").replaceAll("</loadPackageItemGroupQuantity>", "</loadShipemntPackageItemGroupQuantity>");
    }
}
