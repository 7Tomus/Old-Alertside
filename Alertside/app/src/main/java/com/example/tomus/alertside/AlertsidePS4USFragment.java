package com.example.tomus.alertside;

import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
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



public class AlertsidePS4USFragment extends Fragment {

    final android.os.Handler handler = new android.os.Handler();
    boolean refreshing;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        refreshing = true;
        final View v = inflater.inflate(R.layout.layout_ps4us, parent, false);

        final Button genudine_button = (Button)v.findViewById(R.id.genudine);
        final Button palos_button = (Button)v.findViewById(R.id.palos);
        final Button crux_button = (Button)v.findViewById(R.id.crux);
        final Button xelas_button = (Button)v.findViewById(R.id.xelas);
        final Button searhus_button = (Button)v.findViewById(R.id.searhus);

        final ImageView genudineCheckImage = (ImageView)v.findViewById(R.id.genudinechecker);
        final ImageView palosCheckImage = (ImageView)v.findViewById(R.id.paloschecker);
        final ImageView cruxCheckImage = (ImageView)v.findViewById(R.id.cruxchecker);
        final ImageView xelasCheckImage = (ImageView)v.findViewById(R.id.xelaschecker);
        final ImageView searhusCheckImage = (ImageView)v.findViewById(R.id.searhuschecker);
        final ImageView networkStatusText = (ImageView)v.findViewById(R.id.signaltext);


        //planetside font
        Typeface planetsideFont = Typeface.createFromAsset(getActivity().getAssets(), "planetside2.ttf");
        genudine_button.setTypeface(planetsideFont);
        palos_button.setTypeface(planetsideFont);
        crux_button.setTypeface(planetsideFont);
        xelas_button.setTypeface(planetsideFont);
        searhus_button.setTypeface(planetsideFont);

        updateButton(genudine_button, AlertsideMainBase.genudineAlert, "Genudine", AlertsideMainBase.genudineMap);
        updateButton(palos_button, AlertsideMainBase.palosAlert,"Palos",AlertsideMainBase.palosMap);
        updateButton(crux_button, AlertsideMainBase.cruxAlert,"Crux",AlertsideMainBase.cruxMap);
        updateButton(xelas_button,AlertsideMainBase.xelasAlert,"Xelas",AlertsideMainBase.xelasMap);
        updateButton(searhus_button, AlertsideMainBase.searhusAlert,"Searhus",AlertsideMainBase.searhusMap);

        updateButtons(genudine_button, palos_button, crux_button, xelas_button, searhus_button, networkStatusText);

        updateChecker(AlertsideMainBase.genudineChecker, genudineCheckImage);
        updateChecker(AlertsideMainBase.palosChecker, palosCheckImage);
        updateChecker(AlertsideMainBase.cruxChecker, cruxCheckImage);
        updateChecker(AlertsideMainBase.xelasChecker, xelasCheckImage);
        updateChecker(AlertsideMainBase.searhusChecker, searhusCheckImage);

        // listeners

