package com.example.jeevanjyoti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class YesOrNoActivity extends AppCompatActivity {
    private Intent mIntentYesorNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yes_or_no_activity);
        final TextView lYesTextView = findViewById(R.id.yes_id);
        final TextView lNoTextView = findViewById(R.id.no_id);

        View.OnClickListener lSetOnClickListner;

        lSetOnClickListner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.yes_id:
                        Log.w("YesOrNo", "Yes TextView: "+lYesTextView.getText().toString());
                        mIntentYesorNo = new Intent(YesOrNoActivity.this, VerifyMobileNumber.class);
                        mIntentYesorNo.putExtra("yes",lYesTextView.getText().toString());
                        startActivity(mIntentYesorNo);
                        break;
                    case R.id.no_id:
                        Log.w("YesOrNo", "NO TextView: "+lNoTextView.getText().toString());
                        mIntentYesorNo = new Intent(YesOrNoActivity.this, UUIDActivity.class);
                        mIntentYesorNo.putExtra("no",lNoTextView.getText().toString());
                        startActivity(mIntentYesorNo);
                        break;
                    default:
                        break;
                }
            }
        };

        lYesTextView.setOnClickListener(lSetOnClickListner);
        lNoTextView.setOnClickListener(lSetOnClickListner);

        Intent lIntent = getIntent();
        String lString = lIntent.getStringExtra("Activity");

        final String MY_PREFS_NAME = "MyPrefsFile";
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("Activity", lString);
        editor.apply();
    }
}
