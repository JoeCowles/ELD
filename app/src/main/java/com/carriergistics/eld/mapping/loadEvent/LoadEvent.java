package com.carriergistics.eld.mapping.loadEvent;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.util.ArrayList;
import java.util.List;

@Root
public class LoadEvent {
    @Element
    int id;
    @Element
    int sequenceNumber;
    @Element
    String type;
    @Element
    int addressId;
    @Element(required = false)
    String earliest;
    @Element(required = false)
    String latest;
    @Element(required = false)
    String planned;
    @Element(required = false)
    String actual;
    @Element(required = false)
    String appointment;
    @Element(required = false)
    String appointmentEarly;
    @Element(required = false)
    String appointmentLate;
    @Element(required = false)
    String appointmentType;
    @Element(required = false)
    String lateReason;
    @Element(required = false)
    String weightType;
    @Element
    double weight;
    @Element
    String weightUom;

    @ElementList
    ArrayList<LoadEventAttribute> loadEventAttributes;
    //@Element
    //LoadEventAttributes loadEventAttributes;
    public LoadEvent(@Element(name="id") int id,
                     @Element(name="sequenceNumber")int sequenceNumber,
                     @Element(name="type")String type,
                     @Element(name="addressId")int addressId,
                     @Element(name="earliest")String earliest,
                     @Element(name="latest")String latest,
                     @Element(name="planned")String planned,
                     @Element(name="actual")String actual,
                     @Element(name="appointment")String appointment,
                     @Element(name="appointmentEarly")String appointmentEarly,
                     @Element(name="appointmentLate")String appointmentLate,
                     @Element(name="appointmentType")String appointmentType,
                     @Element(name="lateReason")String lateReason,
                     @Element(name="weightType")String weightType,
                     @Element(name="weight")double weight,
                     @Element(name="weightUom")String weightUom,
                     @ElementList(name="loadEventAttributes")ArrayList<LoadEventAttribute> loadEventAttributes) {
        this.id = id;
        this.sequenceNumber = sequenceNumber;
        this.type = type;
        this.addressId = addressId;
        this.earliest = earliest;
        this.latest = latest;
        this.planned = planned;
        this.actual = actual;
        this.appointment = appointment;
        this.appointmentEarly = appointmentEarly;
        this.appointmentLate = appointmentLate;
        this.appointmentType = appointmentType;
        this.lateReason = lateReason;
        this.weightType = weightType;
        this.weight = weight;
        this.weightUom = weightUom;
        this.loadEventAttributes = loadEventAttributes;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getEarliest() {
        return earliest;
    }

    public void setEarliest(String earliest) {
        this.earliest = earliest;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }

    public String getPlanned() {
        return planned;
    }

    public void setPlanned(String planned) {
        this.planned = planned;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getAppointmentEarly() {
        return appointmentEarly;
    }

    public void setAppointmentEarly(String appointmentEarly) {
        this.appointmentEarly = appointmentEarly;
    }

    public String getAppointmentLate() {
        return appointmentLate;
    }

    public void setAppointmentLate(String appointmentLate) {
        this.appointmentLate = appointmentLate;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getLateReason() {
        return lateReason;
    }

    public void setLateReason(String lateReason) {
        this.lateReason = lateReason;
    }

    public String getWeightType() {
        return weightType;
    }

    public void setWeightType(String weightType) {
        this.weightType = weightType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getWeightUom() {
        return weightUom;
    }

    public void setWeightUom(String weightUom) {
        this.weightUom = weightUom;
    }

    public ArrayList<LoadEventAttribute> getLoadEventAttributes() {
        return loadEventAttributes;
    }

    public void setLoadEventAttributes(ArrayList<LoadEventAttribute> loadEventAttributes) {
        this.loadEventAttributes = loadEventAttributes;
    }
}
