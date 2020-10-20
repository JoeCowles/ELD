package com.carriergistics.eld.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.carriergistics.eld.logging.Driver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class Data {
    static Gson gson;
    static Context context;
    public static void init(Context context){
        gson = new Gson();
        setContext(context);
    }

    public static void saveDrivers(ArrayList<Driver> drivers){
        String toSave = gson.toJson(drivers);
        SharedPreferences sharedPreference = context.getSharedPreferences("Logs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString("Drivers", toSave);
    }
    public static ArrayList<Driver> loadDrivers(){
        String json = context.getSharedPreferences("Logs", Context.MODE_PRIVATE).getString("Drivers", "");
        return gson.fromJson(json, new TypeToken<List<Driver>>(){}.getType());
    }

    private static void setContext(Context con){
        context = con;
    }

}
