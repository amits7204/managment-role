package com.example.jeevanjyoti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button lUserRegButton = findViewById(R.id.user_reg_button);
        Button lVolunteerRegButton = findViewById(R.id.volunteer_reg_button);
        Button lAdminRegButton = findViewById(R.id.admin_reg_button);
        final String MY_PREFS_NAME = "MyPrefsFile";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String lMobileNumber = prefs.getString("mobile", null);
        lVolunteerRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lIntent = new Intent(MainActivity.this, VolunteerActivity.class);
                startActivity(lIntent);
            }
        });

        lUserRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(lIntent);
            }
        });

        lAdminRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lMobileNumber==null) {
                    Log.w(TAG,"Enter AdminActivity");
                    Intent lIntent = new Intent(MainActivity.this, AdminActivity.class);
                    startActivity(lIntent);
                }else{
                    Log.w(TAG,"Enter AdminDashBoard");
                    Intent lIntent = new Intent(MainActivity.this, AdminDashBoard.class);
                    startActivity(lIntent);
                }
            }
        });
    }
}
