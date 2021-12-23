package com.nappdeveloper.paryatn.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.nappdeveloper.paryatn.R;

public class splashActivity extends AppCompatActivity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    LottieAnimationView animView;
    SharedPreferences onBoardingScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setStatusBarColor(Color.parseColor("#0072ff"));

        animView = findViewById(R.id.animation_open);

        float progress = animView.getProgress();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(-progress,0 ).setDuration((long) ( animView.getDuration()* progress));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                animView.setProgress(Math.abs((float)animation.getAnimatedValue()));
                animView.setSpeed(-1);
            }
        });
        valueAnimator.start();

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                onBoardingScreen = getSharedPreferences("OnBoardingScreen", MODE_PRIVATE);
                boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);

                if (isFirstTime) {
                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();

                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(getApplicationContext(), OnBoarding.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(getApplicationContext(), signInActivity.class);
                    startActivity(mainIntent);
                    finish();
                }


            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}