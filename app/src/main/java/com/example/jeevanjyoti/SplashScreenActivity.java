package com.example.jeevanjyoti;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    private LottieAnimationView mAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        mAnimationView = findViewById(R.id.animation_view);
//        mAnimationView.playAnimation();
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                //rightlayout.setVisibility(View.GONE);
//                    mAnimationView.cancelAnimation();
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);//LoginTab
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    SplashScreenActivity.this.overridePendingTransition(R.anim.left_anim, R.anim.right_anim);
                    startActivity(i);


                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
