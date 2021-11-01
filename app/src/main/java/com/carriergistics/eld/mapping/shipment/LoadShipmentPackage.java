package main.java.com.carriergistics.eld.mapping.shipment;

public class LoadShipmentPackage extends LoadPackage{
    
    public LoadShipmentPackage(int id, ArrayList<PackageDimension> dimensions, ArrayList<PackageWeight> weights){

        super(id, dimensions, weights);

    }

    @Override
    public String toString(){

        return super.toString().replaceAll("<loadPackage>", "<loadShipmentPackage>").replaceAll("</loadPackage>", "</loadShipmentPackage>");

    }

}
