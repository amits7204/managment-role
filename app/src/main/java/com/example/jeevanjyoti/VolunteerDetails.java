package com.example.jeevanjyoti;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.airbnb.lottie.LottieAnimationView;
import com.example.jeevanjyoti.adapter.UserAdapter;
import com.example.jeevanjyoti.adapter.VolunteerAdapter;
import com.example.jeevanjyoti.retrofit.RetrofitClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;
import com.example.jeevanjyoti.userPojo.UserRoot;
import com.example.jeevanjyoti.volunteerPojo.VolunteerRoot;

import org.json.JSONObject;

import java.util.Collection;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolunteerDetails extends AppCompatActivity {
    public static final String TAG = "VolunteerDetails";
    VolunteerAdapter mVolunteerAdapter;
    RecyclerView mRecyclerView;
    private LottieAnimationView mAnimationView;
    private VolunteerRoot mVolunteerRoot = new VolunteerRoot();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volunteer_details_recycler_view);
       mRecyclerView = findViewById(R.id.volunteer_recylerview);
       mAnimationView = findViewById(R.id.volunteer_animation_view);
       getUserData();

    }

    public void getUserData(){
        mAnimationView.playAnimation();
        UserRegisterApi lUserDataApi = RetrofitClient.postUserdata();
        Call<VolunteerRoot> lJsonObject = lUserDataApi.fetchVolunteerData();
        lJsonObject.enqueue(new Callback<VolunteerRoot>() {
            @Override
            public void onResponse(Call<VolunteerRoot> call, Response<VolunteerRoot> response) {
                if (response.isSuccessful()){
                    Log.w(TAG,"Get User Response: "+response.body());
                    mVolunteerRoot = response.body();
                    mVolunteerAdapter = new VolunteerAdapter(getApplicationContext(), mVolunteerRoot);
                    LinearLayoutManager lLinearLayOutManager = new LinearLayoutManager(getApplicationContext());
                    mRecyclerView.setLayoutManager(lLinearLayOutManager);
                    mRecyclerView.setAdapter(mVolunteerAdapter);
                    mAnimationView.cancelAnimation();
                    mAnimationView.setVisibility(View.GONE);
                }else{
                    Toast.makeText(getApplicationContext(),"Something is wrong", Toast.LENGTH_SHORT).show();
                    mAnimationView.cancelAnimation();
                    mAnimationView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<VolunteerRoot> call, Throwable t) {
//                mUserProgressBar.setVisibility(View.GONE);
                mAnimationView.cancelAnimation();
            }
        });
    }
}
