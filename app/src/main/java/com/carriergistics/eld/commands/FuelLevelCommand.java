package com.carriergistics.eld.commands;

public class FuelLevelCommand extends BluetoothCommand{

    public FuelLevelCommand() {
        super("CGREQ11213");
    }

    @Override
    protected void performCalculations() {

    }

    @Override
    public String getFormattedResult() {
        return null;
    }

    @Override
    public String getCalculatedResult() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
