package com.example.jeevanjyoti;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AboutUsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_layout);
        TextView lAboutUs = findViewById(R.id.about_us);
        lAboutUs.setText("Jeeven Jyoti, A Valmiki Support Systems is designed to collect the" +
                " information about every household and population of our community across the " +
                "India so that we can connect with each other. Our vision is to built the strong " +
                "systems and filter out the problems of our community. Our mission to connect the " +
                "community with the systems.");
    }
}
