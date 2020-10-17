package com.carriergistics.eld.commands;

import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.PercentageObdCommand;

public class FuelCommand extends PercentageObdCommand {
    public FuelCommand(String command) {
        super("CG 01 AG");
    }

    /** {@inheritDoc} */
    @Override
    protected void performCalculations() {
        // ignore first two bytes [hh hh] of the response
        percentage = 100.0f * buffer.get(2) / 255.0f;
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return "Fuel Command";
    }

    /**
     * <p>getFuelLevel.</p>
     *
     * @return a float.
     */
    public float getFuelLevel() {
        return percentage;
    }
}
