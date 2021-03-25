package com.carriergistics.eld.commands;

public class OdoCommand extends BluetoothCommand{

    public OdoCommand() {
        super("CGREQ04215");
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
