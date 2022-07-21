package com.carriergistics.eld.logging.limits;

public class Alert {

    boolean executed;
    String message;

    int secsLeft;

    // This alert should be executed when the amount of seconds are left
    public Alert(String message, int secsLeft) {
        this.message = message;
        this.secsLeft = secsLeft;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSecsLeft() {
        return secsLeft;
    }

    public void setSecsLeft(int secsLeft) {
        this.secsLeft = secsLeft;
    }
}
