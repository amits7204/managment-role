package com.example.jeevanjyoti;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class AdminDashBoard extends AppCompatActivity {
    public static final String TAG = "AdminDashBoard";
    private TextView mUserRegister, mUserDetails, mVolunteerDetails;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard);
        mUserRegister = findViewById(R.id.user_register_textview);
        mUserDetails = findViewById(R.id.user_details_textview);
        mVolunteerDetails = findViewById(R.id.volunteer_detail_textview);
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
        goToUserRegister();
        goToVolunteerDetails();
        goToUserDetails();
    }

    public void goToUserRegister(){
        mUserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lIntent = new Intent(AdminDashBoard.this, YesOrNoActivity.class);
                lIntent.putExtra("Activity", "A");
                startActivity(lIntent);
            }
        });
    }

    public void goToUserDetails(){
        mUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lIntent = new Intent(AdminDashBoard.this, UserDetailsActivity.class);
                startActivity(lIntent);
            }
        });
    }

    public void goToVolunteerDetails(){
        mVolunteerDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lIntent = new Intent(AdminDashBoard.this, VolunteerDetails.class);
                startActivity(lIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}
