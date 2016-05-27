package com.elsy.rynder;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.elsy.rynder.modules.login.LoginActivity;
import com.elsy.rynder.modules.maps.MapActivity;
import com.elsy.rynder.utils.ActivityHelper;
import com.elsy.rynder.utils.TextViewUtils;
import com.elsy.rynder.utils.preferences_manager.UserSessionManager;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;
    private TextView tx_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initUI();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              sendTo();
            }
        }, SPLASH_TIME_OUT);
    }

    private void sendTo(){
        final UserSessionManager sessionManager = new UserSessionManager(SplashActivity.this);
        if(sessionManager.isUserLoggedIn()) {
            ActivityHelper.sendTo(this, MapActivity.class);
        }else  ActivityHelper.sendTo(this, LoginActivity.class);
    }

    private void initUI(){
        tx_message = (TextView) findViewById(R.id.splash_message);
        TextViewUtils.setLobsterTypeface(this, tx_message);
    }
}
