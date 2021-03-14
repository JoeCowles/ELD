package com.carriergistics.eld;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.carriergistics.eld.bluetooth.BlueToothStatus;
import com.carriergistics.eld.commands.BluetoothCommand;
import com.carriergistics.eld.logging.Day;
import com.carriergistics.eld.logging.Driver;
import com.carriergistics.eld.logging.HOSLogger;
import com.carriergistics.eld.logging.Status;
import com.carriergistics.eld.setup.InitActivity;
import com.carriergistics.eld.ui.DriversFragment;
import com.carriergistics.eld.dvir.DvirFragment;
import com.carriergistics.eld.ui.HomeFragment;
import com.carriergistics.eld.ui.LogFragment;
import com.carriergistics.eld.ui.LogViewerFragment;
import com.carriergistics.eld.ui.RoutesFragment;
import com.carriergistics.eld.settings.SettingsFragment;
import com.carriergistics.eld.bluetooth.BluetoothConnector;
import com.carriergistics.eld.ui.StoppedFragment;
import com.carriergistics.eld.utils.Data;
import com.carriergistics.eld.utils.DataConverter;
import com.carriergistics.eld.utils.Settings;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/************************************************************
   Copyright 2020 Unboxed Industries
    Author - Joe Cowles
 ************************************************************/
