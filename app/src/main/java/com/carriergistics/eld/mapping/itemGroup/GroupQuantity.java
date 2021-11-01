package main.java.com.carriergistics.eld.mapping.itemGroup;

public class GroupQuantity extends PackageQuantity{
    
    public GroupQuantity(int id, String type, double quantity, String unit) {
        super(id, type, type, quantity, unit);
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public String toString(){
        if(unit == null || unit.isEmpty()){
            return String.format("<loadPackageItemGroupQuantity> <id> %f </id> <type> %s </type> <quantity> %d </quantity> <uom/> </loadPackageItemGroupQuantity>", id, type, quantity);
        }
        return String.format("<loadPackageItemGroupQuantity> <id> %f </id> <type> %s </type> <quantity> %d </quantity> <uom> %s <uom> </loadPackageItemGroupQuantity>", id, type, quantity, unit);
    }
}
