package com.carriergistics.eld.commands;

import com.github.pires.obd.commands.PercentageObdCommand;

public class ThrottleCommand extends PercentageObdCommand {
    public ThrottleCommand() {
        super("CG AP  01");
    }

    @Override
    public String getName() {
        return "ThrottleCommand";
    }
}
