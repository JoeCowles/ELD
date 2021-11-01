package main.java.com.carriergistics.eld.mapping.loads;

import java.security.KeyStore.Entry.Attribute;
import java.text.Format;

public class LoadEvent {
    
    int id;
    int sequenceNum;
    String type;
    int addressId;
    String earliestTime;
    String latestTime;
    String plannedTime;
    String actualTime;
    String appointmentTime;
    String appointmentTimeEarliest;
    String appointmentTimeLatest;
    String appointmentType;
    String lateReason;
    String weightType;
    double weight;
    String weightUom;

    ArrayList<Attribute> attributes;


    public LoadEvent(int id, int sequenceNum, String type, int addressId, String earliestTime, String latestTime, String plannedTime, String actualTime, String appointmentTime, String appointmentTimeEarliest, String appointmentTimeLatest, String appointmentType, String lateReason, String weightType, double weight, String weightUom, ArrayList<Attribute> attributes) {
        this.id = id;
        this.sequenceNum = sequenceNum;
        this.type = type;
        this.addressId = addressId;
        this.earliestTime = earliestTime;
        this.latestTime = latestTime;
        this.plannedTime = plannedTime;
        this.actualTime = actualTime;
        this.appointmentTime = appointmentTime;
        this.appointmentTimeEarliest = appointmentTimeEarliest;
        this.appointmentTimeLatest = appointmentTimeLatest;
        this.appointmentType = appointmentType;
        this.lateReason = lateReason;
        this.weightType = weightType;
        this.weight = weight;
        this.weightUom = weightUom;
        this.attributes = attributes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String toString(){
        String str = "<loadEvent> <id> %d </id> <sequenceNumber> %d </sequenceNumber> <type> %s </type> <addressId> %s </addressId> <earliest> %s </earliest>";
        str += " <latest> %s </latest> <planned> %s </planned> <actual> %s </actual> <appointment> %s </appointment> <appointmentEarly> %s </appointmentEarly>";
        str += " <appointmentLate> %s <appointmentLate> <appointmentType> %s </appointmentType> <lateReason> %s </lateReason> <weightType> %s </weightType>";
        str += " <weight> %f </weight> <weightUom> %s </weightUom> </loadEvent>";
        str = String.format(str, id, sequenceNum, type, addressId, earliestTime, latestTime, plannedTime, actualTime, appointmentTime, appointmentTimeEarliest, appointmentTimeLatest, appointmentType, lateReason, weightType, weight, weightUom);
        for(Attribute atr : attributes){
            str += " " + atr.toString();
        }
        return str;
    }
}
