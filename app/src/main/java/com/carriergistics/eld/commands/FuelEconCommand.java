package com.carriergistics.eld.commands;


public class FuelEconCommand extends BluetoothCommand {
    public FuelEconCommand() {
        super("CGREQ12214");
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
