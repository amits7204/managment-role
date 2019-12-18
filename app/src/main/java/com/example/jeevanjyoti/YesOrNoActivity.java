package com.example.jeevanjyoti;

import android.content.Intent;
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

        String lStringYesNo;
        lSetOnClickListner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.yes_id:
                        mIntentYesorNo = new Intent(YesOrNoActivity.this, RegisterActivity.class);
                        mIntentYesorNo.putExtra("yes",lYesTextView.getText().toString());
                        startActivity(mIntentYesorNo);
                        Log.w("YesOrNo", "Yes TextView: "+lYesTextView.getText().toString());
                        break;
                    case R.id.no_id:
                        mIntentYesorNo = new Intent(YesOrNoActivity.this, RegisterActivity.class);
                        mIntentYesorNo.putExtra("no",lNoTextView.getText().toString());
                        startActivity(mIntentYesorNo);
                        Log.w("YesOrNo", "NO TextView: "+lNoTextView.getText().toString());
                }
            }
        };

        lYesTextView.setOnClickListener(lSetOnClickListner);
        lNoTextView.setOnClickListener(lSetOnClickListner);

//        lYesTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        lNoTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent lIntentNO = new Intent(YesOrNoActivity.this, RegisterActivity.class);
//                lIntentNO.putExtra("no",lNoTextView.getText().toString());
//                startActivity(lIntentNO);
//            }
//        });
    }
}
