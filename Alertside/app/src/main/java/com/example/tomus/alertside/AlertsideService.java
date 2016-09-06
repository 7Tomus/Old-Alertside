package com.example.tomus.alertside;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.net.URL;

public class AlertsideService extends Service {

    NotificationManager notificationManager;

    public static boolean emeraldAlert,conneryAlert,millerAlert,cobaltAlert,briggsAlert;
    private static boolean emeraldChecker, conneryChecker, millerChecker, cobaltChecker, briggsChecker;
    public static String emeraldMap, conneryMap, millerMap, cobaltMap, briggsMap;
    private int emeraldInstanceID, conneryInstanceID, millerInstanceID, cobaltInstanceID, briggsInstanceID;

    public static boolean rashnuAlert,ceresAlert,lithcorpAlert;
    private static boolean rashnuChecker, ceresChecker, lithcorpChecker;
    public static String rashnuMap, ceresMap, lithcorpMap;
    private int rashnuInstanceID, ceresInstanceID, lithcorpInstanceID;

    public static boolean genudineAlert,palosAlert,cruxAlert,xelasAlert,searhusAlert;
    private static boolean genudineChecker, palosChecker, cruxChecker, xelasChecker, searhusChecker;
    public static String genudineMap, palosMap, cruxMap, xelasMap, searhusMap;
    private int genudineInstanceID, palosInstanceID, cruxInstanceID, xelasInstanceID, searhusInstanceID;

