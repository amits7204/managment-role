package com.example.jeevanjyoti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jeevanjyoti.retrofit.AdminOtpVerify;
import com.example.jeevanjyoti.retrofit.AdminRegister;
import com.example.jeevanjyoti.retrofit.RetrofitClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminOtpVerifyActivity extends AppCompatActivity {
    private final static String TAG = "AdminOtpVerifyActivity";
    private Button mOtpButton;
    private EditText mOtpEditText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_otp_verify);
        mOtpEditText = findViewById(R.id.otp_editText);
        mOtpButton = findViewById(R.id.verify_button);
        verifyOtp();
    }
    private void verifyOtp() {
        Intent lMobileIntent = getIntent();
        final String lMobileString = lMobileIntent.getStringExtra("mobile");
        final String MY_PREFS_NAME = "MyPrefsFile";
        final SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        mOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String lOtpString = mOtpEditText.getText().toString();
                Log.w(TAG, "GET Mobile Number: " + lOtpString);
                    if (lOtpString.isEmpty()) {
                        mOtpEditText.setError("Please Enter valid otp");
                        mOtpEditText.requestFocus();
                    }else{
                        UserRegisterApi lUserRegisterApi = RetrofitClient.postUserdata();
                        Call<AdminOtpVerify> lCallUserResponse = lUserRegisterApi.sendOtpNumber(lOtpString);

                        lCallUserResponse.enqueue(new Callback<AdminOtpVerify>() {
                            @Override
                            public void onResponse(Call<AdminOtpVerify> call, Response<AdminOtpVerify> response) {
                                Log.w("RegisterActivity", "Response: " + response);
                                editor.putString("mobile", lMobileString);
                                editor.apply();
                                Intent lIntent = new Intent(AdminOtpVerifyActivity.this, AdminDashBoard.class);
                                startActivity(lIntent);
                            }

                            @Override
                            public void onFailure(Call<AdminOtpVerify> call, Throwable t) {
                                Log.w("RegisterActivity", "Response Failed: " + t.toString() + call.toString());
                            }
                        });
                    }
            }
        });
    }
}
