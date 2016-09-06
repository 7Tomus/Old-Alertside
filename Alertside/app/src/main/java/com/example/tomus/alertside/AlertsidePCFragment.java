package com.example.tomus.alertside;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class AlertsidePCFragment extends Fragment {

    final android.os.Handler handler = new android.os.Handler();
    boolean refreshing;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        refreshing = true;
        final View v = inflater.inflate(R.layout.layout_pc, parent, false);
        final Button emerald_button = (Button)v.findViewById(R.id.emerald);
        final Button connery_button = (Button)v.findViewById(R.id.connery);
        final Button miller_button = (Button)v.findViewById(R.id.miller);
        final Button cobalt_button = (Button)v.findViewById(R.id.cobalt);
        final Button briggs_button = (Button)v.findViewById(R.id.briggs);

        final ImageView emeraldCheckImage = (ImageView)v.findViewById(R.id.emeraldcheckimage);
        final ImageView conneryCheckImage = (ImageView)v.findViewById(R.id.connerycheckimage);
        final ImageView millerCheckImage = (ImageView)v.findViewById(R.id.millercheckimage);
        final ImageView cobaltCheckImage = (ImageView)v.findViewById(R.id.cobaltcheckimage);
        final ImageView briggsCheckImage = (ImageView)v.findViewById(R.id.briggscheckimage);
        final ImageView networkStatusText = (ImageView)v.findViewById(R.id.signaltext);

        //planetside font
        Typeface planetsideFont = Typeface.createFromAsset(getActivity().getAssets(), "planetside2.ttf");
        emerald_button.setTypeface(planetsideFont);
        connery_button.setTypeface(planetsideFont);
        miller_button.setTypeface(planetsideFont);
        cobalt_button.setTypeface(planetsideFont);
        briggs_button.setTypeface(planetsideFont);

        updateButtons(emerald_button, connery_button, miller_button, cobalt_button, briggs_button,networkStatusText);

        updateChecker(AlertsideMainBase.emeraldChecker, emeraldCheckImage);
        updateChecker(AlertsideMainBase.conneryChecker,conneryCheckImage);
        updateChecker(AlertsideMainBase.millerChecker,millerCheckImage);
        updateChecker(AlertsideMainBase.cobaltChecker,cobaltCheckImage);
        updateChecker(AlertsideMainBase.briggsChecker,briggsCheckImage);


        // listeners

        emerald_button.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AlertsideMainBase.emeraldChecker = !AlertsideMainBase.emeraldChecker;
                        if (AlertsideMainBase.emeraldChecker) {
                            emeraldCheckImage.setImageResource(R.mipmap.checkon);
                        } else {
                            emeraldCheckImage.setImageResource(R.mipmap.checkoff);
                        }
                        if (AlertsideMainBase.emeraldAlert) {
                            emerald_button.setBackgroundResource(R.mipmap.buttonalerthighlighted);
                        } else {
                            emerald_button.setBackgroundResource(R.mipmap.buttonnormalhighlighted);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (AlertsideMainBase.emeraldAlert) {
                            emerald_button.setBackgroundResource(R.mipmap.buttonalert);
                        } else {
                            emerald_button.setBackgroundResource(R.mipmap.buttonnormal);
                        }
                        break;
                }
                return false;
            }
        });

        connery_button.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AlertsideMainBase.conneryChecker = !AlertsideMainBase.conneryChecker;
                        if (AlertsideMainBase.conneryChecker) {
                            conneryCheckImage.setImageResource(R.mipmap.checkon);
                        } else {
                            conneryCheckImage.setImageResource(R.mipmap.checkoff);
                        }
                        if (AlertsideMainBase.conneryAlert) {
                            connery_button.setBackgroundResource(R.mipmap.buttonalerthighlighted);
                        } else {
                            connery_button.setBackgroundResource(R.mipmap.buttonnormalhighlighted);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (AlertsideMainBase.conneryAlert) {
                            connery_button.setBackgroundResource(R.mipmap.buttonalert);
                        } else {
                            connery_button.setBackgroundResource(R.mipmap.buttonnormal);
                        }
                        break;
                }
                return false;
            }
        });

        miller_button.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AlertsideMainBase.millerChecker = !AlertsideMainBase.millerChecker;
                        if (AlertsideMainBase.millerChecker) {
                            millerCheckImage.setImageResource(R.mipmap.checkon);
                        } else {
                            millerCheckImage.setImageResource(R.mipmap.checkoff);
                        }
                        if (AlertsideMainBase.millerAlert) {
                            miller_button.setBackgroundResource(R.mipmap.buttonalerthighlighted);
                        } else {
                            miller_button.setBackgroundResource(R.mipmap.buttonnormalhighlighted);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (AlertsideMainBase.millerAlert) {
                            miller_button.setBackgroundResource(R.mipmap.buttonalert);
                        } else {
                            miller_button.setBackgroundResource(R.mipmap.buttonnormal);
                        }
                        break;
                }
                return false;
            }
        });

        cobalt_button.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AlertsideMainBase.cobaltChecker = !AlertsideMainBase.cobaltChecker;
                        if (AlertsideMainBase.cobaltChecker) {
                            cobaltCheckImage.setImageResource(R.mipmap.checkon);
                        } else {
                            cobaltCheckImage.setImageResource(R.mipmap.checkoff);
                        }
                        if (AlertsideMainBase.cobaltAlert) {
                            cobalt_button.setBackgroundResource(R.mipmap.buttonalerthighlighted);
                        } else {
                            cobalt_button.setBackgroundResource(R.mipmap.buttonnormalhighlighted);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (AlertsideMainBase.cobaltAlert) {
                            cobalt_button.setBackgroundResource(R.mipmap.buttonalert);
                        } else {
                            cobalt_button.setBackgroundResource(R.mipmap.buttonnormal);
                        }
                        break;
                }
                return false;
            }
        });

        briggs_button.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AlertsideMainBase.briggsChecker = !AlertsideMainBase.briggsChecker;
                        if (AlertsideMainBase.briggsChecker) {
                            briggsCheckImage.setImageResource(R.mipmap.checkon);
                        } else {
                            briggsCheckImage.setImageResource(R.mipmap.checkoff);
                        }
                        if (AlertsideMainBase.briggsAlert) {
                            briggs_button.setBackgroundResource(R.mipmap.buttonalerthighlighted);
                        } else {
                            briggs_button.setBackgroundResource(R.mipmap.buttonnormalhighlighted);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (AlertsideMainBase.briggsAlert) {
                            briggs_button.setBackgroundResource(R.mipmap.buttonalert);
                        } else {
                            briggs_button.setBackgroundResource(R.mipmap.buttonnormal);
                        }
                        break;
                }
                return false;
            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(refreshing){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            updateButtons(emerald_button, connery_button, miller_button, cobalt_button, briggs_button,networkStatusText);
                            Log.v("PC","PC");
                        }
                    });
                    try{
                        Thread.sleep(30000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(runnable).start();
        return v;
    }

    public void updateChecker(Boolean checker, ImageView checkerImage){
        if(checker){
            checkerImage.setImageResource(R.mipmap.checkon);
        }
        else{
            checkerImage.setImageResource(R.mipmap.checkoff);
        }
    }

    public void updateButton(Button button, boolean status, String server, String map){
        if(status){
            button.setText(Html.fromHtml(server + "<br>" + map));
            button.setBackgroundResource(R.mipmap.buttonalert);
            button.setTextColor(0xffdf2a2d);
        }
        else{
            button.setText(Html.fromHtml(server));
            button.setBackgroundResource(R.mipmap.buttonnormal);
            button.setTextColor(0xff009bbe);
        }
    }

    public void updateButtons(Button first, Button second, Button third, Button fourth, Button fifth, ImageView image){
        updateButton(first,AlertsideMainBase.emeraldAlert,"Emerald",AlertsideMainBase.emeraldMap);
        updateButton(second,AlertsideMainBase.conneryAlert,"Connery",AlertsideMainBase.conneryMap);
        updateButton(third,AlertsideMainBase.millerAlert,"Miller",AlertsideMainBase.millerMap);
        updateButton(fourth,AlertsideMainBase.cobaltAlert,"Cobalt",AlertsideMainBase.cobaltMap);
        updateButton(fifth,AlertsideMainBase.briggsAlert,"Briggs", AlertsideMainBase.briggsMap);
        if(isNetworkAvailable()){
            image.setImageResource(R.mipmap.signalrecieving);
        }
        else{
            image.setImageResource(R.mipmap.signallost);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onPause(){
        super.onPause();
        refreshing = false;
    }

}