    private static final String TAG = "AlertsideService";
    private boolean isRunning, oneRunningAlready;
    private String pcUrl = "http://census.daybreakgames.com/s:alertside/json/get/ps2:v2/world_event?type=METAGAME";
    private String ps4EuUrl = "http://census.daybreakgames.com/s:alertside/json/get/ps2ps4eu:v2/world_event?type=METAGAME";
    private String ps4UsUrl = "http://census.daybreakgames.com/s:alertside/json/get/ps2ps4us:v2/world_event?type=METAGAME";


    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        isRunning = true;
        updateCheckers();
        fillInstanceIdJSON(pcUrl);
        fillInstanceIdJSON(ps4EuUrl);
        fillInstanceIdJSON(ps4UsUrl);
        checkAlert(pcUrl);
        checkAlert(ps4UsUrl);
        checkAlert(ps4EuUrl);
        activityNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service onStartCommand");
        if(!oneRunningAlready) {
            oneRunningAlready = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (isRunning) {
                            Log.v(TAG, "service running");
                            Thread.sleep(30000);
                            updateCheckers();
                            checkAlert(pcUrl);
                            Thread.sleep(500);
                            checkAlert(ps4UsUrl);
                            Thread.sleep(500);
                            checkAlert(ps4EuUrl);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    stopSelf();
                }
            }).start();
        }
            return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        Log.i(TAG, "Service onDestroy");
        notificationManager.cancelAll();

    }

    public void updateCheckers(){
        emeraldChecker = AlertsideMainBase.emeraldChecker;      conneryChecker = AlertsideMainBase.conneryChecker;
        millerChecker = AlertsideMainBase.millerChecker;        cobaltChecker = AlertsideMainBase.cobaltChecker;
        briggsChecker = AlertsideMainBase.briggsChecker;        rashnuChecker = AlertsideMainBase.rashnuChecker;
        ceresChecker = AlertsideMainBase.ceresChecker;          lithcorpChecker = AlertsideMainBase.lithcorpChecker;
        genudineChecker = AlertsideMainBase.genudineChecker;    palosChecker = AlertsideMainBase.palosChecker;
        cruxChecker = AlertsideMainBase.cruxChecker;            xelasChecker = AlertsideMainBase.xelasChecker;
        searhusChecker = AlertsideMainBase.searhusChecker;
    }

    public void fillInstanceIdJSON(String url){
        JSONArray jsonArray;
        if(isNetworkAvailable()) {
            JSONObject dataZNetu = requestWebService(url);
            if(dataZNetu != null) {
                try {
                    jsonArray = dataZNetu.getJSONArray("world_event_list");
                    for (int i = jsonArray.length() - 1; i >= 0; i--) {
                        JSONObject tempObj = jsonArray.getJSONObject(i);
                        int worldId = tempObj.getInt("world_id");
                        int instanceId = tempObj.getInt("instance_id");
                        Log.v("" + worldId,"" + instanceId);
                        fillInstanceId(worldId, instanceId);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    //TODO switch namiesto ifov, a pole[2] instance ID

    public void fillInstanceId(int worldId, int instanceId){
        if (worldId == 25) {briggsInstanceID = instanceId;}
        if (worldId == 1) {conneryInstanceID = instanceId;}
        if (worldId == 10) {millerInstanceID = instanceId;}
        if (worldId == 13) {cobaltInstanceID = instanceId;}
        if (worldId == 17) {emeraldInstanceID = instanceId;}

        if (worldId == 2000) {ceresInstanceID = instanceId;}
        if (worldId == 2001) {lithcorpInstanceID = instanceId;}
        if (worldId == 2002) {rashnuInstanceID = instanceId;}

        if (worldId == 1004) {xelasInstanceID = instanceId;}
        if (worldId == 1001) {palosInstanceID = instanceId;}
        if (worldId == 1002) {cruxInstanceID = instanceId;}
        if (worldId == 1000) {genudineInstanceID = instanceId;}
        if (worldId == 1003) {searhusInstanceID = instanceId;}
    }

    public void checkAlert(String url){
        JSONArray jsonArray;
        if(isNetworkAvailable()) {
            JSONObject dataZNetu = requestWebService(url);
            if(dataZNetu != null) {
                try {
                    jsonArray = dataZNetu.getJSONArray("world_event_list");
                    for (int i = jsonArray.length() - 1; i >= 0; i--) {
                        JSONObject tempObj = jsonArray.getJSONObject(i);
                        int worldId = tempObj.getInt("world_id");
                        int stateID = tempObj.getInt("metagame_event_state");
                        int eventType = tempObj.getInt("metagame_event_id");
                        int instanceId = tempObj.getInt("instance_id");
                        updateStatus(worldId, stateID, eventType, instanceId);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void activityNotification(){
        Context context = getApplicationContext();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.icon);
        Intent intent = new Intent(context, AlertsideMainBase.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        //intent.setAction(Intent.ACTION_MAIN);
        //intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle("Alertside");
        builder.setContentText("Checking new alerts");
        builder.setOngoing(true);
        Notification notification = builder.build();
        notificationManager.notify(0,notification);
    }
/*
    Notification n  = new Notification.Builder(this);
            .setContentTitle(map + "Alert !");
            .setContentText(server);
            .setAutoCancel(true);
            .build();

    NotificationManager notificationManager =
            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    notificationManager.notify(0, n);
*/
    public void notif(int serverID, String server, String map){
        Context context = getApplicationContext();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(map + "Alert !");
        builder.setContentText(server);
        builder.setLights(0xffffff00, 1500, 750);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.icon);
        Notification notification = builder.build();
        notificationManager.notify(serverID,notification);
    }

    public void updateStatus(int worldId, int stateID, int eventType, int instanceId){

        if (worldId == 25) { //briggs
            if (stateID == 135 && eventType == 1) {
                briggsAlert = true; briggsMap = "Indar";
            }
            if (stateID == 135 && eventType == 2) {
                briggsAlert = true; briggsMap = "Esamir";
            }
            if (stateID == 135 && eventType == 3) {
                briggsAlert = true; briggsMap = "Amerish";
            }
            if (stateID == 135 && eventType == 4) {
                briggsAlert = true; briggsMap = "Hossin";
            }
            if (stateID == 135 && (eventType == 51 || eventType == 52 || eventType == 53 || eventType == 54)) {
                briggsAlert = true; briggsMap = "Special";
            }
            if(stateID == 135 && briggsChecker && instanceId != briggsInstanceID){
                notif(worldId,"Briggs",briggsMap);briggsInstanceID = instanceId;
            }
            if (stateID == 138 || stateID == 137) {
                briggsAlert = false;
            }
        }
        if (worldId == 1) { //connery
            if (stateID == 135 && eventType == 1) {
                conneryAlert = true; conneryMap = "Indar";
            }
            if (stateID == 135 && eventType == 2) {
                conneryAlert = true; conneryMap = "Esamir";
            }
            if (stateID == 135 && eventType == 3) {
                conneryAlert = true; conneryMap = "Amerish";
            }
            if (stateID == 135 && eventType == 4) {
                conneryAlert = true; conneryMap = "Hossin";
            }
            if (stateID == 135 && (eventType == 51 || eventType == 52 || eventType == 53 || eventType == 54)) {
                conneryAlert = true; conneryMap = "Special";
            }
            if(stateID == 135 && conneryChecker && instanceId != conneryInstanceID){
                Log.v("Connery",conneryInstanceID + " " + instanceId);notif(worldId, "Connery", conneryMap);conneryInstanceID = instanceId;
            }
            if (stateID == 138 || stateID == 137) {
                conneryAlert = false;
            }
        }
        if (worldId == 10) { //miller
            if (stateID == 135 && eventType == 1) {
                millerAlert = true; millerMap = "Indar";
            }
            if (stateID == 135 && eventType == 2) {
                millerAlert = true; millerMap = "Esamir";
            }
            if (stateID == 135 && eventType == 3) {
                millerAlert = true; millerMap = "Amerish";
            }
            if (stateID == 135 && eventType == 4) {
                millerAlert = true; millerMap = "Hossin";
            }
            if (stateID == 135 && (eventType == 51 || eventType == 52 || eventType == 53 || eventType == 54)) {
                millerAlert = true; millerMap = "Special";
            }
            if(stateID == 135 && millerChecker && instanceId != millerInstanceID){
                notif(worldId,"Miller",millerMap);millerInstanceID = instanceId;
            }
            if (stateID == 138 || stateID == 137) {
                millerAlert = false;
            }
        }
        if (worldId == 13) { //cobalt
            if (stateID == 135 && eventType == 1) {
                cobaltAlert = true; cobaltMap = "Indar";
            }
            if (stateID == 135 && eventType == 2) {
                cobaltAlert = true; cobaltMap = "Esamir";
            }
            if (stateID == 135 && eventType == 3) {
                cobaltAlert = true; cobaltMap = "Amerish";
            }
            if (stateID == 135 && eventType == 4) {
                cobaltAlert = true; cobaltMap = "Hossin";
            }
            if (stateID == 135 && (eventType == 51 || eventType == 52 || eventType == 53 || eventType == 54)) {
                cobaltAlert = true; cobaltMap = "Special";
            }
            if(stateID == 135 && cobaltChecker && instanceId != cobaltInstanceID){
                notif(worldId,"Cobalt",cobaltMap); cobaltInstanceID= instanceId;
            }
            if (stateID == 138 || stateID == 137) {
                cobaltAlert = false;
            }
        }
        if (worldId == 17) { //emerald
            if (stateID == 135 && eventType == 1) {
                emeraldAlert = true; emeraldMap = "Indar";
            }
            if (stateID == 135 && eventType == 2) {
                emeraldAlert = true; emeraldMap = "Esamir";
            }
            if (stateID == 135 && eventType == 3) {
                emeraldAlert = true; emeraldMap = "Amerish";
            }
            if (stateID == 135 && eventType == 4) {
                emeraldAlert = true; emeraldMap = "Hossin";
            }
            if (stateID == 135 && (eventType == 51 || eventType == 52 || eventType == 53 || eventType == 54)) {
                emeraldAlert = true; emeraldMap = "Special";
            }
            if(stateID == 135 && emeraldChecker && instanceId != emeraldInstanceID){
                notif(worldId,"Emerald",emeraldMap);emeraldInstanceID = instanceId;
            }
            if (stateID == 138 || stateID == 137) {
                emeraldAlert = false;
            }
        }
        if (worldId == 2000) { //ceres
            if (stateID == 135 && eventType == 1) {
                ceresAlert = true; ceresMap = "Indar";
            }
            if (stateID == 135 && eventType == 2) {
                ceresAlert = true; ceresMap = "Esamir";
            }
            if (stateID == 135 && eventType == 3) {
                ceresAlert = true; ceresMap = "Amerish";
            }
            if (stateID == 135 && eventType == 4) {
                ceresAlert = true; ceresMap = "Hossin";
            }
            if (stateID == 135 && (eventType == 51 || eventType == 52 || eventType == 53 || eventType == 54)) {
                ceresAlert = true; ceresMap = "Special";
            }
            if(stateID == 135 && ceresChecker && instanceId != ceresInstanceID){
                Log.v("Ceres",ceresInstanceID + " " + instanceId);notif(worldId,"Ceres",ceresMap);ceresInstanceID = instanceId;
            }
            if (stateID == 138 || stateID == 137) {
                ceresAlert = false;
            }
        }
        if (worldId == 2001) { //lithcorp
            if (stateID == 135 && eventType == 1) {
                lithcorpAlert = true; lithcorpMap = "Indar";
            }
            if (stateID == 135 && eventType == 2) {
                lithcorpAlert = true; lithcorpMap = "Esamir";
            }
            if (stateID == 135 && eventType == 3) {
                lithcorpAlert = true; lithcorpMap = "Amerish";
            }
            if (stateID == 135 && eventType == 4) {
                lithcorpAlert = true; lithcorpMap = "Hossin";
            }
            if (stateID == 135 && (eventType == 51 || eventType == 52 || eventType == 53 || eventType == 54)) {
                lithcorpAlert = true; lithcorpMap = "Special";
            }
            if(stateID == 135 && lithcorpChecker && instanceId != lithcorpInstanceID){
                notif(worldId,"Lithcorp",lithcorpMap);lithcorpInstanceID = instanceId;
            }
            if (stateID == 138 || stateID == 137) {
                lithcorpAlert = false;
            }
        }
        if (worldId == 2002) { //rashnu
            if (stateID == 135 && eventType == 1) {
                rashnuAlert = true; rashnuMap = "Indar";
            }
            if (stateID == 135 && eventType == 2) {
                rashnuAlert = true; rashnuMap = "Esamir";
            }
            if (stateID == 135 && eventType == 3) {
                rashnuAlert = true; rashnuMap = "Amerish";
            }
            if (stateID == 135 && eventType == 4) {
                rashnuAlert = true; rashnuMap = "Hossin";
            }
            if (stateID == 135 && (eventType == 51 || eventType == 52 || eventType == 53 || eventType == 54)) {
                rashnuAlert = true; rashnuMap = "Special";
            }
            if(stateID == 135 && rashnuChecker && instanceId != rashnuInstanceID){
                Log.v("Rashnu",rashnuInstanceID + " " + instanceId);notif(worldId,"Rashnu",rashnuMap);rashnuInstanceID = instanceId;
            }
            if (stateID == 138 || stateID == 137) {
                rashnuAlert = false;
            }
        }
        if (worldId == 1004) { //xelas
            if (stateID == 135 && eventType == 1) {
                xelasAlert = true; xelasMap = "Indar";
            }
            if (stateID == 135 && eventType == 2) {
                xelasAlert = true; xelasMap = "Esamir";
            }
            if (stateID == 135 && eventType == 3) {
                xelasAlert = true; xelasMap = "Amerish";
            }
            if (stateID == 135 && eventType == 4) {
                xelasAlert = true; xelasMap = "Hossin";
            }
            if (stateID == 135 && (eventType == 51 || eventType == 52 || eventType == 53 || eventType == 54)) {
                xelasAlert = true; xelasMap = "Special";
            }
            if(stateID == 135 && xelasChecker && instanceId != xelasInstanceID){
                notif(worldId,"Xelas",xelasMap);xelasInstanceID = instanceId;
            }
            if (stateID == 138 || stateID == 137) {
                xelasAlert = false;
            }
        }
        if (worldId == 1001) { //palos
            if (stateID == 135 && eventType == 1) {
                palosAlert = true; palosMap = "Indar";
            }
            if (stateID == 135 && eventType == 2) {
                palosAlert = true; palosMap = "Esamir";
            }
            if (stateID == 135 && eventType == 3) {
                palosAlert = true; palosMap = "Amerish";
            }
            if (stateID == 135 && eventType == 4) {
                palosAlert = true; palosMap = "Hossin";
            }
            if (stateID == 135 && (eventType == 51 || eventType == 52 || eventType == 53 || eventType == 54)) {
                palosAlert = true; palosMap = "Special";
            }
            if(stateID == 135 && palosChecker && instanceId != palosInstanceID){
                notif(worldId,"Palos",palosMap);palosInstanceID = instanceId;
            }
            if (stateID == 138 || stateID == 137) {
                palosAlert = false;
            }
        }
        if (worldId == 1002) { //crux
            if (stateID == 135 && eventType == 1) {
                cruxAlert = true; cruxMap = "Indar";
            }
            if (stateID == 135 && eventType == 2) {
                cruxAlert = true; cruxMap = "Esamir";
            }
            if (stateID == 135 && eventType == 3) {
                cruxAlert = true; cruxMap = "Amerish";
            }
            if (stateID == 135 && eventType == 4) {
                cruxAlert = true; cruxMap = "Hossin";
            }
            if (stateID == 135 && (eventType == 51 || eventType == 52 || eventType == 53 || eventType == 54)) {
                cruxAlert = true; cruxMap = "Special";
            }
            if(stateID == 135 && cruxChecker && instanceId != cruxInstanceID){
                notif(worldId,"Crux",cruxMap);cruxInstanceID = instanceId;
            }
            if (stateID == 138 || stateID == 137) {
                cruxAlert = false;
            }
        }
        if (worldId == 1000) { //genudine
            if (stateID == 135 && eventType == 1) {
                genudineAlert = true; genudineMap = "Indar";
            }
            if (stateID == 135 && eventType == 2) {
                genudineAlert = true; genudineMap = "Esamir";
            }
            if (stateID == 135 && eventType == 3) {
                genudineAlert = true; genudineMap = "Amerish";
            }
            if (stateID == 135 && eventType == 4) {
                genudineAlert = true; genudineMap = "Hossin";
            }
            if (stateID == 135 && (eventType == 51 || eventType == 52 || eventType == 53 || eventType == 54)) {
                genudineAlert = true; genudineMap = "Special";
            }
            if(stateID == 135 && genudineChecker && instanceId != genudineInstanceID){
                notif(worldId,"Genudine",genudineMap);genudineInstanceID = instanceId;
            }
            if (stateID == 138 || stateID == 137) {
                genudineAlert = false;
            }
        }
        if (worldId == 1003) { //searhus
            if (stateID == 135 && eventType == 1) {
                searhusAlert = true; searhusMap = "Indar";
            }
            if (stateID == 135 && eventType == 2) {
                searhusAlert = true; searhusMap = "Esamir";
            }
            if (stateID == 135 && eventType == 3) {
                searhusAlert = true; searhusMap = "Amerish";
            }
            if (stateID == 135 && eventType == 4) {
                searhusAlert = true; searhusMap = "Hossin";
            }
            if (stateID == 135 && (eventType == 51 || eventType == 52 || eventType == 53 || eventType == 54)) {
                searhusAlert = true; searhusMap = "Special";
            }
            if(stateID == 135 && searhusChecker && instanceId != searhusInstanceID){
                notif(worldId,"Searhus",searhusMap);searhusInstanceID = instanceId;
            }
            if (stateID == 138 || stateID == 137) {
                searhusAlert = false;
            }
        }
    }

    public static JSONObject requestWebService(String serviceUrl) {
        disableConnectionReuseIfNecessary();
        HttpURLConnection urlConnection = null;
        try {
            // create connection
            URL urlToRequest = new URL(serviceUrl);
            urlConnection = (HttpURLConnection)urlToRequest.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);

            // handle issues
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                Log.v("requestWebService","HTTP_UNAUTHORIZED");
                // handle unauthorized (if service requires user login)
            } else if (statusCode != HttpURLConnection.HTTP_OK) {
                Log.v("requestWebService","404");
                // handle any other errors, like 404, 500,..
            }

            // create JSON object from content
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Log.v("requestWebService","JSON obtained");
            return new JSONObject(getResponseText(in));

        } catch (MalformedURLException e) {
            Log.v("requestWebService","URL is invalid");
        } catch (SocketTimeoutException e) {

            Log.v("requestWebService","data retrieval or connection timed out");
        } catch (IOException e) {
            Log.v("requestWebService","could not read response body");
        } catch (JSONException e) {
            Log.v("requestWebService","response body is no valid JSON string");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }
    private static void disableConnectionReuseIfNecessary() {
        if (Integer.parseInt(Build.VERSION.SDK)
                < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }
    private static String getResponseText(InputStream inStream) {
        return new Scanner(inStream).useDelimiter("\\A").next();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
