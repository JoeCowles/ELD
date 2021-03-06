package com.carriergistics.eld.bluetooth;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.carriergistics.eld.MainActivity;
import com.carriergistics.eld.commands.FuelEconCommand;
import com.carriergistics.eld.commands.FuelLevelCommand;
import com.carriergistics.eld.commands.OdoCommand;
import com.carriergistics.eld.commands.SpeedCommand;
import com.carriergistics.eld.commands.RPMCommand;
import com.carriergistics.eld.commands.SetBluetoothNameCommand;
import com.carriergistics.eld.commands.SetTimeCommand;
import com.carriergistics.eld.commands.VinCommand;
import com.carriergistics.eld.logging.HOSLogger;
import com.carriergistics.eld.utils.DataConverter;
import com.carriergistics.eld.utils.Debugger;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class BluetoothConnector {
    private static BluetoothSocket socket;
    private static BlueToothStatus status = BlueToothStatus.AVAILABLE;
    private static SpeedCommand speedCommand;
    private static RPMCommand rpmCommand;
    private static BluetoothDevice device;
    private static int reconnectAttempts = 0;
    private static String name = "hc-05";
    private static String password = "1234";

    public static boolean connect(String deviceID){
        status = BlueToothStatus.CONNECTING;
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        device  = btAdapter.getRemoteDevice(deviceID);
        speedCommand = new SpeedCommand();
        rpmCommand = new RPMCommand();
        if(connect()){
            status = BlueToothStatus.CONNECTED;
            return true;
        }else{
            return false;
        }

    }

    public static void sendRequests() {
        TelematicsData data;
        if (getStatus() == BlueToothStatus.CONNECTED){
            try {
                speedCommand.run(socket.getInputStream(), socket.getOutputStream());
                rpmCommand.run(socket.getInputStream(), socket.getOutputStream());
                data = new TelematicsData();

                int speed = DataConverter.speedMPH(speedCommand.getResult());
                int rpm = DataConverter.convertRPM(rpmCommand.getResult());

                data.setSpeed(speed);
                data.setRpm(rpm);
                Message message = Message.obtain();
                message.obj = data;
                HOSLogger.handler.sendMessage(message);
                reconnectAttempts = 0;
            } catch (InterruptedIOException e){
                Log.e("ERROR", "Connection interupted, " + e.getMessage());
            } catch (IOException e){
                disconnect();
                Thread connector = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        connect(device.getAddress());
                    }
                });
                connector.start();
                reconnectAttempts++;
                if(reconnectAttempts >= 5){
                    Log.d("DEBUGGING", "Lost connection... sending stopped data");
                    TelematicsData stopped = new TelematicsData();
                    stopped.setSpeed(0);
                    stopped.setRpm(0);
                    Message message = Message.obtain();
                    message.obj = stopped;
                    HOSLogger.handler.sendMessage(message);
                    status = BlueToothStatus.AVAILABLE;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    private static boolean connect(){
        boolean connected = false;
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        try {
            socket = device.createInsecureRfcommSocketToServiceRecord(uuid);
            socket.connect();
            connected = true;
        } catch (IOException e) {
            disconnect();
            connected = false;
            BluetoothSocket tmp = socket;
            Class<?> clazz = tmp.getRemoteDevice().getClass();
            Class<?>[] paramTypes = new Class<?>[]{Integer.TYPE};
            Method m = null;
            try {
                Object[] params = new Object[]{Integer.valueOf(1)};
                m = clazz.getMethod("createRfcommSocket", paramTypes);
                socket = (BluetoothSocket) m.invoke(tmp.getRemoteDevice(), params);
                socket.connect();
                connected = true;
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
                disconnect();
                connected = false;
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
                disconnect();
                connected = false;
            } catch (IOException ex) {
                ex.printStackTrace();
                disconnect();
                connected = false;
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
                disconnect();
                connected = false;
            }

        }
        return connected;
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
    /*public static void renameDevice(String _name) throws IOException {
        name = _name;
        SetBluetoothNameCommand command = new SetBluetoothNameCommand(socket.getOutputStream());
        try {
            command.run(name, password, socket.getInputStream());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/
    public static void renameDevice(String _name, String _password) throws IOException {
        name = _name;
        password = _password;
        SetBluetoothNameCommand command = new SetBluetoothNameCommand(socket.getOutputStream());
        try {
            command.run(name, password, socket.getInputStream());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void setTime(String time) throws IOException {
        SetTimeCommand cmd = new SetTimeCommand(socket.getOutputStream());
        try{
            cmd.run(time, socket.getInputStream());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public static String getVinNum(){

        VinCommand vinCommand = new VinCommand();
        try {
            vinCommand.run(socket.getInputStream(),socket.getOutputStream());
        } catch (IOException e) {
            Debugger.print("BT","Tried to read the vin, threw an IOException" + e.getMessage());
            return "Couldn't retrieve";
        } catch (InterruptedException e) {
            Debugger.print("BT","Tried to read the vin, threw an Interrupted Exception" + e.getMessage());
            return "Couldn't retrieve";
        }
        Debugger.print("BT","Tried to read the vin, the bluetooth command returned: " + vinCommand.getResult());
        return vinCommand.getResult().substring(0,17);
    }
    public static String getFuelLevel(){
        FuelLevelCommand fuelCmd = new FuelLevelCommand();
        try {
            fuelCmd.run(socket.getInputStream(),socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return fuelCmd.getResult();
    }
    public static String getFuelEcon(){

        FuelEconCommand fuelCmd = new FuelEconCommand();
        try {
            fuelCmd.run(socket.getInputStream(),socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return fuelCmd.getResult();
    }
    public static int getOdo(){
        OdoCommand command = new OdoCommand();
        try {
            command.run(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            if(command.getResult() != null){
                Debugger.print("BT", "Tried to read the odo, the bluetooth command returned: " + command.getResult());
            }else{
                Debugger.print("BT", "The BT command failed!");
            }
            return Integer.parseInt(command.getResult());
        }catch (NumberFormatException e){
            return 0;
        }
    }
}
