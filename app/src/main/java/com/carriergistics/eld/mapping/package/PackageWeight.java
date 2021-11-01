package main.java.com.carriergistics.eld.mapping;

public class PackageWeight extends PackageAttribute{


    public PackageWeight(int id, String type, double weight, String units) {
        super(id, type, type, weight, units);
    }

    public String toString(){
        if(unit == null || unit.isEmpty()){
            return String.format("<loadPackageItemGroupWeight> <id> %f </id> <type> %s </type> <weight> %d </weight> <uom/> </loadPackageGroupItem>", id, type, quantity);
        }
        return String.format("<loadPackageItemGroupWeight> <id> %f </id> <type> %s </type> <weight> %d </weight> <uom> %s <uom> </loadPackageItemGroupWeight>", id, type, quantity, unit);
    }

}