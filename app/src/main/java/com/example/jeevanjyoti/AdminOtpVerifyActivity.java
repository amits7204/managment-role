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
import com.example.jeevanjyoti.otpPojo.OtpRoot;
import com.example.jeevanjyoti.retrofit.AdminOtpVerify;
import com.example.jeevanjyoti.retrofit.AdminRegister;
import com.example.jeevanjyoti.retrofit.RetrofitClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;

import org.json.JSONException;
import org.json.JSONObject;

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
//        mOtpEditText.setShowSoftInputOnFocus(false);
        mOtpButton = findViewById(R.id.verify_button);
//        SmsDetector.bindListener(new Common.OTPListener() {
//            @Override
//            public void onOtpReceived(String otp) {
//                mOtpEditText.setText(otp);
//            }
//        });
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
                        Call<OtpRoot> lCallUserResponse = lUserRegisterApi.sendOtpNumber(lOtpString);

                        lCallUserResponse.enqueue(new Callback<OtpRoot>() {
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
                                        editor.putString("vmobile", lMobileString);
                                        editor.apply();
                                        Intent lIntent = new Intent(AdminOtpVerifyActivity.this, AdminDashBoard.class);
                                        startActivity(lIntent);
                                        mProgress.setVisibility(View.GONE);
                                    }else {
                                        mOtpEditText.setError("Please Fill correct OTP");
                                        mOtpEditText.requestFocus();
                                        mProgress.setVisibility(View.GONE);
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(),"Something is Wrong", Toast.LENGTH_SHORT).show();
                                    mProgress.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onFailure(Call<OtpRoot> call, Throwable t) {
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
