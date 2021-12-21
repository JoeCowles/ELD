package com.carriergistics.eld.mapping.priceSheet;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root
public class LoadPriceSheet {
    @Element
    int id;
    @Element
    String type;
    @Element
    int isSelected;
    @Element
    double accessorialTotal;
    @Element
    double subTotal;
    @Element
    double total;
    @Element
    double normalizedTotal;

    @ElementList
    ArrayList<LoadPriceSheetCharge> loadPricesheetCharges;

    public LoadPriceSheet(@Element(name="id") int id, @Element(name="type") String type, @Element(name="isSelected") int isSelected, @Element(name="accessorialTotal") double accessorialTotal, @Element(name="subTotal") double subTotal, @Element(name="total") double total, @Element(name="normalizedTotal") double normalizedTotal, @ElementList(name="loadPricesheetCharges") ArrayList<LoadPriceSheetCharge> loadPricesheetCharges) {
        this.id = id;
        this.type = type;
        this.isSelected = isSelected;
        this.accessorialTotal = accessorialTotal;
        this.subTotal = subTotal;
        this.total = total;
        this.normalizedTotal = normalizedTotal;
        this.loadPricesheetCharges = loadPricesheetCharges;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public double getAccessorialTotal() {
        return accessorialTotal;
    }

    public void setAccessorialTotal(double accessorialTotal) {
        this.accessorialTotal = accessorialTotal;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getNormalizedTotal() {
        return normalizedTotal;
    }

    public void setNormalizedTotal(double normalizedTotal) {
        this.normalizedTotal = normalizedTotal;
    }

    public ArrayList<LoadPriceSheetCharge> getLoadPricesheetCharges() {
        return loadPricesheetCharges;
    }

    public void setLoadPricesheetCharges(ArrayList<LoadPriceSheetCharge> loadPricesheetCharges) {
        this.loadPricesheetCharges = loadPricesheetCharges;
    }
}
