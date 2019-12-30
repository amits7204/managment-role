package com.example.jeevanjyoti;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jeevanjyoti.otpPojo.RegisterConfirmOtp;
import com.example.jeevanjyoti.retrofit.RetrofitUserClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileRegisterOtp extends AppCompatActivity {
    EditText mOtpEditText;
    Button mOtpButton;
    private static final String TAG = "MobileRegisterOtp";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_register_otp);
        mOtpEditText = findViewById(R.id.v_otp_edit_text);
        mOtpButton = findViewById(R.id.verify_user_otp_button);

        mOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmOtp();
            }
        });
    }

    private void confirmOtp(){
        Intent lIntent = getIntent();
        String lMobile = lIntent.getStringExtra("mobile");
        String lOtpNo = mOtpEditText.getText().toString();
        if(lOtpNo.length() == 0){
            mOtpEditText.setError("Please Enter Otp number");
            mOtpEditText.requestFocus();
        }
        Map<String, String> lOtpNumber = new HashMap<>();
        lOtpNumber.put("mobile", lMobile);
        lOtpNumber.put("otp", lOtpNo);

        UserRegisterApi lApi = RetrofitUserClient.userdata();
        Call<RegisterConfirmOtp> lJsonObject = lApi.confirmOtp(lOtpNumber);
        lJsonObject.enqueue(new Callback<RegisterConfirmOtp>() {
            @Override
            public void onResponse(Call<RegisterConfirmOtp> call, Response<RegisterConfirmOtp> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    Log.w(TAG, "Response: " + response.body().getUUID());
                    Intent lIntent = new Intent(MobileRegisterOtp.this, RegisterActivity.class);
                    lIntent.putExtra("uuid",response.body().getUUID());
                    startActivity(lIntent);
                    finish();
                }else if(response.code() == 409){
                    Toast.makeText(getApplicationContext(),"this number already exist", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterConfirmOtp> call, Throwable t) {
                Log.w(TAG, "Failed: "+t.getMessage());
            }
        });
    }
}
