package com.example.jeevanjyoti;

import android.content.Context;
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

import com.example.jeevanjyoti.otpPojo.OtpRoot;
import com.example.jeevanjyoti.retrofit.AdminOtpVerify;
import com.example.jeevanjyoti.retrofit.RetrofitClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerficationActivity extends AppCompatActivity {
    private static final String TAG = "OtpVerficationActivity";
    private String mOtpString;
    private EditText mOtpEditText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verification_activity);
        ImageView lLogoImageView = findViewById(R.id.logo_imageview);
        mOtpEditText = findViewById(R.id.otp_editText);

        Button lButton = findViewById(R.id.verify_button);
        final String MyPREFERENCES = "MyPrefs" ;
        SharedPreferences lSp = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String lGenderValue = lSp.getString("gender", "");

        Log.w(TAG,"Check Gender Value: "+lGenderValue);
        if (lGenderValue.equals("Female")){
            lButton.setBackground(getDrawable(R.drawable.female_rounded_box));
        }
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
        Call<OtpRoot> lOtpCall = lUserRegisterApi.verifyVolunteerOtp(aOtpString);
        lOtpCall.enqueue(new Callback<OtpRoot>() {
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
                        Intent lIntent = new Intent(OtpVerficationActivity.this, VolunteerDashBoard.class);
                        startActivity(lIntent);
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
}
