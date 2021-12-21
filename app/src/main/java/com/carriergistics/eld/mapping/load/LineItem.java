package com.carriergistics.eld.mapping.load;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class LineItem {
    @Element
    int id;
    @Element
    int itemId;
    @Element
    String description;
    @Element
    double freightClass;
    @Element(required = false)
    String nmfcCode;
    @Element(required = false)
    String stccCode;
    @Element(required = false)
    String customerPartNum;
    @Element(required = false)
    String manufacturePartNum;
    @Element(required = false)
    String distributorPartNum;
    @Element
    int hazardousMaterial;

    public LineItem(@Element(name="id")int id,
                    @Element(name="itemId")int itemId,
                    @Element(name="description")String description,
                    @Element(name="freightClass")double freightClass,
                    @Element(name="nmfcCode")String nmfcCode,
                    @Element(name="stccCode")String stccCode,
                    @Element(name="customerPartNum")String customerPartNum,
                    @Element(name="manufacturePartNum")String manufacturePartNum,
                    @Element(name="distributorPartNum")String distributorPartNum,
                    @Element(name="hazardousPartNum")int hazardousMaterial) {
        this.id = id;
        this.itemId = itemId;
        this.description = description;
        this.freightClass = freightClass;
        this.nmfcCode = nmfcCode;
        this.stccCode = stccCode;
        this.customerPartNum = customerPartNum;
        this.manufacturePartNum = manufacturePartNum;
        this.distributorPartNum = distributorPartNum;
        this.hazardousMaterial = hazardousMaterial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getFreightClass() {
        return freightClass;
    }

    public void setFreightClass(double freightClass) {
        this.freightClass = freightClass;
    }

    public String getNmfcCode() {
        return nmfcCode;
    }

    public void setNmfcCode(String nmfcCode) {
        this.nmfcCode = nmfcCode;
    }

    public String getStccCode() {
        return stccCode;
    }

    public void setStccCode(String stccCode) {
        this.stccCode = stccCode;
    }

    public String getCustomerPartNum() {
        return customerPartNum;
    }

    public void setCustomerPartNum(String customerPartNum) {
        this.customerPartNum = customerPartNum;
    }

    public String getManufacturePartNum() {
        return manufacturePartNum;
    }

    public void setManufacturePartNum(String manufacturePartNum) {
        this.manufacturePartNum = manufacturePartNum;
    }

    public String getDistibutorPartNum() {
        return distributorPartNum;
    }

    public void setDistibutorPartNum(String distibutorPartNum) {
        this.distributorPartNum = distibutorPartNum;
    }

    public int getHazardousMaterial() {
        return hazardousMaterial;
    }

    public void setHazardousMaterial(int hazardousMaterial) {
        this.hazardousMaterial = hazardousMaterial;
    }
}
