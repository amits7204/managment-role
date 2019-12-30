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

public class UUIDActivity extends AppCompatActivity {
    EditText mUniqueEditText;
    Button mSubmitButton;
    String mUuid;
    private static final String TAG = "UUIDActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uuid_layout);
        mUniqueEditText = findViewById(R.id.v_verify_unique_id);
        ImageView lBackImage = findViewById(R.id.back_button);
        mSubmitButton = findViewById(R.id.submit_uuid_number);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUuid();
            }
        });
        lBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void registerUuid(){

        mUuid = mUniqueEditText.getText().toString();
        Log.w(TAG,"UUID: "+mUuid);
        Map<String, String> lUuidNumber = new HashMap<>();
        lUuidNumber.put("uuid", mUuid);
        Log.w(TAG,"Register UUID: "+mUuid);
        UserRegisterApi lApi = RetrofitUserClient.userdata();
        Call<ResponseBody> lJsonObject = lApi.sendOtp(lUuidNumber);
        lJsonObject.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    Log.w(TAG, "Response: " + response.body().toString());
                    Intent lIntent = new Intent(UUIDActivity.this, UUIDRegisterOtp.class);
                    lIntent.putExtra("uuid", mUuid);
                    lIntent.putExtra("no", "no");
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
