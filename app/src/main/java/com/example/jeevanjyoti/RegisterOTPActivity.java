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

import com.example.jeevanjyoti.otpPojo.OtpRoot;
import com.example.jeevanjyoti.registerencapsulation.UserRegister;
import com.example.jeevanjyoti.retrofit.RetrofitClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterOTPActivity extends AppCompatActivity {
    private static final String TAG = "RegisterOTPActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_otp_layout);
        final EditText lOtpEditText = findViewById(R.id.r_otp_edit_text);
        Button lVerifyButton = findViewById(R.id.r_button);
        lVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lOtpString = lOtpEditText.getText().toString();
                Log.w(TAG,"GET OTP: "+lOtpString);
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
                                Intent lIntent = new Intent(RegisterOTPActivity.this, UserConfermActivity.class);
                                startActivity(lIntent);
                            }else {

                                lOtpEditText.setError("Please Fill correct OTP");
                                lOtpEditText.requestFocus();
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
        });
    }
}
