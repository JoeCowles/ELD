package com.carriergistics.eld.mapping.priceSheet;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class LoadPriceSheetCharge {
    @Element
    int id;
    @Element
    int sequenceNumber;
    @Element(required = false)
    String description;
    @Element(required = false)
    String ediCode;
    @Element(required = false)
    double amount;
    @Element(required = false)
    double rate;
    @Element(required = false)
    String rateQualifier;
    @Element(required = false)
    double quantity;
    @Element(required = false)
    double weight;
    @Element(required = false)
    double dimWeight;
    @Element(required = false)
    double freightClass;

    public LoadPriceSheetCharge(@Element(name ="id")int id,
                                @Element(name ="sequenceNumber") int sequenceNumber,
                                @Element(name ="description") String description,
                                @Element(name ="ediCode") String ediCode,
                                @Element(name ="amount") double amount,
                                @Element(name ="rate") double rate,
                                @Element(name ="rateQualifier") String rateQualifier,
                                @Element(name ="quantity") double quantity,
                                @Element(name ="weight") double weight,
                                @Element(name ="dimWeight") double dimWeight,
                                @Element(name ="freightClass") double freightClass) {
        this.id = id;
        this.sequenceNumber = sequenceNumber;
        this.description = description;
        this.ediCode = ediCode;
        this.amount = amount;
        this.rate = rate;
        this.rateQualifier = rateQualifier;
        this.quantity = quantity;
        this.weight = weight;
        this.dimWeight = dimWeight;
        this.freightClass = freightClass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEdiCode() {
        return ediCode;
    }

    public void setEdiCode(String ediCode) {
        this.ediCode = ediCode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getRateQualifier() {
        return rateQualifier;
    }

    public void setRateQualifier(String rateQualifier) {
        this.rateQualifier = rateQualifier;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDimWeight() {
        return dimWeight;
    }

    public void setDimWeight(double dimWeight) {
        this.dimWeight = dimWeight;
    }

    public double getFreightClass() {
        return freightClass;
    }

    public void setFreightClass(double freightClass) {
        this.freightClass = freightClass;
    }
}