package com.carriergistics.eld.mapping.load;

import com.carriergistics.eld.mapping.loadEvent.LoadEvent;
import com.carriergistics.eld.mapping.loadPackage.LoadPackage;
import com.carriergistics.eld.mapping.loadShipment.LoadShipment;
import com.carriergistics.eld.mapping.priceSheet.PriceSheet;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Default
//@Root
public class Load {

    //@Element
    int id;
    //@Element
    BillTo billTo;
    //@Element
    ArrayList<Attribute> attributes;
    //@Element
    ArrayList<LoadPackage> loadPackages;
    //@Element
    ArrayList<LoadEvent> loadEvents;
    //@Element
    ArrayList<LoadShipment> loadShipment;
    //@Element
    ArrayList<PriceSheet>  priceSheets;

    public Load(int id, BillTo billToInfo,ArrayList<Attribute> attributes, ArrayList<LoadPackage> loadPackages, ArrayList<LoadEvent> loadEvents,ArrayList<LoadShipment> loadShipment,ArrayList<PriceSheet> priceSheets) {

        this.id = id;
        this.billTo = billToInfo;
        this.attributes = attributes;
        this.loadPackages = loadPackages;
        this.loadEvents = loadEvents;
        this.loadShipment = loadShipment;
        this.priceSheets = priceSheets;


    }

    public BillTo getBillToInfo() {
        return billTo;
    }

    public void setBillToInfo(BillTo billToInfo) {
        this.billTo = billToInfo;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<LoadPackage> getLoadPackages() {
        return loadPackages;
    }

    public void setLoadPackages(ArrayList<LoadPackage> loadPackages) {
        this.loadPackages = loadPackages;
    }

    public ArrayList<LoadEvent> getLoadEvents() {
        return loadEvents;
    }

    public void setLoadEvents(ArrayList<LoadEvent> loadEvents) {
        this.loadEvents = loadEvents;
    }

    public ArrayList<LoadShipment> getLoadShipment() {
        return loadShipment;
    }

    public void setLoadShipment(ArrayList<LoadShipment> loadShipment) {
        this.loadShipment = loadShipment;
    }

    public ArrayList<PriceSheet> getPriceSheet() {
        return priceSheets;
    }

    public void setPriceSheet(ArrayList<PriceSheet> priceSheets) {
        this.priceSheets = priceSheets;
    }
}
