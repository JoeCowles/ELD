package main.java.com.carriergistics.eld.mapping;

public abstract class PackageMeasurement extends PackageAttribute{

    public PackageMeasurement(int id, String type, String dimType, double dim, String unit) {
        super(id, type, dimType, dim, unit);
    }

    public int getID(){
        return id;
    }
    
    public String toString(){
        if(unit == null || unit.isEmpty()){
            return String.format("<loadPackageMeasurement> <id> %f </id> <type> %s </type> <dimension> %d </dimension> <uom/> </loadPackageMeasurement>", id, type, quantity);
        }
        return String.format("<loadPackageMeasurement> <id> %f </id> <type> %s </type> <dimension> %d </dimension> <uom> %s <uom> </loadPackageMeasurement>", id, type, quantity, unit);
    }
    
}
