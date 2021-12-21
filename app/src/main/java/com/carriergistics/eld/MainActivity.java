package com.carriergistics.eld;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;

import com.carriergistics.eld.bluetooth.BlueToothStatus;
import com.carriergistics.eld.bluetooth.TelematicsData;
import com.carriergistics.eld.dvir.AddVehicleFragment;
import com.carriergistics.eld.dvir.CreateDvirFragment;
import com.carriergistics.eld.dvir.EditDvirFragment;
import com.carriergistics.eld.dvir.Vehicle;
import com.carriergistics.eld.dvir.VehicleFragment;
import com.carriergistics.eld.logging.Day;
import com.carriergistics.eld.logging.Driver;
import com.carriergistics.eld.logging.HOSLogger;
import com.carriergistics.eld.logging.Status;
import com.carriergistics.eld.mapping.MappingXMLParser;
import com.carriergistics.eld.mapping.load.Load;
import com.carriergistics.eld.mapping.loadEvent.LoadEvent;
import com.carriergistics.eld.setup.InitActivity;
import com.carriergistics.eld.ui.DriversFragment;
import com.carriergistics.eld.dvir.DvirFragment;
import com.carriergistics.eld.ui.HomeFragment;
import com.carriergistics.eld.editlog.LogFragment;
import com.carriergistics.eld.editlog.LogViewerFragment;
import com.carriergistics.eld.mapping.RoutesFragment;
import com.carriergistics.eld.settings.SettingsFragment;
import com.carriergistics.eld.bluetooth.BluetoothConnector;
import com.carriergistics.eld.ui.StoppedFragment;
import com.carriergistics.eld.utils.Data;
import com.carriergistics.eld.utils.DataConverter;
import com.carriergistics.eld.utils.Debugger;
import com.carriergistics.eld.utils.Settings;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/************************************************************
 Copyright 2020 Unboxed Industries
 Author - Joe Cowles
 ************************************************************/
