package com.carriergistics.eld.commands;

import android.util.Log;

import com.carriergistics.eld.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SetTimeCommand {
    OutputStream output;
    InputStream in;
    long responseTime = 500L;
    String input = "";
    public SetTimeCommand(OutputStream output){
        this.output = output;
    }
    public boolean run(String time, InputStream in) throws IOException, InterruptedException {
        MainActivity.dontSendBt();
        String command = "CGCMD01";
        command += calcChecksum(command);
        output.write((command+"\r").getBytes());
        output.flush();
        this.in = in;
        Log.d("DEBUGGING", "Setting time");
        input = waitForGoodResponse();

        if(input.contains("OK")){
            String response = "CG!" + time + "!";
            response += calcChecksum(response) + "";
            output.write((response + "\r").getBytes());
            Log.d("DEBUGGING", response);
        }

        input = waitForGoodResponse();
        MainActivity.sendBt();
        if(input != null){
            return true;
        }
        return false;
    }
    public String readResult() throws IOException{
        String rawData;
        byte b = 0;
        StringBuilder res = new StringBuilder();

        // read until '>' arrives OR end of stream reached
        char c;
        // -1 or 10 if the end of the stream is reached
        if(in.available() > 0) {

            while (((b = (byte) in.read()) > -1) && b != 10){
                c = (char) b;
                if (c == '>') // read until '>' arrives
                {
                    break;
                }
                try{
                    res.append(c);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else{
            Log.d("DEBUGGING", "No Message");
        }
        rawData = res.toString().replaceAll("SEARCHING", "");
        Log.d("DEBUGGING", rawData + " From bluetooth");
        return rawData;
    }
    private int calcChecksum(String toCalc){
        int sum = 0;
        for(int i = 0; i < toCalc.length(); i++){
            sum += toCalc.charAt(i);
        }
        sum %= 255;
        Log.d("DEBUGGING", "Checksum: "+ sum);
        return sum;
    }
    private String waitForGoodResponse() throws IOException, InterruptedException {
        String response = "";
        int attempts = 0;
        while((in.available() <= 0 &&  attempts < 50) || ((response = readResult()).isEmpty() && attempts < 50)){
            Thread.sleep(100);
            attempts++;
        }
        return response;
    }
}
