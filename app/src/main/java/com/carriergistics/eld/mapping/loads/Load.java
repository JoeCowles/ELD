package main.java.com.carriergistics.eld.mapping.loads;

public class Load{

    int id;
    BilledInfo billTo;
    ArrayList<Attribute> attributes;
    ArrayList<LoadPackage> packages;
    ArrrayList<LoadEvent> events;
    ArrayList<LoadShipment> shipments;

    public Load(int id, BilledInfo billTo, ArrayList<Attribute> attributes, ArrayList<LoadPackage> packages, ArrayList<LoadEvent> events, ArrayList<LoadShipment> shipments){
        this.id = id;
        this.billTo = billTo;
        this.attributes = attributes;
        this.packages = packages;
        this.events = events;
        this.shipments = shipments;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

}