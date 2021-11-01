package main.java.com.carriergistics.eld.mapping.itemGroup;


public class LineItem{

    int id;
    String item_id;
    String description;
    double freightClass;
    String nmfcCode;
    String stccCode;
    String customerPartNum;
    String manufacturerPartNum;
    String distributerPartNum;
    boolean hazardous;

    public LineItem(int id, String item_id, String description, double freightClass, String nmfcCode, String stccCode, String customerPartNum, String manufacturerPartNum, String distributerPartNum, boolean hazardous) {
        this.id = id;
        this.item_id = item_id;
        this.description = description;
        this.freightClass = freightClass;
        this.nmfcCode = nmfcCode;
        this.stccCode = stccCode;
        this.customerPartNum = customerPartNum;
        this.manufacturerPartNum = manufacturerPartNum;
        this.distributerPartNum = distributerPartNum;
        this.hazardous = hazardous;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        return String.format("<loadPackageItemGroupLineItem><id>%d</id><itemId>%s</itemId><description>%s</description><freightClass>%f</freightClass><nmfcCode>%s</nmfcCode><stccCode>%s</stccCode><customerPartNum>%s</customerPartNum><manufacturePartNum>%s</manufacturePartNum><distributorPartNum>%s</distributorPartNum><hazardousMaterial>%d</hazardousMaterial></loadPackageItemGroupLineItem>",
                id, item_id, description, freightClass, nmfcCode, stccCode, customerPartNum, manufacturerPartNum, distributerPartNum, hazardous);
    }


}