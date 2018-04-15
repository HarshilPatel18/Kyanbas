package com.anomaly.android.kyanbas.View.Splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.anomaly.android.kyanbas.R;
import com.anomaly.android.kyanbas.View.Main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DELAY = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView kyanbaslogo= findViewById(R.id.splashLogo);
        ImageView kyanbastext= findViewById(R.id.splashText);
        Animation uptodown= AnimationUtils.loadAnimation(this,R.anim.uptodown);
        Animation downtoup= AnimationUtils.loadAnimation(this,R.anim.downtoup);
        kyanbaslogo.setAnimation(uptodown);
        kyanbastext.setAnimation(downtoup);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        },SPLASH_DELAY);
    }
}
