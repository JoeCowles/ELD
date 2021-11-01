package main.java.com.carriergistics.eld.mapping;

public class PackageAttribute {
    
    int id;
    String type;
    String dimType;
    double attribute;
    String unit;

    public PackageAttribute(int id, String type, String dimType, double attribute, String unit) {
        this.id = id;
        this.type = type;
        this.dimType = dimType;
        this.attribute = attribute;
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString(){
        return String.format("<loadPackageDimension> <id>%s<id> <type>%s</type> <dim>%s</dim> <dimension>%f<dimension> <uom>%s</uom> </loadPackageDimension>", id, type, dimType, attribute, unit);
    }
    
}
