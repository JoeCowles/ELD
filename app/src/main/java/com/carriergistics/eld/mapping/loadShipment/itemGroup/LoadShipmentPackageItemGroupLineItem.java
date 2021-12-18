package com.carriergistics.eld.mapping.loadShipment.itemGroup;

import com.carriergistics.eld.mapping.load.LineItem;

public class LoadShipmentPackageItemGroupLineItem extends LineItem {

    public LoadShipmentPackageItemGroupLineItem(int id, int itemId, String description, double freightClass, String nmfcCode, String stccCode, String customerPartNum, String manufacturePartNum, String distibutorPartNum, int hazardousMaterial) {
        super(id, itemId, description, freightClass, nmfcCode, stccCode, customerPartNum, manufacturePartNum, distibutorPartNum, hazardousMaterial);
    }
}
