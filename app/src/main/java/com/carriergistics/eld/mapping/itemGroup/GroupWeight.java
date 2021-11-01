package main.java.com.carriergistics.eld.mapping.itemGroup;

import main.java.com.carriergistics.eld.mapping.PackageAttribute;

public class GroupWeight extends PackageAttribute{
    
    public GroupWeight(int id, String type, double weight, String unit) {
        super(id, type, weight, unit);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String toString(){
        return String.format("<loadPackageItemGroupWeight> <id> %s </id> <type> %s </type> <weight> %f </weight> <uom> %s </uom> </loadPackageItemGroupWeight>", id, type, weight, unit);
    } 
}
