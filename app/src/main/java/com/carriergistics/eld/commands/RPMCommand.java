package com.carriergistics.eld.commands;


public class RPMCommand extends BluetoothCommand {
    int rpm;
    public RPMCommand() {
        super("CGREQ02213");
    }

    @Override
    protected void performCalculations() {
        // ignore first two bytes [41 0C] of the response((A*256)+B)/4
        rpm = (buffer.get(2) * 256 + buffer.get(3)) / 4;
    }

    /** {@inheritDoc} */
    @Override
    public String getFormattedResult() {
        return String.format("%d%s", rpm, getResultUnit());
    }

    /** {@inheritDoc} */
    @Override
    public String getCalculatedResult() {
        return String.valueOf(rpm);
    }

    /** {@inheritDoc} */
    @Override
    public String getResultUnit() {
        return "RPM";
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return null;
    }

    /**
     * <p>getRPM.</p>
     *
     * @return a int.
     */
    public int getRPM() {
        return rpm;
    }
}
