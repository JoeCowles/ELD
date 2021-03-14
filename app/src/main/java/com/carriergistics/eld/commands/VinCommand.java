package com.carriergistics.eld.commands;

public class VinCommand extends BluetoothCommand{

    public VinCommand() {
        super("CGREQ20213");
    }

    @Override
    protected void performCalculations() {

    }

    @Override
    public String getFormattedResult() {
        String vin = "";
        for(int i : buffer){
            vin += (char) i;
        }
        return vin;
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
