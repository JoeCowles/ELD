package com.carriergistics.eld.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.carriergistics.eld.dvir.Vehicle;
import com.carriergistics.eld.logging.Driver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
/*

    Data saved in the app

 */
public class Data {
    static Gson gson;
    static Context context;
    public static void init(Context context){
        gson = new Gson();
        setContext(context);
    }

    public static void saveDrivers(ArrayList<Driver> drivers){
        String toSave = gson.toJson(drivers);
        SharedPreferences sharedPreference = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString("Drivers", toSave);
        editor.commit();
    }
    public static ArrayList<Driver> loadDrivers(){
        String json = context.getSharedPreferences("Settings", Context.MODE_PRIVATE).getString("Drivers", "");
        ArrayList<Driver> drivers = gson.fromJson(json, new TypeToken<List<Driver>>(){}.getType());
        if(drivers == null || drivers.isEmpty()){
            return new ArrayList<Driver>();
        }else{
            return drivers;
        }
    }
    public static Company loadCompany(){
        Company company;
        Gson gson = new Gson();
        SharedPreferences  sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        company = gson.fromJson(sharedPreferences.getString("Company", ""), Company.class);
        if(company == null){
            return new Company();
        }
        return company;
    }
    public static void saveCompany(Company company){
        SharedPreferences  sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString("Company", gson.toJson(company));
        editor.commit();
    }
    public static void saveVehicles(ArrayList<Vehicle> vehicles){
        String toSave = gson.toJson(vehicles);
        SharedPreferences sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Vehicles", toSave);
        editor.commit();
    }
    public static ArrayList<Vehicle> loadVehicles(){
        String json = context.getSharedPreferences("Settings", Context.MODE_PRIVATE).getString("Vehicles", "");
        ArrayList<Vehicle> vehicles = gson.fromJson(json, new TypeToken<List<Vehicle>>(){}.getType());
        if(vehicles == null || vehicles.isEmpty()){
            return new ArrayList<Vehicle>();
        }
        return vehicles;
    }
    public static void saveSettings(Settings settings){
        SharedPreferences  sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString("SettingsSaved", gson.toJson(settings));
        editor.commit();
    }
    public static Settings loadSettings(){
        Settings settings;
        Gson gson = new Gson();
        SharedPreferences  sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        settings = gson.fromJson(sharedPreferences.getString("SettingsSaved", ""), Settings.class);
        if(settings == null){
            return new Settings();
        }
        return settings;
    }
    private static void setContext(Context con){
        context = con;
    }

}
