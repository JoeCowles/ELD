package com.carriergistics.eld.mapping.loadPackage.ItemGroup;

import com.carriergistics.eld.mapping.load.Quantity;

import org.simpleframework.xml.Element;

public class LoadPackageItemGroupQuantity extends Quantity {

    public LoadPackageItemGroupQuantity(@Element(name="id") int id,
                                        @Element(name="type")String type,
                                        @Element(name="quantity")double quantity,
                                        @Element(name="uom") String uom) {
        super(id, type, quantity, uom);
    }
}