public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    public static Toolbar toolbar;
    private NavigationView navDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private static Class currentlyInflated;
    public static FragmentManager fragmentManager;
    public static Driver currentDriver;
    public static Driver secondaryDriver;
    public static ArrayList<Driver> drivers;
    public static ArrayList<Vehicle> vehicles;
    public static Vehicle currentVehicle;
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
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setLogo(R.drawable.ic_dvir);
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
        fragment = HomeFragment.newInstance("", "");
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        instance = this;
        currentlyInflated = HomeFragment.class;

        if (!load()) {

            setup();
            currentDriver.setDays(new ArrayList<Day>());
            if (secondaryDriver != null) {
                secondaryDriver.setDays(new ArrayList<Day>());
            }
        }
        try {
            Debugger.init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        drivers = new ArrayList<>();
        drivers.add(currentDriver);
        drivers.add(secondaryDriver);
        setCurrentDriver(currentDriver);


        // Test code
        try {

            Load load = MappingXMLParser.deserialize(R.raw.test_route);
            Log.d("DEBUGGING", MappingXMLParser.serialize(load));



        } catch (Exception e) {
            e.printStackTrace();
        }


        // Setup notifications
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Carriergistics Notification", "Carriergistics Notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        startLogging();
    }

    // Check if there is data to load
    private boolean load() {
        Data.init(getApplicationContext());
        drivers = Data.loadDrivers();
        settings = Data.loadSettings();
        vehicles = Data.loadVehicles();

        if (settings == null) {
            settings = new Settings();
        }
        if (vehicles != null && vehicles.size() >= 1) {
            for (Vehicle v : vehicles) {
                if (v != null) {
                    currentVehicle = v;
                    break;
                }
            }
        }
        if (drivers != null && drivers.size() >= 1) {
            currentDriver = drivers.get(0);
            if (drivers.size() >= 2 && drivers.get(1) != null) {
                secondaryDriver = drivers.get(1);
            }
            return true;
        }

        return false;
    }

    // Open the setup Activity
    private void setup() {
        Intent intent = new Intent(this, InitActivity.class);
        startActivity(intent);
    }

    //  Save all data that needs to be saved
    public static void save() {
        Data.saveDrivers(drivers);
        Data.saveSettings(settings);
        Data.saveVehicles(vehicles);
    }

    // Choose fragment based on what item is clicked in drawer
    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Class fragmentClass;
        switch (menuItem.getItemId()) {
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

        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);

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
            if (fragmentClass == LogFragment.class) {
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

    public void switchToFragment(Class fragmentClass, String paramName, String param, String paramName2, String param2) {

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString(paramName, param);
            bundle.putString(paramName2, param2);
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        } catch (Exception e) {

            Log.d("DEBUGGING", fragmentClass.toString() + " could not be inflated");

        }

    }

    // Get the current fragment that is inflated
    public static String getFragment() {

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

    //TODO: Clean up perms
    private boolean checkCameraPerms() {

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

    public void setCurrentDriver(Driver driver) {

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
    public static void tick() {
        updateTime();
        Log.d("DEBUGGING", "TICK---------------");
        if (fragment != null && fragment.getClass() == HomeFragment.class) {
            if (currentDriver != null) {
                ((HomeFragment) fragment).update();
            }
        }
        if (BluetoothConnector.getStatus() == BlueToothStatus.AVAILABLE) {
            Log.d("DEBUGGING", "Device address is " + settings.getDeviceAddress());
            if (settings != null && !settings.getDeviceAddress().isEmpty()) {
                Log.d("DEBUGGING", "Connecting to the device with address: " + settings.getDeviceAddress());
                connectBt();
            }

            if (gotDisconnected) {
                gotDisconnected = false;
                // TODO: Disconnected event
                TelematicsData stopped = new TelematicsData();
                stopped.setRpm(0);
                stopped.setSpeed(0);
                Message msg = Message.obtain();
                msg.obj = stopped;
                HOSLogger.handler.sendMessage(msg);

            }
            HOSLogger.checkAlerts();
        } else if (BluetoothConnector.getStatus() == BlueToothStatus.CONNECTED) {
            HOSLogger.checkAlerts();
            gotDisconnected = true;
            if (shouldSendBt) {
                BluetoothConnector.sendRequests();
                //Log.d("DEBUGGING", BluetoothConnector.getFuelLevel());
            }

        }
        try {
            if (dayChanged()) {
                Day today = new Day(DataConverter.removeTime(getTime()));
                currentDriver.getDays().add(today);
                save();
                // Remove any days that are 17 days old
                for (Driver driver : drivers) {
                    if (driver == null) {
                        break;
                    }
                    for (int index = driver.getDays().size() - 1; index >= 0; index--) {
                        Day d = driver.getDays().get(index);
                        // If the day is 17 days old, remove it, there is no need to save it
                        if (!d.getDate().after(DataConverter.addHoursToDate(getTime(), (-24 * 17)))) {
                            driver.getDays().remove(d);
                        }
                    }

                }
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

    public static void startLogging() {

        HOSLogger.init(currentDriver);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
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

    public static Settings loadSettings() {

        settings = Data.loadSettings();
        return settings;

    }

    public static void saveSettings(Settings _settings) {

        settings = _settings;
        Data.saveSettings(settings);

    }

    public static void sendBt() {

        shouldSendBt = true;

    }

    public static void dontSendBt() {

        shouldSendBt = false;

    }

    public static void stoppedDriving() {
        MainActivity.instance.checkCameraPerms();
        MainActivity.instance.switchToFragment(StoppedFragment.class);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (currentDriver.getStatus() != null) {
            notifyUser("Current driver is " + currentDriver.getStatus().toString());
        }
        HOSLogger.save(currentDriver.getStatus());
        save();
        try {
            Debugger.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: get time from api
    public static Date getTime() {
        return currentTime;
    }

    private static void updateTime() {
        currentTime = Calendar.getInstance().getTime();
    }

    public static boolean dayChanged() throws ParseException {
        if (currentDriver.getDays() == null || currentDriver.getDays().size() <= 0) {
            return true;
        } else {
            // Get the last date logged
            Date date = currentDriver.getDays().get(currentDriver.getDays().size() - 1).getDate();
            if (DataConverter.sameDayNoTime(date, getTime())) {
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Class switchTo = null;
        if (fragment.getClass() == VehicleFragment.class) {
            switchTo = DvirFragment.class;
        } else if (fragment.getClass() == AddVehicleFragment.class) {
            switchTo = DvirFragment.class;
        } else if (fragment.getClass() == LogFragment.class) {
            switchTo = LogViewerFragment.class;
        } else if (fragment.getClass() == CreateDvirFragment.class) {
            switchTo = VehicleFragment.class;
        } else if (fragment.getClass() == EditDvirFragment.class) {
            switchTo = VehicleFragment.class;
        } else {
            switchTo = HomeFragment.class;
        }
        switchToFragment(switchTo);
    }

    private void notifyUser(String msg) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Carriergistics Notification")
                .setSmallIcon(R.mipmap.logosmall)
                .setContentTitle("Carriergistics")
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
        managerCompat.notify(1, builder.build());
    }

    public static Vehicle getVehicleFromVin(String vin) {
        for (Vehicle v : vehicles) {
            if (v.getVin().equalsIgnoreCase(vin)) {
                return v;
            }
        }
        return null;
    }

    public Location getLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return location;
    }
}