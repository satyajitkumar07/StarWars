package com.gami.starwars.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gami.starwars.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    private Timer timerObj;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        timerObj = new Timer();
        TimerTask timerTaskObj = new TimerTask() {
            public void run() {
                //perform your action here
                Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        timerObj.schedule(timerTaskObj, 500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerObj!=null){
            timerObj.cancel();
        }
    }
}
