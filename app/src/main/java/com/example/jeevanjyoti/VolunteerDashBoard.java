package com.example.jeevanjyoti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VolunteerDashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volunteer_dashboard);
        TextView lTextView = findViewById(R.id.volunteer_user_register_textview);
        lTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lIntent = new Intent(VolunteerDashBoard.this,RegisterActivity.class);
                startActivity(lIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}
