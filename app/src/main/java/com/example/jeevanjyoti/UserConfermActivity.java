package com.example.jeevanjyoti;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jeevanjyoti.registerencapsulation.UserRegister;
import com.example.jeevanjyoti.retrofit.RetrofitClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserConfermActivity extends AppCompatActivity {
    TextView mFullName, mFatherName, mMotherName, mMobileNumber,
    mGender, mDOB, mMaritalStatus, mQualification, mEducationStatus,
    mOccupation, mOccupationDiscription, mFlatRoom, mBuilding, mRoadStreet,
    mArea, mPinCode, mState, mDistrict;
    Button mEditButton, mSaveButton;
    ImageView mSuccessFullImage;
    String mFullNameString, mFatherNameString, mMotherNameString, mMobile, mGenderString, mDob, mQualificationString,
    mMarital, mEducationStatusString, mOccupationString, mOccupationDisc, mFlatString, mBuildingString,
    mRoadStreetString, mAreaString, mPinCodeString, mStateString, mDistrictString;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_confirm_activity);
        mFullName = findViewById(R.id.full_name);
        mFatherName = findViewById(R.id.father_name);
        mMotherName = findViewById(R.id.mother_name);
        mMobileNumber = findViewById(R.id.mobile_text_view);
        mGender = findViewById(R.id.gender_text_view);
        mDOB = findViewById(R.id.dob_text_view);
        mMaritalStatus = findViewById(R.id.marital_status_text_view);
        mQualification = findViewById(R.id.qulification_text_view);
        mEducationStatus = findViewById(R.id.education_status_text_view);
        mOccupation = findViewById(R.id.occupation_text_view);
        mOccupationDiscription = findViewById(R.id.occupation_description_text_view);
        mFlatRoom = findViewById(R.id.flat_room_text_view);
        mBuilding = findViewById(R.id.building_villege_text_view);
        mRoadStreet = findViewById(R.id.road_lane_text_view);
        mArea = findViewById(R.id.area_locality_text_view);
        mPinCode = findViewById(R.id.pin_code_text_view);
        mState = findViewById(R.id.state_text_view);
        mDistrict = findViewById(R.id.district_text_view);
        mSuccessFullImage = findViewById(R.id.successfull_image_view);

        Intent lIntent = getIntent();
        mFullNameString = lIntent.getStringExtra("name");

        mFatherNameString = lIntent.getStringExtra("fathername");

        mMotherNameString = lIntent.getStringExtra("mothername");

        mMobile = lIntent.getStringExtra("mobile");

        mGenderString = lIntent.getStringExtra("gender");

        mDob = lIntent.getStringExtra("dob");

        mMarital = lIntent.getStringExtra("marital");

        mQualificationString = lIntent.getStringExtra("qualification");

        mEducationStatusString = lIntent.getStringExtra("edicationstatus");

        mOccupationString = lIntent.getStringExtra("occupation");

        mOccupationDisc = lIntent.getStringExtra("occupationDescription");

        mFlatString = lIntent.getStringExtra("flat");

        mBuildingString = lIntent.getStringExtra("building");

        mRoadStreetString = lIntent.getStringExtra("road");

        mAreaString = lIntent.getStringExtra("area");

        mPinCodeString = lIntent.getStringExtra("pincode");

        mStateString = lIntent.getStringExtra("state");

        mDistrictString = lIntent.getStringExtra("district");



        mEditButton = findViewById(R.id.edit_button);
        mSaveButton = findViewById(R.id.save_button);
        mFullName.setText(mFullNameString);
        mFatherName.setText(mFatherNameString);
        mMotherName.setText(mMotherNameString);
        mMobileNumber.setText(mMobile);
        mGender.setText(mGenderString);
        mDOB.setText(mDob);
        mMaritalStatus.setText(mMarital);
        mQualification.setText(mQualificationString);
        mEducationStatus.setText(mEducationStatusString);
        mOccupation.setText(mOccupationString);
        mOccupationDiscription.setText(mOccupationDisc);
        mFlatRoom.setText(mFlatString);
        mBuilding.setText(mBuildingString);
        mRoadStreet.setText(mRoadStreetString);
        mArea.setText(mAreaString);
        mPinCode.setText(mPinCodeString);
        mState.setText(mStateString);
        mDistrict.setText(mDistrictString);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void registerUser(){

        UserRegisterApi lUserRegisterApi = RetrofitClient.postUserdata();
        Call<UserRegister> lCallUserResponse = lUserRegisterApi.sendUserData(
                mFullNameString,
                mFatherNameString,
                mMotherNameString,
                mMobile,
                mGenderString,
                mDob,
                mMarital,
                mQualificationString,
                mEducationStatusString,
                mOccupationString,
                mOccupationDisc,
                mFlatString,
                mBuildingString,
                mRoadStreetString,
                mAreaString,
                mPinCodeString,
                mStateString,
                mDistrictString);

        lCallUserResponse.enqueue(new Callback<UserRegister>() {
            @Override
            public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {
                Log.w("RegisterActivity", "Response: " + response);
                if (response.isSuccessful()){
                    mSuccessFullImage.setVisibility(View.VISIBLE);
                    mSaveButton.setVisibility(View.GONE);
                }else {
                    Toast.makeText(getApplicationContext(),"Something is Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserRegister> call, Throwable t) {
                Log.w("RegisterActivity", "Response Failed: " + t.toString() + call.toString());
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
