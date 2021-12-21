package com.carriergistics.eld.mapping.loadPackage.ItemGroup;

import com.carriergistics.eld.mapping.load.LineItem;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

@Default
public class LoadPackageItemGroupLineItem extends LineItem {

    public LoadPackageItemGroupLineItem(@Element(name="id") int id,
                                        @Element(name="itemId")int itemId,
                                        @Element(name="description")String description,
                                        @Element(name="freightClass")double freightClass,
                                        @Element(name="nmfcCode")String nmfcCode,
                                        @Element(name="stccCode")String stccCode,
                                        @Element(name="customerPartNum")String customerPartNum,
                                        @Element(name="manufacturePartNum")String manufacturePartNum,
                                        @Element(name="distributorPartNum")String distributorPartNum,
                                        @Element(name="hazardousMaterial") int hazardousMaterial) {
        super(id, itemId, description, freightClass, nmfcCode, stccCode, customerPartNum, manufacturePartNum, distributorPartNum, hazardousMaterial);
    }
}
