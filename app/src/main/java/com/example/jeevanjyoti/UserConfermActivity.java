package com.example.jeevanjyoti;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.jeevanjyoti.otpPojo.OtpRoot;
import com.example.jeevanjyoti.registerencapsulation.UserRegister;
import com.example.jeevanjyoti.retrofit.RetrofitClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserConfermActivity extends AppCompatActivity {
    private static final String TAG = "UserConfermActivity";
    TextView mFullName, mFatherName, mMotherName, mMobileNumber,
    mGender, mDOB, mMaritalStatus, mQualification, mEducationStatus,
    mOccupation, mOccupationDiscription, mFullAddress, mBuilding, mRoadStreet,
    mArea, mPinCode, mState, mDistrict, mUniqueId, mEduDisc;
    Button mEditButton, mSaveButton;
    private LottieAnimationView mAnimationView;
    ImageView mSuccessFullImage;
    LinearLayout mAddFamilyLinearLayout;
    String mFullNameString, mFatherNameString, mMotherNameString, mMobile, mGenderString, mDob, mQualificationString,
    mMarital, mEducationStatusString, mOccupationString, mOccupationDisc, mFlatString, mBuildingString,
    mRoadStreetString, mAreaString, mPinCodeString, mStateString, mDistrictString, mUniqueIdString,
    mEduDiscString;
    Button mVerifyButton;
    EditText mOtpEditText;
    FrameLayout mOtpFrameLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_confirm_activity);
        mOtpFrameLayout = findViewById(R.id.otp_frame_layout);
        mUniqueId = findViewById(R.id.unique_id);
        mFullName = findViewById(R.id.full_name);
        mFatherName = findViewById(R.id.father_name);
        mSuccessFullImage = findViewById(R.id.successful);
        mMotherName = findViewById(R.id.mother_name);
        mOtpEditText = findViewById(R.id.r_otp_edit_text);
        mMobileNumber = findViewById(R.id.mobile_text_view);
        mEduDisc = findViewById(R.id.education_discription);
        mGender = findViewById(R.id.gender_text_view);
        mDOB = findViewById(R.id.dob_text_view);
        mVerifyButton = findViewById(R.id.r_button);
        mMaritalStatus = findViewById(R.id.marital_status_text_view);
        mQualification = findViewById(R.id.qulification_text_view);
        mEducationStatus = findViewById(R.id.education_status_text_view);
        mOccupation = findViewById(R.id.occupation_text_view);
        mOccupationDiscription = findViewById(R.id.occupation_description_text_view);
        mFullAddress = findViewById(R.id.full_addr_text_view);
        Intent lIntent = getIntent();
        mFullNameString = lIntent.getStringExtra("name");
        mUniqueIdString = lIntent.getStringExtra("uniqueId");
        mFatherNameString = lIntent.getStringExtra("fathername");

        mMotherNameString = lIntent.getStringExtra("mothername");

        mMobile = lIntent.getStringExtra("mobile");

        mGenderString = lIntent.getStringExtra("gender");

        mDob = lIntent.getStringExtra("dob");

        mMarital = lIntent.getStringExtra("marital");

        mQualificationString = lIntent.getStringExtra("qualification");

        mEducationStatusString = lIntent.getStringExtra("edicationstatus");
        mEduDiscString = lIntent.getStringExtra("edudisc");
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
        mUniqueId.setText(mUniqueIdString);
        mFatherName.setText(mFatherNameString);
        mMotherName.setText(mMotherNameString);
        mMobileNumber.setText(mMobile);
        mGender.setText(mGenderString);
        mDOB.setText(mDob);
        mMaritalStatus.setText(mMarital);
        mQualification.setText(mQualificationString);
        mEducationStatus.setText(mEducationStatusString);
        mEduDisc.setText(mEduDiscString);
        mOccupation.setText(mOccupationString);
        mOccupationDiscription.setText(mOccupationDisc);
        mFullAddress.setText(mFlatString+" "+mBuildingString+" "+mRoadStreetString+" "+mAreaString+
                " "+mPinCodeString+" "+mStateString+" "+mDistrictString);
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
        mVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOtp();
            }
        });
    }

    public void sendOtp(){
        String lOtpString = mOtpEditText.getText().toString();
        UserRegisterApi lApi = RetrofitClient.postUserdata();
        Call<OtpRoot> lOtpRoot = lApi.verifyUserOtp(lOtpString);
        lOtpRoot.enqueue(new Callback<OtpRoot>() {
            @Override
            public void onResponse(Call<OtpRoot> call, Response<OtpRoot> response) {
                if (response.isSuccessful() && response.body()!=null){
                    Log.w("OTPVERIFY", "RESPONSE OTP: " + response.body().getData());
                    String lJson = response.body().getData();
                    String lStatus = "";
                    try {
                        JSONObject lJsonObj = new JSONObject(lJson);
//                        JSONArray jsonData = lJsonObj.getJSONArray("data");
                        Log.w(TAG,"JSON Object: "+lJsonObj.length());
                        for (int i = 0; i<lJsonObj.length(); i++){
//                            JSONObject jsonOBject = lJsonObj.getJSONObject(i);
                            lStatus = lJsonObj.getString("Status");
                            Log.w(TAG,"STATUS: "+lStatus);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (lStatus.equals("Success")) {
                        mOtpFrameLayout.setVisibility(View.GONE);
                    }else {

                        mOtpEditText.setError("Please Fill correct OTP");
                        mOtpEditText.requestFocus();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Something is Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OtpRoot> call, Throwable t) {

            }
        });
    }

    public void registerUser(){
        Log.w(TAG,"Education Discription: "+mEduDiscString+ " "+mUniqueIdString);
        UserRegisterApi lUserRegisterApi = RetrofitClient.postUserdata();
        Call<UserRegister> lCallUserResponse = lUserRegisterApi.sendUserData(
                mFullNameString,
                mUniqueIdString,
                mFatherNameString,
                mMotherNameString,
                mMobile,
                mGenderString,
                mDob,
                mMarital,
                mQualificationString,
                mEducationStatusString,
                mEduDiscString,
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
                    mSaveButton.setVisibility(View.GONE);
                    mSuccessFullImage.setVisibility(View.VISIBLE);
                    mOtpFrameLayout.setVisibility(View.VISIBLE);
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
