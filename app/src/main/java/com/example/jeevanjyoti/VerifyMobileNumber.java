package com.example.jeevanjyoti;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.jeevanjyoti.retrofit.RetrofitUserClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyMobileNumber extends AppCompatActivity {
    private static final String TAG = "VerifyMobileNumber";
    EditText mMobileNumber, mOtpNumber, mUniqueId;
    Button mButton, mUuidVerifyButton;
    String mMobileString, mUuid;
    ImageView mBackImageView;
    String mYes = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_mobile_number);
        mMobileNumber = findViewById(R.id.v_verify_mobile_number);
        mOtpNumber = findViewById(R.id.v_otp_edit_text);
        mBackImageView = findViewById(R.id.back_button);
        mUniqueId = findViewById(R.id.v_verify_unique_id);
        mButton = findViewById(R.id.submit_mobile_number);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerMobileNumber();
                mBackImageView.setVisibility(View.VISIBLE);
            }
        });

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Intent lIntent = getIntent();
        mYes = lIntent.getStringExtra("yes");

        if (mYes==null){
            Log.w(TAG,"Yes Value: "+mYes);
            mMobileNumber.setVisibility(View.GONE);
            mUniqueId.setVisibility(View.VISIBLE);
            mButton.setVisibility(View.GONE);
        }
    }

    private void registerMobileNumber(){
        mMobileString = mMobileNumber.getText().toString();
        if (mMobileString.isEmpty() && mMobileNumber.length()<10){
            mMobileNumber.setError("Please enter your Mobile Number");
            mMobileNumber.requestFocus();
        }
        Map<String, String> lMobileNumber = new HashMap<>();
        lMobileNumber.put("mobile", mMobileString);
        UserRegisterApi lApi = RetrofitUserClient.userdata();
        Call<ResponseBody> lJsonObject = lApi.sendOtp(lMobileNumber);
        lJsonObject.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful() && response.body()!=null) {
                    Log.w(TAG, "Response: " + response.body().toString());
                    Intent lIntent = new Intent(VerifyMobileNumber.this, MobileRegisterOtp.class);
                    lIntent.putExtra("mobile", mMobileString);
                    lIntent.putExtra("yes", "yes");
                    startActivity(lIntent);
                }else {
                    Toast.makeText(getApplicationContext(),"Something went wrong: ",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.w(TAG, "Failed: "+t.getMessage());

            }
        });
    }



}
