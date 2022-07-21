package com.carriergistics.eld.mapping.load;

import com.carriergistics.eld.mapping.loadEvent.LoadEvent;
import com.carriergistics.eld.mapping.loadPackage.LoadPackage;
import com.carriergistics.eld.mapping.loadShipment.LoadShipment;
import com.carriergistics.eld.mapping.priceSheet.LoadPriceSheet;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

//@Default
@Root
public class Load {

    @Element
    int id;
    @Element
    BillTo billTo;
    @ElementList
    ArrayList<Attribute> attributes;
    @ElementList
    ArrayList<LoadPackage> loadPackages;
    @ElementList
    ArrayList<LoadEvent> loadEvents;
    @ElementList
    ArrayList<LoadShipment> loadShipments;
    @ElementList
    ArrayList<LoadPriceSheet>  loadPricesheets;

    public Load(@Element(name="id") int id,
                @Element(name="billTo")BillTo billToInfo,
                @ElementList(name="attributes")ArrayList<Attribute> attributes,
                @ElementList(name="loadPackages")ArrayList<LoadPackage> loadPackages,
                @ElementList(name="loadEvents") ArrayList<LoadEvent> loadEvents,
                @ElementList(name="loadShipments") ArrayList<LoadShipment> loadShipments,
                @ElementList(name="loadPricesheets") ArrayList<LoadPriceSheet> loadPricesheets) {

        this.id = id;
        this.billTo = billToInfo;
        this.attributes = attributes;
        this.loadPackages = loadPackages;
        this.loadEvents = loadEvents;
        this.loadShipments = loadShipments;
        this.loadPricesheets = loadPricesheets;


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

    public ArrayList<LoadShipment> getLoadShipments() {
        return loadShipments;
    }

    public void setLoadShipment(ArrayList<LoadShipment> loadShipment) {
        this.loadShipments = loadShipment;
    }

    public ArrayList<LoadPriceSheet> getPriceSheet() {
        return loadPricesheets;
    }

    public void setPriceSheet(ArrayList<LoadPriceSheet> loadPricesheets) {
        this.loadPricesheets = loadPricesheets;
    }
}
