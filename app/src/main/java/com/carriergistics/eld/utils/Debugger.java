package com.carriergistics.eld.utils;

import android.Manifest;

import com.carriergistics.eld.MainActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class Debugger {

    private static FileOutputStream fos;
    private static BufferedWriter writer;
    private static File saveFile;
    private static String path;
    private static final String FILENAME = "DebugLog.txt";

    private static final int REQUEST_NUM = 1023;

    public static void init() throws IOException {

        path = MainActivity.instance.getExternalCacheDir().getAbsolutePath();
        path += File.separator + "debug" + File.separator + FILENAME;

        saveFile = new File(path);
        saveFile.createNewFile();
        fos = new FileOutputStream(saveFile);
        writer = new BufferedWriter(new FileWriter(saveFile));

        MainActivity.instance.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_NUM);

    }


    public static void print(String tag, String data){
        data = String.format("%s -> %s    %s\n", MainActivity.getTime().toString(), tag, data);
        try {
            writer.append(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void close() throws IOException {
        writer.close();
    }
}
