package main.java.com.carriergistics.eld.mapping;
import java.util.ArrayList;

public class LoadPackage {
    
    int id;
    ArrayList<PackageDimension> dimensions = new ArrayList<PackageDimension>();
    ArrayList<PackageWeight> weights = new ArrayList<PackageWeight>();
    ArrayList<PackageItemGroup> groups;

    public LoadPackage(int id, ArrayList<PackageDimension> dimensions, ArrayList<PackageWeight> weights, ArrayList<PackageItemGroup> groups) {
        this.id = id;
        this.dimensions = dimensions;
        this.weights = weights;
        this.groups = groups;
    }

    public LoadPackage(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        String str = "<loadPackage> <id>%f</id> ";
        str += "<loadPackageDimensions>";
        for(PackageDimension dimension : dimensions){
            str += dimension.toString();
        }
        str += "</loadPackageDimensions>";
        str += "<loadPackageWeights>";
        for(PackageWeight weight : weights){
            str += weight.toString();
        }
        str += "</loadPackageWeights>";
        str += group.toString();
        str += "<loadPackageItemGroups>";
        for(PackageItemGroup group : groups){
            str += group.toString();
        }
        str += "</loadPackageItemGroups>";
        str += "</loadPackage>";
        return str;
    }
}
