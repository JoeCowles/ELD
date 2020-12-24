package com.carriergistics.eld;


import android.util.Log;

import com.carriergistics.eld.bluetooth.BluetoothConnector;
import com.carriergistics.eld.logging.HOSLogger;

import java.util.TimerTask;

public class Ticker extends TimerTask {
    @Override
    public void run() {
        MainActivity.tick();
    }
}
