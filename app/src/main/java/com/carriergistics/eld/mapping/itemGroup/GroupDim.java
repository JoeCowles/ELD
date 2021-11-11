package com.carriergistics.eld.mapping.itemGroup;

public class GroupDim extends PackageAttribute{

    public GroupDim(int id, String type, String dimType, double dim, String dimUnit) {
        super(id, type, dimType, dim, dimUnit);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String toString(){
        if(unit == null || unit.isEmpty()){
            return String.format("<loadPackageItemGroupDimension> <id> %f </id> <type> %s </type> <dim> %d </dim> <dimension> %f </dimension> <uom/> </loadPackageItemGroupDimension>", id, type, dimType, dim);
        }
        return String.format("<loadPackageItemGroupDimension> <id> %f </id> <type> %s </type> <dim> %d </dim> <dimesion> %f </dimension>  <uom> %s <uom> </loadPackageItemGroupDimension>", id, type, dimType, dim, unit);
    }
}
