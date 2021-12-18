package com.carriergistics.eld.mapping.priceSheet;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root
public class PriceSheet {
    @Element
    int id;
    @Element
    String type;
    @Element
    int isSelected;
    @Element
    double accessorialATotal;
    @Element
    double subTotal;
    @Element
    double total;
    @Element
    double normalizedTotal;

    @Element
    ArrayList<PriceSheetCharge> loadPriceSheetCharges;

    public PriceSheet(@Element(name="id") int id, @Element(name="type") String type, @Element(name="isSelected") int isSelected, @Element(name="accessorialTotal") double accessorialATotal, @Element(name="subTotal") double subTotal, @Element(name="total") double total, @Element(name="normalizedTotal") double normalizedTotal, @Element(name="loadPriceSheetCharges") ArrayList<PriceSheetCharge> loadPriceSheetCharges) {
        this.id = id;
        this.type = type;
        this.isSelected = isSelected;
        this.accessorialATotal = accessorialATotal;
        this.subTotal = subTotal;
        this.total = total;
        this.normalizedTotal = normalizedTotal;
        this.loadPriceSheetCharges = loadPriceSheetCharges;
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

    public double getAccessorialATotal() {
        return accessorialATotal;
    }

    public void setAccessorialATotal(double accessorialATotal) {
        this.accessorialATotal = accessorialATotal;
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

    public ArrayList<PriceSheetCharge> getLoadPriceSheetCharges() {
        return loadPriceSheetCharges;
    }

    public void setLoadPriceSheetCharges(ArrayList<PriceSheetCharge> loadPriceSheetCharges) {
        this.loadPriceSheetCharges = loadPriceSheetCharges;
    }
}
