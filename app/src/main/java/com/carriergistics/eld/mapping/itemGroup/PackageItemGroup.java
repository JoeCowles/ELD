package main.java.com.carriergistics.eld.mapping.ItemGroup;

import java.util.ArrayList;

public class PackageItemGroup{

    int id;
    String description;
    ArrayList<PackageItemGroupDim> dims;
    ArrayList<PackageItemGroupQuantity> quantities;
    ArrayList<PackageItemGroupWeight> weights;
    ArrayList<LineItem> lineItems;

    public PackageItemGroup(int id, String description, ArrayList<PackageItemGroupDim> dims, ArrayList<PackageItemGroupQuantity> quantities, ArrayList<LineItem> lineItems) {
        this.id = id;
        this.description = description;
        this.dims = dims;
        this.lineItems = lineItems;
        this.quantities = quantities;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    
    public String toString(){
        String str = "<loadPackageItemGroup>";
        str += "<id>" + id + "</id>";
        str += "<description>" + description + "</description>";
        str += "<loadPackageItemGroupDimensions>";
        for(PackageItemGroupDim dim : dims){
            str += dim.toString();
        }
        str += "</loadPackageItemGroupDimensions>";
        str += "<loadPackageItemGroupQuantities>";
        for(PackageItemGroupQuantity quantity : quantities){
            str += quantity.toString();
        }
        str += "</loadPackageItemGroupQuantities>";
        str += "<loadPackageItemGroupWeights>";
        for(PackageItemGroupWeight weight : weights){
            str += weight.toString();
        }
        str += "</loadPackageItemGroupWeights>";
        str += "<lineItems>";
        for(LineItem lineItem : lineItems){
            str += lineItem.toString();
        }
        str += "</lineItems>";
        str += "</loadPackageItemGroup>";
        return str;
        

    }

}