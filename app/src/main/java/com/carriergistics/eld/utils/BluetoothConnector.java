package com.carriergistics.eld.utils;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Message;
import com.github.pires.obd.commands.SpeedCommand;
import com.github.pires.obd.commands.engine.RPMCommand;
import com.github.pires.obd.commands.engine.RuntimeCommand;
import com.github.pires.obd.commands.protocol.ObdResetCommand;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class BluetoothConnector {
    private static BluetoothSocket socket;
    private static BluetoothSocket fallbackSocket;
    private static BlueToothStatus status;
    private static SpeedCommand speedCommand;
    private static String rpm;
    private static RPMCommand rpmCommand;
    private static ObdResetCommand initCommand;
    private static RuntimeCommand runtimeCommand;
    private static String gear, runTime;
    private static boolean fallback;
    private static int mphRaw;
    public static void connect(String deviceID){
        fallback = false;
        status = BlueToothStatus.CONNECTING;
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

        BluetoothDevice device = btAdapter.getRemoteDevice(deviceID);

        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            try {
                socket = device.createInsecureRfcommSocketToServiceRecord(uuid);
                socket.connect();
            } catch (IOException e) {
                BluetoothSocket tmp = socket;
                Class<?> clazz = tmp.getRemoteDevice().getClass();
                Class<?>[] paramTypes = new Class<?>[]{Integer.TYPE};
                Method m = null;
                try {
                    Object[] params = new Object[]{Integer.valueOf(1)};
                    m = clazz.getMethod("createRfcommSocket", paramTypes);
                    socket = (BluetoothSocket) m.invoke(tmp.getRemoteDevice(), params);
                    socket.connect();
                    fallback = true;
                } catch (NoSuchMethodException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                }

            }
        status = BlueToothStatus.CONNECTED;
        startThread();
    }

    private static void startThread() {
        runtimeCommand = new RuntimeCommand();
        speedCommand = new SpeedCommand();
        rpm = "";
        gear = "";
        rpmCommand = new RPMCommand();
        initCommand = new ObdResetCommand();
        Thread btRunner = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    initCommand.run(socket.getInputStream(), socket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (getStatus() == BlueToothStatus.CONNECTED){
                    try {
                        speedCommand.run(socket.getInputStream(), socket.getOutputStream());
                        rpmCommand.run(socket.getInputStream(), socket.getOutputStream());
                        runTime = runtimeCommand.getFormattedResult();
                        rpm = rpmCommand.getCalculatedResult();
                        mphRaw = Integer.parseInt(speedCommand.getCalculatedResult());
                        TelematicsData data = new TelematicsData(mphRaw, Integer.parseInt(rpm), 0, 0.0f, runTime);
                        Message message = Message.obtain();
                        message.obj = data;
                        DataLogger.log(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        btRunner.start();
    }

    public static BlueToothStatus getStatus(){
        return status;
    }
    public static void disconnect(){
        status = BlueToothStatus.AVAILABLE;
    }
}
