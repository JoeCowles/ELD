package com.carriergistics.eld.bluetooth;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.carriergistics.eld.commands.MPHCommand;
import com.carriergistics.eld.logging.HOSLogger;
import com.carriergistics.eld.ui.HomeFragment;
import com.carriergistics.eld.utils.DataConverter;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class BluetoothConnector {
    private static BluetoothSocket socket;
    private static BluetoothSocket fallbackSocket;
    private static BlueToothStatus status;
    private static String rpm;
    private static String gear, runTime;
    private static boolean fallback;
    private static int mphRaw;
    private static Handler handler;
    private static MPHCommand speedCommand;
    private static BluetoothDevice device;
    public static void connect(String deviceID){
        fallback = false;
        status = BlueToothStatus.CONNECTING;
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        device  = btAdapter.getRemoteDevice(deviceID);
        speedCommand = new MPHCommand();
        startThread();
    }

    private static void startThread() {

        Thread btRunner = new Thread(new Runnable() {
            @Override
            public void run() {
                TelematicsData data;
                UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                try {
                    Log.d("DEBUGGING", device.toString() + " DEVICE <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    socket = device.createInsecureRfcommSocketToServiceRecord(uuid);
                    socket.connect();
                    Log.d("DEBUGGING", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Didnt disconnect");
                } catch (IOException e) {
                    disconnect();
                    Log.d("DEBUGGING", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Disconnected... Reconnecting");
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
                        Log.d("DEBUGGING", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Reconnected");
                    } catch (NoSuchMethodException ex) {
                        ex.printStackTrace();
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        disconnect();
                        try {
                            Thread.currentThread().join();
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        Log.d("DEBUGGING", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + e.getMessage());
                    } catch (InvocationTargetException ex) {
                        ex.printStackTrace();
                    }

                }
                status = BlueToothStatus.CONNECTED;
                while (getStatus() == BlueToothStatus.CONNECTED){
                    try {
                        speedCommand.run(socket.getInputStream(), socket.getOutputStream());
                        data = new TelematicsData();
                        int speed = DataConverter.speedMPH(speedCommand.getResult());
                        data.setSpeed(speed);
                        //data.setSpeed((int)speedCommand.getImperialSpeed());
                        HOSLogger.log(data);
                        Message message = Message.obtain();
                        message.obj = data;
                        HomeFragment.handler.sendMessage(message);
                    } catch (InterruptedIOException e){
                        Log.e("ERROR", "Connection interupted, " + e.getMessage());
                    } catch (IOException e){
                        disconnect();
                        connect(device.getAddress());
                        try {
                            Thread.currentThread().join();
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                       // Log.d("DEBUGGING", speedCommand.getResult());
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("DEBUGGING", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + e.getMessage());
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
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("DEBUGGING", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + e.getMessage());
        }
    }
}
