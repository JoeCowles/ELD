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
import android.widget.Toast;

import com.carriergistics.eld.logging.Driver;
import com.carriergistics.eld.setup.SetupActivity;
import com.carriergistics.eld.ui.DriversFragment;
import com.carriergistics.eld.ui.HomeFragment;
import com.carriergistics.eld.ui.LogFragment;
import com.carriergistics.eld.ui.RoutesFragment;
import com.carriergistics.eld.ui.SettingsFragment;
import com.carriergistics.eld.bluetooth.BluetoothConnector;
import com.carriergistics.eld.utils.Data;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

//
public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private static Class currentlyInflated;
    public static FragmentManager fragmentManager;
    public static Driver currentDriver;
    public static Driver secondaryDriver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission(Manifest.permission.BLUETOOTH, 100);
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, 100);
        checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, 100);
        checkPermission(Manifest.permission.INTERNET, 100);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // This will display an Up icon (<-), we will replace it with hamburger later
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
        fragmentManager.beginTransaction().replace(R.id.flContent, new HomeFragment()).commit();
        if(!load()){
            setup();
            save();
        }
    }
    private boolean load(){
        Data.init(getApplicationContext());
        ArrayList<Driver> drivers = Data.loadDrivers();
        if(drivers != null){
            currentDriver = drivers.get(0);
            secondaryDriver = drivers.get(1);
            return true;
        }
        return false;
    }
    private void setup(){
        Intent intent = new Intent(this, SetupActivity.class);
        startActivity(intent);
    }
    public static void save(){
        ArrayList<Driver> drivers = new ArrayList<Driver>();
        drivers.add(currentDriver);
        drivers.add(secondaryDriver);
        Data.saveDrivers(drivers);
    }
    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_routes:
                fragmentClass = RoutesFragment.class;
                break;
            case R.id.nav_log:
                fragmentClass = LogFragment.class;
                break;
            case R.id.nav_drivers:
                fragmentClass = DriversFragment.class;
                break;
            case R.id.nav_settings:
                fragmentClass = SettingsFragment.class;
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
    public void switchToFragment(Class fragmentClass){
        try {
            Fragment frag = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, frag).commit();
        }catch (Exception e){
            Log.d("DEBUGGING", fragmentClass.toString() + " could not be inflated");
        }
    }
    public static void connect(String device){
        BluetoothConnector.connect(device);
    }
    public static String getFragment(){
        return currentlyInflated.getName();
    }
    public void checkPermission(String permission, int requestCode) {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                MainActivity.this,
                permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            MainActivity.this,
                            new String[]{permission},
                            requestCode);
        }
    }


    }