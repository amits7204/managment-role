package com.example.jeevanjyoti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.jeevanjyoti.registerencapsulation.UserRegister;
import com.example.jeevanjyoti.retrofit.AdminRegister;
import com.example.jeevanjyoti.retrofit.RetrofitClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {
    final static String TAG = "AdminActivity";
    private int REQUEST_CODE_READ_SMS = 1;
    private EditText mMobileNumberEditText;
    private  Button mButton;
    private String mMobileString = "8697903433";
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);
        mButton = findViewById(R.id.admin_button);
        mMobileNumberEditText = findViewById(R.id.admin_mobile);
        mProgressBar = findViewById(R.id.progressBar);
        ActivityCompat.requestPermissions(AdminActivity.this,
                new String[]{android.Manifest.permission.RECEIVE_SMS}, REQUEST_CODE_READ_SMS);

        getOtp();

    }

    public void getOtp(){

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String lMobileString = mMobileNumberEditText.getText().toString();
                mProgressBar.setVisibility(View.VISIBLE);
                Log.w(TAG,"GET Mobile Number: "+lMobileString);
                if (mMobileString.contentEquals(lMobileString)){
                    Log.w(TAG,"GET Mobile Number: "+lMobileString);
                    UserRegisterApi lUserRegisterApi = RetrofitClient.postUserdata();
                    Call<AdminRegister> lCallUserResponse = lUserRegisterApi.sendAdminNumber(lMobileString);

                    lCallUserResponse.enqueue(new Callback<AdminRegister>() {
                        @Override
                        public void onResponse(Call<AdminRegister> call, Response<AdminRegister> response) {
                            Log.w("RegisterActivity", "Response: " + response);

                                Intent lIntent = new Intent(AdminActivity.this, AdminOtpVerifyActivity.class);
                                lIntent.putExtra("mobile", lMobileString);
                                startActivity(lIntent);
                                mProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<AdminRegister> call, Throwable t) {
                            Log.w("RegisterActivity", "Response Failed: " + t.toString() + call.toString());
                            mProgressBar.setVisibility(View.GONE);
                        }
                    });
                }else {
                    Log.w(TAG,"MY Mobile NUmber: "+lMobileString);
                    mMobileNumberEditText.setError("Please Fill required Mobile Number");
                    mMobileNumberEditText.requestFocus();
                }

            }
        });

    }
}
