package com.example.jeevanjyoti;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VolunteerActivity extends AppCompatActivity {
    private static final String TAG = "VolunteerActivity";
    private ImageView mImageView, mMenImageView;
    private TextView mMaleTextView, mFemaleTextView;
    private EditText mNameEditText, mMobileNumberEditText, mFullAddressEditText;
    private Button mButton;
    LinearLayout mMaleLinearLayout, mFemaleLinearLayout;
    Volunteer mVolunteer = new Volunteer();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volunteer_activity);
        mImageView = findViewById(R.id.profile_image);
        mMaleTextView = findViewById(R.id.male_textview);
        mFemaleTextView = findViewById(R.id.female_textView);
        mNameEditText = findViewById(R.id.name_edit_textview);
        mMobileNumberEditText = findViewById(R.id.mobile_edit_textview);
        mFullAddressEditText = findViewById(R.id.address_textView);
        mButton = findViewById(R.id.button);
        mMaleLinearLayout = findViewById(R.id.male_linear_layout);
        mFemaleLinearLayout = findViewById(R.id.female_linear_layout);
        mMenImageView = findViewById(R.id.men_imageView);
        final String lNameString = mNameEditText.getText().toString();
        final String lMobileNumberString = mMobileNumberEditText.getText().toString();
        final String lFullAddressString = mFullAddressEditText.getText().toString();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getGender();
                Log.w(TAG,"Onclick Button");
                mVolunteer.setFullName(lNameString);
                mVolunteer.setMobileNumber(lMobileNumberString);
                mVolunteer.setAddress(lFullAddressString);
                Intent lIntent = new Intent(VolunteerActivity.this, OtpVerficationActivity.class);
                startActivity(lIntent);
            }
        });
        getGender();

    }

    public void getGender(){
        mMaleLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mMaleLinearLayout.setBackground(getDrawable(R.drawable.text_view_shape));
                    mMaleTextView.setTextColor(getColor(R.color.colorPrimaryBlack));
                    mMenImageView.setImageDrawable(getDrawable(R.drawable.ic_men_black));
                    mFemaleTextView.setTextColor(getColor(R.color.colorAccent));
                }
                mFemaleLinearLayout.setBackground(getDrawable(R.drawable.secondary_primary_box));
                String lMaleString = mMaleTextView.getText().toString();
                Log.w(TAG,"Gender Status: "+lMaleString);
                mVolunteer.setGender(lMaleString);
            }
        });

        mFemaleLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mFemaleLinearLayout.setBackground(getDrawable(R.drawable.text_view_shape));
                    mFemaleTextView.setTextColor(getColor(R.color.colorPrimaryBlack));
                    mMaleTextView.setTextColor(getColor(R.color.colorAccent));
                    mMenImageView.setImageDrawable(getDrawable(R.drawable.ic_men));
                }
                mMaleLinearLayout.setBackground(getDrawable(R.drawable.secondary_primary_box));

                String lFemaleString = mFemaleTextView.getText().toString();
                Log.w(TAG,"Gender Status: "+lFemaleString);
                mVolunteer.setGender(lFemaleString);

            }
        });
    }
}
