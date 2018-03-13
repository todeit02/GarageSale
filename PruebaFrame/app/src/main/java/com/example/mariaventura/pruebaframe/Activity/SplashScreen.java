package com.example.mariaventura.pruebaframe.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.example.mariaventura.pruebaframe.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends Activity {

        private Timer timer;
        private int i = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.activity_splash_screen);

            ImageView demoImage = (ImageView)findViewById(R.id.logo);
            demoImage.getLayoutParams().height = 500;
            demoImage.getLayoutParams().width = 500;


//SPLASH

            final long intervalo = 45;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (i < 100){
                        i++;
                    }else{
                        timer.cancel();
                        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            },0,intervalo);

        }
}


