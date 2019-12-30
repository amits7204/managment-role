package com.example.jeevanjyoti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDashBoard extends AppCompatActivity {
    public static final String TAG = "AdminDashBoard";
    private TextView mUserRegister, mUserDetails, mVolunteerDetails;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard);
        mUserRegister = findViewById(R.id.user_register_textview);
        mUserDetails = findViewById(R.id.user_details_textview);
        mVolunteerDetails = findViewById(R.id.volunteer_detail_textview);
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
