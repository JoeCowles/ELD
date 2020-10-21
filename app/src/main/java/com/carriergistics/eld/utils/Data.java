package com.carriergistics.eld.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
        SharedPreferences sharedPreference = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        Log.d("DEBUGGING", "Saving........................" + toSave);
        editor.putString("Drivers", toSave);
        editor.commit();
    }
    public static ArrayList<Driver> loadDrivers(){
        String json = context.getSharedPreferences("Settings", Context.MODE_PRIVATE).getString("Drivers", "");
        ArrayList<Driver> drivers = gson.fromJson(json, new TypeToken<List<Driver>>(){}.getType());
        if(drivers == null || drivers.isEmpty()){
            Log.d("DEBUGGING", "COULDNT FIND DATA.....................................................");
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
    }
    private static void setContext(Context con){
        context = con;
    }

}
