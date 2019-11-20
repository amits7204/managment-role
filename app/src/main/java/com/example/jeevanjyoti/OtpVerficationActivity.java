package com.example.jeevanjyoti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jeevanjyoti.retrofit.AdminOtpVerify;
import com.example.jeevanjyoti.retrofit.RetrofitClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerficationActivity extends AppCompatActivity {
    private String mOtpString;
    private EditText mOtpEditText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verification_activity);
        ImageView lLogoImageView = findViewById(R.id.logo_imageview);
        mOtpEditText = findViewById(R.id.otp_editText);

        Button lButton = findViewById(R.id.verify_button);

        lButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOtpString = mOtpEditText.getText().toString();
                Log.w("kjsdfjklgm", "kjdfjfkljflkj: "+mOtpString);
                if(mOtpString.isEmpty()){
                    mOtpEditText.setError("Please Fill the right Otp");
                    mOtpEditText.requestFocus();
                }
                getVerifyOtp(mOtpString);

            }
        });

    }

    public void getVerifyOtp(String aOtpString){
        Intent lIntent = getIntent();
        final String lMobileString = lIntent.getStringExtra("vmobile");
        final String MY_PREFS_NAME = "MyPrefsFile";
        final SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        UserRegisterApi lUserRegisterApi = RetrofitClient.postUserdata();
        Call<AdminOtpVerify> lOtpCall = lUserRegisterApi.verifyVolunteerOtp(aOtpString);
        lOtpCall.enqueue(new Callback<AdminOtpVerify>() {
            @Override
            public void onResponse(Call<AdminOtpVerify> call, Response<AdminOtpVerify> response) {

                if (response.isSuccessful()){
                    Log.w("OTPVERIFY","RESPONSE OTP: "+response);
                    editor.putString("vmobile", lMobileString);
                    editor.apply();
                    Intent lIntent = new Intent(OtpVerficationActivity.this,VolunteerDashBoard.class);
                    startActivity(lIntent);
                }else{
                    Toast.makeText(getApplicationContext(),"Something is Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AdminOtpVerify> call, Throwable t) {

            }
        });
    }
}
