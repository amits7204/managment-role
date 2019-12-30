package com.example.jeevanjyoti;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AboutUsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_layout);
        TextView lAboutUs = findViewById(R.id.about_us);
        TextView lOurTeam = findViewById(R.id.our_team);
        TextView lOurHistory = findViewById(R.id.our_history);
        lAboutUs.setText("MISSION:  We focus on making the maximum positive effort for our community. " +
                "Our members and volunteers provide the momentum that helps us affect change. " +
                "Using data driven models, we provide solutions that make a long-lasting difference..");

        lOurTeam.setText("TEAM: Our amazing team of regulars and part-time volunteers are committed to helping others. " +
                "We take our convictions and turn them into action. " +
                "Think you would be a good fit? Get in touch for more information!");
        lOurHistory.setText("HISTORY:  Seeing a need for energetic, nonprofit work in this area, " +
                "we formed our organization to provide sensible solutions." +
                " We've consistently grown since then, all thanks to the helping hands of " +
                "this amazing community!");


    }
}
