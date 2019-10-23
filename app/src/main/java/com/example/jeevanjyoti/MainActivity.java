package com.example.jeevanjyoti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button lUserRegButton = findViewById(R.id.user_reg_button);
        Button lVolunteerRegButton = findViewById(R.id.volunteer_reg_button);
        Button lAdminRegButton = findViewById(R.id.admin_reg_button);

        lVolunteerRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lIntent = new Intent(MainActivity.this, VolunteerActivity.class);
                startActivity(lIntent);
            }
        });
    }
}
