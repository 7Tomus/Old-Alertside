package com.example.tomus.alertside;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class AlertsideMainBase extends ActionBarActivity{
    private FragmentManager fragmentManager;
    public static boolean refreshing;
    final android.os.Handler handler = new android.os.Handler();
    private static int maxThreads = 1;

    public static boolean emeraldAlert,conneryAlert,millerAlert,cobaltAlert,briggsAlert;
    public static boolean emeraldChecker, conneryChecker, millerChecker, cobaltChecker, briggsChecker;

    public static boolean rashnuAlert,ceresAlert,lithcorpAlert;
    public static boolean rashnuChecker, ceresChecker, lithcorpChecker;

    public static boolean genudineAlert,palosAlert,cruxAlert,xelasAlert,searhusAlert;
    public static boolean genudineChecker, palosChecker, cruxChecker, xelasChecker, searhusChecker;

    public static String emeraldMap, conneryMap, millerMap, cobaltMap, briggsMap;
    public static String rashnuMap, ceresMap, lithcorpMap;
    public static String genudineMap, palosMap, cruxMap, xelasMap, searhusMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        //multiple instances prevention
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent.ACTION_MAIN)) {
                Log.w("Alertside", "Main Activity is not the root.  Finishing Main Activity instead of launching.");
                finish();
                return;
            }
        }
*/
        setContentView(R.layout.layout_mainbase);

        Intent intent = new Intent(AlertsideMainBase.this, AlertsideService.class);
        startService(intent);
        updateAlerts();
        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_fragment_container);
        if (fragment == null) {
            fragment = new AlertsidePCFragment();
            fragmentManager.beginTransaction().add(R.id.main_fragment_container, fragment).commit();

        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        Thread.sleep(1500);
                        updateAlerts();
                        Log.v("FromBase", "FirstUpdate");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alertside, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("emeraldChecker", emeraldChecker);
        savedInstanceState.putBoolean("conneryChecker",conneryChecker);
        savedInstanceState.putBoolean("millerChecker",millerChecker);
        savedInstanceState.putBoolean("cobaltChecker",cobaltChecker);
        savedInstanceState.putBoolean("briggsChecker",briggsChecker);

        savedInstanceState.putBoolean("rashnudChecker",rashnuChecker);
        savedInstanceState.putBoolean("ceresChecker",ceresChecker);
        savedInstanceState.putBoolean("lithcorpChecker",lithcorpChecker);

        savedInstanceState.putBoolean("genudineChecker",genudineChecker);
        savedInstanceState.putBoolean("palosChecker",palosChecker);
        savedInstanceState.putBoolean("cruxChecker",cruxChecker);
        savedInstanceState.putBoolean("xelasChecker",xelasChecker);
        savedInstanceState.putBoolean("searhusChecker",searhusChecker);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        emeraldChecker = savedInstanceState.getBoolean("emeraldChecker");
        conneryChecker = savedInstanceState.getBoolean("conneryChecker");
        millerChecker = savedInstanceState.getBoolean("millerChecker");
        cobaltChecker = savedInstanceState.getBoolean("cobaltChecker");
        briggsChecker = savedInstanceState.getBoolean("briggsChecker");

        rashnuChecker = savedInstanceState.getBoolean("rashnuChecker");
        ceresChecker = savedInstanceState.getBoolean("ceresChecker");
        lithcorpChecker = savedInstanceState.getBoolean("lithcorpChecker");

        genudineChecker = savedInstanceState.getBoolean("genudineChecker");
        palosChecker = savedInstanceState.getBoolean("palosChecker");
        cruxChecker = savedInstanceState.getBoolean("cruxChecker");
        xelasChecker = savedInstanceState.getBoolean("xelasChecker");
        searhusChecker = savedInstanceState.getBoolean("searhusChecker");
    }

    @Override
    public void onResume(){
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        Thread.sleep(3000);
                        updateAlerts();
                        Log.v("FromBase", "first Update");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        refreshing = true;
        if(maxThreads == 1) {
            maxThreads = maxThreads - 1;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        while (refreshing) {
                            Thread.sleep(30000);
                            updateAlerts();
                            Log.v("FromBase", "OK Updating");
                            maxThreads = 1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


    @Override
    public void onPause(){
        super.onPause();
        refreshing = false;
        Log.v("main base", "paused");
    }

    @Override
    public void onStop(){
        super.onStop();
        refreshing = false;
        Log.v("main base","stopped");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        refreshing = false;
        Log.v("main base","stopped refreshing");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.pcmenu) {
            updateAlerts();
            Fragment fragment = new AlertsidePCFragment();
            fragmentManager.beginTransaction().replace(R.id.main_fragment_container,fragment).commit();
            return true;
        }

        if(id == R.id.ps4eumenu){
            updateAlerts();
            Fragment fragment = new AlertsidePS4EUfragment();
            fragmentManager.beginTransaction().replace(R.id.main_fragment_container,fragment).commit();
            return true;
        }

        if(id == R.id.ps4usmenu){
            updateAlerts();
            Fragment fragment = new AlertsidePS4USFragment();
            fragmentManager.beginTransaction().replace(R.id.main_fragment_container,fragment).commit();
            return true;
        }

        if(id == R.id.donate){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=RB7FYXQDZLKM4"));
            startActivity(browserIntent);
            finish();
        }

        if(id == R.id.exit){
            Intent intent = new Intent(AlertsideMainBase.this, AlertsideService.class);
            stopService(intent);
            refreshing = false;
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateAlerts(){
        emeraldAlert = AlertsideService.emeraldAlert; emeraldMap = AlertsideService.emeraldMap;
        conneryAlert = AlertsideService.conneryAlert; conneryMap = AlertsideService.conneryMap;
        millerAlert = AlertsideService.millerAlert; millerMap = AlertsideService.millerMap;
        cobaltAlert = AlertsideService.cobaltAlert; cobaltMap = AlertsideService.cobaltMap;
        briggsAlert = AlertsideService.briggsAlert; briggsMap = AlertsideService.briggsMap;

        rashnuAlert = AlertsideService.rashnuAlert; rashnuMap = AlertsideService.rashnuMap;
        ceresAlert = AlertsideService.ceresAlert; ceresMap = AlertsideService.ceresMap;
        lithcorpAlert = AlertsideService.lithcorpAlert; lithcorpMap = AlertsideService.lithcorpMap;

        genudineAlert = AlertsideService.genudineAlert; genudineMap = AlertsideService.genudineMap;
        palosAlert = AlertsideService.palosAlert; palosMap = AlertsideService.palosMap;
        cruxAlert = AlertsideService.cruxAlert; cruxMap = AlertsideService.cruxMap;
        xelasAlert = AlertsideService.xelasAlert; xelasMap = AlertsideService.xelasMap;
        searhusAlert = AlertsideService.searhusAlert; searhusMap = AlertsideService.searhusMap;
    }
}
