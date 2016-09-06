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



public class AlertsidePS4EUfragment extends Fragment {

    final android.os.Handler handler = new android.os.Handler();
    boolean refreshing;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        refreshing = true;
        final View v = inflater.inflate(R.layout.layout_ps4eu, parent, false);

        final Button rashnu_button = (Button)v.findViewById(R.id.rashnu);
        final Button ceres_button = (Button)v.findViewById(R.id.ceres);
        final Button lithcorp_button = (Button)v.findViewById(R.id.lithcorp);

        final ImageView rashnuCheckImage = (ImageView)v.findViewById(R.id.rashnuchecker);
        final ImageView ceresCheckImage = (ImageView)v.findViewById(R.id.cereschecker);
        final ImageView lithcorpCheckImage = (ImageView)v.findViewById(R.id.lithcorpchecker);
        final ImageView networkStatusText = (ImageView)v.findViewById(R.id.signaltext);


        //planetside font
        Typeface planetsideFont = Typeface.createFromAsset(getActivity().getAssets(), "planetside2.ttf");
        rashnu_button.setTypeface(planetsideFont);
        ceres_button.setTypeface(planetsideFont);
        lithcorp_button.setTypeface(planetsideFont);

        updateButton(rashnu_button, AlertsideMainBase.rashnuAlert,"Rashnu",AlertsideMainBase.rashnuMap);
        updateButton(ceres_button,AlertsideMainBase.ceresAlert,"Ceres",AlertsideMainBase.ceresMap);
        updateButton(lithcorp_button,AlertsideMainBase.lithcorpAlert,"Lithcorp",AlertsideMainBase.lithcorpMap);

        updateButtons(rashnu_button, ceres_button, lithcorp_button, networkStatusText);

        updateChecker(AlertsideMainBase.rashnuChecker, rashnuCheckImage);
        updateChecker(AlertsideMainBase.ceresChecker, ceresCheckImage);
        updateChecker(AlertsideMainBase.lithcorpChecker, lithcorpCheckImage);

        // listeners

        rashnu_button.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AlertsideMainBase.rashnuChecker = !AlertsideMainBase.rashnuChecker;
                        if (AlertsideMainBase.rashnuChecker) {
                            rashnuCheckImage.setImageResource(R.mipmap.checkon);
                        } else {
                            rashnuCheckImage.setImageResource(R.mipmap.checkoff);
                        }
                        if (AlertsideMainBase.rashnuAlert) {
                            rashnu_button.setBackgroundResource(R.mipmap.buttonalerthighlighted);
                        } else {
                            rashnu_button.setBackgroundResource(R.mipmap.buttonnormalhighlighted);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (AlertsideMainBase.rashnuAlert) {
                            rashnu_button.setBackgroundResource(R.mipmap.buttonalert);
                        } else {
                            rashnu_button.setBackgroundResource(R.mipmap.buttonnormal);
                        }
                        break;
                }
                return false;
            }
        });

        ceres_button.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AlertsideMainBase.ceresChecker = !AlertsideMainBase.ceresChecker;
                        if (AlertsideMainBase.ceresChecker) {
                            ceresCheckImage.setImageResource(R.mipmap.checkon);
                        } else {
                            ceresCheckImage.setImageResource(R.mipmap.checkoff);
                        }
                        if (AlertsideMainBase.ceresAlert) {
                            ceres_button.setBackgroundResource(R.mipmap.buttonalerthighlighted);
                        } else {
                            ceres_button.setBackgroundResource(R.mipmap.buttonnormalhighlighted);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (AlertsideMainBase.ceresAlert) {
                            ceres_button.setBackgroundResource(R.mipmap.buttonalert);
                        } else {
                            ceres_button.setBackgroundResource(R.mipmap.buttonnormal);
                        }
                        break;
                }
                return false;
            }
        });

        lithcorp_button.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AlertsideMainBase.lithcorpChecker = !AlertsideMainBase.lithcorpChecker;
                        if (AlertsideMainBase.lithcorpChecker) {
                            lithcorpCheckImage.setImageResource(R.mipmap.checkon);
                        } else {
                            lithcorpCheckImage.setImageResource(R.mipmap.checkoff);
                        }
                        if (AlertsideMainBase.lithcorpAlert) {
                            lithcorp_button.setBackgroundResource(R.mipmap.buttonalerthighlighted);
                        } else {
                            lithcorp_button.setBackgroundResource(R.mipmap.buttonnormalhighlighted);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (AlertsideMainBase.lithcorpAlert) {
                            lithcorp_button.setBackgroundResource(R.mipmap.buttonalert);
                        } else {
                            lithcorp_button.setBackgroundResource(R.mipmap.buttonnormal);
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
                            updateButtons(rashnu_button, ceres_button, lithcorp_button, networkStatusText);
                            Log.v("PS4", "EU");
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

    public void updateButtons(Button first, Button second, Button third, ImageView image){
        updateButton(first,AlertsideMainBase.rashnuAlert,"Rashnu",AlertsideMainBase.rashnuMap);
        updateButton(second,AlertsideMainBase.ceresAlert,"Ceres",AlertsideMainBase.ceresMap);
        updateButton(third,AlertsideMainBase.lithcorpAlert,"Lithcorp",AlertsideMainBase.lithcorpMap);
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
