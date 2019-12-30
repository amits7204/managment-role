package com.example.jeevanjyoti;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class UUIDRegisterOtp extends AppCompatActivity {
    EditText mOtpEditText;
    Button mOtpButton;
    TextView mTimerTextView;
    private static final String TAG = "UUIDRegisterOtp";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uuid_otp);
        mOtpEditText = findViewById(R.id.uuid_otp_edit_text);
        mOtpButton = findViewById(R.id.verify_uuid_otp_button);
        mTimerTextView = findViewById(R.id.resend_otp);

        mOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmOtp();
            }
        });
    }

    private void confirmOtp(){
        Intent lIntent = getIntent();
        String lUUid = lIntent.getStringExtra("uuid");
        String lOtpString = mOtpEditText.getText().toString();
        Map<String, String> lOtpNumber = new HashMap<>();
        lOtpNumber.put("uuid", lUUid);
        lOtpNumber.put("otp", lOtpString);
        Log.w(TAG,"UUID: "+lUUid+ "otp: "+lOtpString);
        UserRegisterApi lApi = RetrofitUserClient.userdata();
        Call<RegisterConfirmOtp> lJsonObject = lApi.confirmOtp(lOtpNumber);
        lJsonObject.enqueue(new Callback<RegisterConfirmOtp>() {
            @Override
            public void onResponse(Call<RegisterConfirmOtp> call, Response<RegisterConfirmOtp> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    Log.w(TAG, "Response: " + response.body().getUUID());
                    Intent lIntent = new Intent(UUIDRegisterOtp.this, RegisterActivity.class);
                    lIntent.putExtra("uuid",response.body().getUUID());
                    lIntent.putExtra("no", "no");
                    startActivity(lIntent);
                    finish();
                }else{
                    Toast.makeText(UUIDRegisterOtp.this, "Please enter correct otp:", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterConfirmOtp> call, Throwable t) {
                Log.w(TAG, "Failed: "+t.getMessage());
            }
        });
    }
}