        genudine_button.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AlertsideMainBase.genudineChecker = !AlertsideMainBase.genudineChecker;
                        if (AlertsideMainBase.genudineChecker) {
                            genudineCheckImage.setImageResource(R.mipmap.checkon);
                        } else {
                            genudineCheckImage.setImageResource(R.mipmap.checkoff);
                        }
                        if (AlertsideMainBase.genudineAlert) {
                            genudine_button.setBackgroundResource(R.mipmap.buttonalerthighlighted);
                        } else {
                            genudine_button.setBackgroundResource(R.mipmap.buttonnormalhighlighted);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (AlertsideMainBase.genudineAlert) {
                            genudine_button.setBackgroundResource(R.mipmap.buttonalert);
                        } else {
                            genudine_button.setBackgroundResource(R.mipmap.buttonnormal);
                        }
                        break;
                }
                return false;
            }
        });

        palos_button.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AlertsideMainBase.palosChecker = !AlertsideMainBase.palosChecker;
                        if (AlertsideMainBase.palosChecker) {
                            palosCheckImage.setImageResource(R.mipmap.checkon);
                        } else {
                            palosCheckImage.setImageResource(R.mipmap.checkoff);
                        }
                        if (AlertsideMainBase.palosAlert) {
                            palos_button.setBackgroundResource(R.mipmap.buttonalerthighlighted);
                        } else {
                            palos_button.setBackgroundResource(R.mipmap.buttonnormalhighlighted);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (AlertsideMainBase.palosAlert) {
                            palos_button.setBackgroundResource(R.mipmap.buttonalert);
                        } else {
                            palos_button.setBackgroundResource(R.mipmap.buttonnormal);
                        }
                        break;
                }
                return false;
            }
        });

        crux_button.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AlertsideMainBase.cruxChecker = !AlertsideMainBase.cruxChecker;
                        if (AlertsideMainBase.cruxChecker) {
                            cruxCheckImage.setImageResource(R.mipmap.checkon);
                        } else {
                            cruxCheckImage.setImageResource(R.mipmap.checkoff);
                        }
                        if (AlertsideMainBase.cruxAlert) {
                            crux_button.setBackgroundResource(R.mipmap.buttonalerthighlighted);
                        } else {
                            crux_button.setBackgroundResource(R.mipmap.buttonnormalhighlighted);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (AlertsideMainBase.cruxAlert) {
                            crux_button.setBackgroundResource(R.mipmap.buttonalert);
                        } else {
                            crux_button.setBackgroundResource(R.mipmap.buttonnormal);
                        }
                        break;
                }
                return false;
            }
        });

        xelas_button.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AlertsideMainBase.xelasChecker = !AlertsideMainBase.xelasChecker;
                        if (AlertsideMainBase.xelasChecker) {
                            xelasCheckImage.setImageResource(R.mipmap.checkon);
                        } else {
                            xelasCheckImage.setImageResource(R.mipmap.checkoff);
                        }
                        if (AlertsideMainBase.xelasAlert) {
                            xelas_button.setBackgroundResource(R.mipmap.buttonalerthighlighted);
                        } else {
                            xelas_button.setBackgroundResource(R.mipmap.buttonnormalhighlighted);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (AlertsideMainBase.xelasAlert) {
                            xelas_button.setBackgroundResource(R.mipmap.buttonalert);
                        } else {
                            xelas_button.setBackgroundResource(R.mipmap.buttonnormal);
                        }
                        break;
                }
                return false;
            }
        });

        searhus_button.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AlertsideMainBase.searhusChecker = !AlertsideMainBase.searhusChecker;
                        if (AlertsideMainBase.searhusChecker) {
                            searhusCheckImage.setImageResource(R.mipmap.checkon);
                        } else {
                            searhusCheckImage.setImageResource(R.mipmap.checkoff);
                        }
                        if (AlertsideMainBase.searhusAlert) {
                            searhus_button.setBackgroundResource(R.mipmap.buttonalerthighlighted);
                        } else {
                            searhus_button.setBackgroundResource(R.mipmap.buttonnormalhighlighted);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (AlertsideMainBase.searhusAlert) {
                            searhus_button.setBackgroundResource(R.mipmap.buttonalert);
                        } else {
                            searhus_button.setBackgroundResource(R.mipmap.buttonnormal);
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
                            updateButtons(genudine_button, palos_button, crux_button, xelas_button, searhus_button, networkStatusText);
                            Log.v("PS4", "US");
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
        updateButton(first,AlertsideMainBase.genudineAlert,"Genudine",AlertsideMainBase.genudineMap);
        updateButton(second,AlertsideMainBase.palosAlert,"Palos",AlertsideMainBase.palosMap);
        updateButton(third,AlertsideMainBase.cruxAlert,"Crux",AlertsideMainBase.cruxMap);
        updateButton(fourth,AlertsideMainBase.xelasAlert,"Xelas",AlertsideMainBase.xelasMap);
        updateButton(fifth,AlertsideMainBase.searhusAlert,"Searhus", AlertsideMainBase.searhusMap);
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
