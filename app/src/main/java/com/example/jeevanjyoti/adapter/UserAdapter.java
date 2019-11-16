package com.example.jeevanjyoti.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jeevanjyoti.R;
import com.example.jeevanjyoti.retrofit.RetrofitClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;
import com.example.jeevanjyoti.userPojo.UserRoot;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserAdapterViewHolder>{

    private Context mContext;
    private UserRoot mUserRoot;
    public UserAdapter(Context aContext, UserRoot aUserRoot){
        mContext = aContext;
        mUserRoot = aUserRoot;
    }
    @NonNull
    @Override
    public UserAdapter.UserAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_detail_layout,
                parent, false);
        return new UserAdapter.UserAdapterViewHolder(lView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserAdapterViewHolder aVieHolder, int aPosition) {
        aVieHolder.mFullName.setText(mUserRoot.getData().get(aPosition).getName());
        aVieHolder.mFatherName.setText(mUserRoot.getData().get(aPosition).getFather_husband_name());
        aVieHolder.mMotherName.setText(mUserRoot.getData().get(aPosition).getMother_name());
        aVieHolder.mMobileNumber.setText(mUserRoot.getData().get(aPosition).getMobile());
        aVieHolder.mGender.setText(mUserRoot.getData().get(aPosition).getGender());
        aVieHolder.mDOB.setText(mUserRoot.getData().get(aPosition).getDob());
        aVieHolder.mMaritalStatus.setText(mUserRoot.getData().get(aPosition).getMarital_status());
        aVieHolder.mQualificationStatus.setText(mUserRoot.getData().get(aPosition).getEducation());
        aVieHolder.mEducationStatus.setText(mUserRoot.getData().get(aPosition).getEducation_status());
        aVieHolder.mOccupation.setText(mUserRoot.getData().get(aPosition).getOccupation());
        aVieHolder.mOccupationDescription.setText(mUserRoot.getData().get(aPosition).getOccupation_description());
        aVieHolder.mFlat.setText(mUserRoot.getData().get(aPosition).getFlat_room_block_no());
        aVieHolder.mBuilding.setText(mUserRoot.getData().get(aPosition).getPremises_building_villa());
        Log.w("UserAdapter","Check RoadLane: "+mUserRoot.getData().get(aPosition).getRoad_street_lane());
        aVieHolder.mRoadStreet.setText(mUserRoot.getData().get(aPosition).getRoad_street_lane());
        aVieHolder.mArea.setText(mUserRoot.getData().get(aPosition).getArea_locality_taluk());
        aVieHolder.mPinCode.setText(mUserRoot.getData().get(aPosition).getPin_code());
        aVieHolder.mState.setText(mUserRoot.getData().get(aPosition).getState());
        aVieHolder.mDistrict.setText(mUserRoot.getData().get(aPosition).getDistrict());
    }

    @Override
    public int getItemCount() {
        return mUserRoot.getData().size();
    }

    public class UserAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView mFullName, mFatherName, mMotherName, mMobileNumber, mGender, mDOB,
        mMaritalStatus, mQualificationStatus, mEducationStatus, mOccupation, mOccupationDescription,
        mFlat, mBuilding, mArea, mPinCode, mState, mDistrict, mRoadStreet;
        ImageView mDownloadImage;
        public UserAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mFullName = itemView.findViewById(R.id.full_name);
            mFatherName = itemView.findViewById(R.id.father_name);
            mMotherName = itemView.findViewById(R.id.mother_name);
            mMobileNumber = itemView.findViewById(R.id.mobile_text_view);
            mGender = itemView.findViewById(R.id.gender_text_view);
            mDOB = itemView.findViewById(R.id.dob_text_view);
            mMaritalStatus = itemView.findViewById(R.id.marital_status_text_view);
            mQualificationStatus = itemView.findViewById(R.id.qulification_text_view);
            mEducationStatus = itemView.findViewById(R.id.education_status_text_view);
            mOccupation = itemView.findViewById(R.id.occupation_text_view);
            mOccupationDescription = itemView.findViewById(R.id.occupation_description_text_view);
            mFlat = itemView.findViewById(R.id.flat_room_text_view);
            mBuilding = itemView.findViewById(R.id.building_villege_text_view);
            mRoadStreet = itemView.findViewById(R.id.road_lane_text_view);
            mArea = itemView.findViewById(R.id.area_locality_text_view);
            mPinCode = itemView.findViewById(R.id.pin_code_text_view);
            mState = itemView.findViewById(R.id.state_text_view);
            mDistrict = itemView.findViewById(R.id.district_text_view);



        }
    }
}
