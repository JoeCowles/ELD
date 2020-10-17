package com.carriergistics.eld.logging;

public class HOSEventCodes {
    // Types of events
    public static final int CHANGE_EVENT_TYPE = 1;
    public static final int PERIODIC_EVENT_TYPE = 2;
    public static final int YARD_MOVE_EVENT_TYPE = 3;
    public static final int DRIVER_CERTIFICATION_TYPE = 4;
    public static final int LOGIN_TYPE = 5;
    public static final int ENGINE_STATE_TYPE = 6;
    public static final int MALFUNCTION_TYPE = 7;

    // ------------------ Event codes --------------------------

    // Change event
    public static final int OFF_DUTY = 1;
    public static final int SLEEPING = 2;
    public static final int DRIVING = 3;
    public static final int ON_DUTY_NOT_DRIVING = 4;

    // Periodic event
    public static final int PRECISE_ACCURACY = 1;
    public static final int REDUCED_ACCURACY = 2;

    // YARD MOVE event
    public static final int AUTH_USE_OF_CMV = 1;
    public static final int YARD_MOVE = 2;

    // Login event
    public static final int LOGIN = 1;
    public static final int LOGOUT = 2;

    // POWER-ON / SHUTDOWN (Engine state) event
    public static final int PRECISE_STARTUP = 1;
    public static final int REDUCED_STARTUP = 2;
    public static final int PRECISE_SHUTDOWN = 3;
    public static final int REDUCED_SHUTDOWN = 4;
}