public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private static Class currentlyInflated;
    public static FragmentManager fragmentManager;
    public static Driver currentDriver;
    public static Driver secondaryDriver;
    public static ArrayList<Driver> drivers;
    private static Settings settings;
    private static boolean gotDisconnected = false;
    public static MainActivity instance;
    private static boolean shouldSendBt = true;
    private static Date currentTime;
    private static Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateTime();
        // Has been changed to request all perms
        checkPermission(Manifest.permission.BLUETOOTH, 100);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 100);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This will display an Up icon (<-),  will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navDrawer = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(navDrawer);

        drawerToggle = setupDrawerToggle();
        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        mDrawer.addDrawerListener(drawerToggle);
        fragmentManager = getSupportFragmentManager();
        fragment = HomeFragment.newInstance("","");
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        instance = this;
        currentlyInflated = HomeFragment.class;

        if(!load()){

            setup();

        }

        drivers = new ArrayList<>();
        drivers.add(currentDriver);
        drivers.add(secondaryDriver);
        setCurrentDriver(currentDriver);
        HOSLogger.init(currentDriver);
        HOSLogger.startOnDuty();
        startLogging();

    }

    // Check if there is data to load
    private boolean load(){
        Data.init(getApplicationContext()); 
        drivers = Data.loadDrivers();
        settings = Data.loadSettings();
        if(settings == null){
            settings = new Settings();
        }
        if(drivers != null && drivers.size() >= 1){
            currentDriver = drivers.get(0);
            if(drivers.size() >= 2 && drivers.get(1) != null){
                secondaryDriver = drivers.get(1);
            }
            return true;
        }
        return false;
    }

    // Open the setup Activity
    private void setup(){
        Intent intent = new Intent(this, InitActivity.class);
        startActivity(intent);
    }

    //  Save all data that needs to be saved
    public static void save(){
        ArrayList<Driver> drivers = new ArrayList<Driver>();
        drivers.add(currentDriver);
        drivers.add(secondaryDriver);
        Data.saveDrivers(drivers);
    }

    // Choose fragment based on what item is clicked in drawer
    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_routes:
                fragmentClass = RoutesFragment.class;
                break;
            case R.id.nav_log:
                fragmentClass = LogViewerFragment.class;
                break;
            case R.id.nav_drivers:
                fragmentClass = DriversFragment.class;
                break;
            case R.id.nav_settings:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.nav_dvir:
                fragmentClass = DvirFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
                break;
        }
        try {
            currentlyInflated = fragmentClass;
            fragment = (Fragment) fragmentClass.newInstance();
            Log.d("DEBUGGING", fragmentClass.toString() + " Frag inflated");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private ActionBarDrawerToggle setupDrawerToggle() {

        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);

    }

    // Inflate the fragment
    public void switchToFragment(Class fragmentClass) {

        try {

            fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        } catch (Exception e) {

            Log.d("DEBUGGING", fragmentClass.toString() + " could not be inflated");

        }

    }
    public void switchToFragment(Class fragmentClass, String paramName, String param) {

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            if(fragmentClass == LogFragment.class){
                fragment = LogFragment.newInstance(param, "");
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString(paramName, param);
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        } catch (Exception e) {

            Log.d("DEBUGGING", fragmentClass.toString() + " could not be inflated");

        }

    }
    // Get the current fragment that is inflated
    public static String getFragment(){

        return currentlyInflated.getName();

    }

    // Check to see if perms are allowed, if not then request them
    public void checkPermission(String permission, int requestCode) {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                MainActivity.this,
                permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, requestCode);
        }

    }

    private boolean checkCameraPerms(){

        if (ContextCompat.checkSelfPermission(this.getBaseContext(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        100);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        100);
            }
            return false;
        } else {
            return true;
        }

    }

    public void setCurrentDriver(Driver driver){

        if (driver != null && !currentDriver.equals(driver)) {

            secondaryDriver = currentDriver;
            currentDriver = driver;
            drivers.set(0, currentDriver);
            drivers.set(1, secondaryDriver);
            currentDriver.setCurrentDriver(true);
            secondaryDriver.setCurrentDriver(false);
            HOSLogger.save(Status.ON_DUTY);
            Data.saveDrivers(drivers);
            HOSLogger.init(driver);

        }

    }

    //
    // Method that is runs periodically. Calls everything that needs to be called periodically
    //
    public static void tick(){
        updateTime();
        Log.d("DEBUGGING", "TICK---------------");
        if(fragment != null && fragment.getClass() == HomeFragment.class){
            ((HomeFragment) fragment).update();
        }
        if(BluetoothConnector.getStatus() == BlueToothStatus.AVAILABLE){
            Log.d("DEBUGGING", "Device address is " + settings.getDeviceAddress());
            if(settings !=  null && !settings.getDeviceAddress().isEmpty()) {
                Log.d("DEBUGGING", "Connecting to the device with address: " + settings.getDeviceAddress());
                connectBt();
            }

            if(gotDisconnected){

                gotDisconnected = false;
                // TODO: Disconnected event
                stoppedDriving();

            }
            HOSLogger.checkAlerts();
        }else if(BluetoothConnector.getStatus() == BlueToothStatus.CONNECTED){
            HOSLogger.checkAlerts();
            gotDisconnected = true;
            if(shouldSendBt){
                BluetoothConnector.sendRequests();
                //Log.d("DEBUGGING", BluetoothConnector.getFuelLevel());
            }

        }
        try {
            if(dayChanged()){
                Day today = new Day(DataConverter.removeTime(getTime()));
                currentDriver.getDays().add(today);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private static void connectBt() {
        Thread connector = new Thread(new Runnable() {
            @Override
            public void run() {
                BluetoothConnector.connect(settings.getDeviceAddress());
            }
        });
        connector.start();
    }

    public static void startLogging(){

        Log.d("DEBUGGING", "LOGGING");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tick();
                }
            }
        });
        thread.start();

    }

    public static Settings loadSettings(){

        settings = Data.loadSettings();
        return settings;

    }

    public static void saveSettings(Settings _settings){

        settings = _settings;
        Data.saveSettings(settings);

    }

    public static void sendBt(){

        shouldSendBt = true;

    }

    public static void dontSendBt(){

        shouldSendBt = false;

    }

    public static void stoppedDriving(){
        MainActivity.instance.checkCameraPerms();
        MainActivity.instance.switchToFragment(StoppedFragment.class);
    }

    @Override
    protected void onStop() {
        super.onStop();
        HOSLogger.save(currentDriver.getStatus());
        Data.saveDrivers(drivers);
    }

    // TODO: get time from api
    public static Date getTime(){
        return currentTime;
    }

    private static void updateTime(){
        currentTime = Calendar.getInstance().getTime();
    }
    public static boolean dayChanged() throws ParseException {
        if(currentDriver.getDays() == null || currentDriver.getDays().size() <= 0){
            return true;
        }else{
            // Get the last date logged
            Date date = currentDriver.getDays().get(currentDriver.getDays().size() - 1).getDate();
            if(DataConverter.sameDayNoTime(date, getTime())){
                return false;
            }else{
                return true;
            }
        }
    }


}