package com.example.jeevanjyoti;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AddFamilyActivity extends AppCompatActivity {
    EditText mTestEditText;
    String mTestString;
    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_education_details);
        Button lButton = findViewById(R.id.submit_button);
        LayoutInflater layoutInflator = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout lLauOut = findViewById(R.id.root_linear);
        List<View> views = new ArrayList<>();

        for(int i = 0; i<2; i++){
            View view = null;
            if (layoutInflator != null) {
                view = layoutInflator.inflate(R.layout.user_education_details, null);
                Spinner lEducation = view.findViewById(R.id.eucation_spinner);
                Spinner lEducationStatus = view.findViewById(R.id.education_status_spinner);
                mTestEditText = view.findViewById(R.id.test_edit_text);
                mTestString = mTestEditText.getText().toString();
                views.add(view);
            }

        }
        lButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("AddFamilyActivity", "Add Test: "+mTestString);
            }
        });
        for (int i = 0; i<views.size(); i++){
            lLauOut.addView(views.get(i));
        }
    }
}
