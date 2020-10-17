package com.carriergistics.eld.commands;

import com.github.pires.obd.commands.ObdCommand;

public class MPHCommand extends BluetoothCommand{
    private int metricSpeed;

    public MPHCommand() {
        super("CGREQ01212");
    }

    @Override
    protected void performCalculations() {
        metricSpeed = buffer.get(2);
    }

    @Override
    public String getFormattedResult() {
        return useImperialUnits ? String.format("%.2f%s", getImperialUnit(), getResultUnit())
                : String.format("%d%s", getMetricSpeed(), getResultUnit());
    }

    @Override
    public String getCalculatedResult() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
    public float getImperialUnit() {
        return metricSpeed * 0.621371192F;
    }
    public float getImperialSpeed() {
        return getImperialUnit();
    }
    public int getMetricSpeed() {
        return metricSpeed;
    }
}
