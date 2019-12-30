package com.example.jeevanjyoti;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jeevanjyoti.broadcastReceiver.Common;
import com.example.jeevanjyoti.broadcastReceiver.SmsDetector;
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
    private ProgressBar mProgress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_otp_verify);
        mOtpEditText = findViewById(R.id.otp_editText);
//        hideSoftKeyboard();
        mProgress = findViewById(R.id.progressBar_otp);
        mOtpEditText.setShowSoftInputOnFocus(false);
        mOtpButton = findViewById(R.id.verify_button);
        SmsDetector.bindListener(new Common.OTPListener() {
            @Override
            public void onOtpReceived(String otp) {
                mOtpEditText.setText(otp);
            }
        });
        verifyOtp();

    }
    @Override
    protected void onDestroy() {
        SmsDetector.unbindListener();
        super.onDestroy();
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
                mProgress.setVisibility(View.VISIBLE);
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

                                if (response.isSuccessful() && response.body()!=null) {
                                    Log.w("AdminOtpVerifyActivity", "Response: " + response);
                                    editor.putString("mobile", lMobileString);
                                    editor.apply();
                                    Intent lIntent = new Intent(AdminOtpVerifyActivity.this, AdminDashBoard.class);
                                    startActivity(lIntent);
                                    mProgress.setVisibility(View.GONE);

                                }else {
                                    Toast.makeText(getApplicationContext(),"Something is wrong",Toast.LENGTH_SHORT).show();
                                   mProgress.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onFailure(Call<AdminOtpVerify> call, Throwable t) {
                                Log.w("RegisterActivity", "Response Failed: " + t.toString() + call.toString());
                                Toast.makeText(getApplicationContext(),"Something is wrong",Toast.LENGTH_SHORT).show();
                               mProgress.setVisibility(View.GONE);
                            }
                        });
                    }
            }
        });
    }
}
