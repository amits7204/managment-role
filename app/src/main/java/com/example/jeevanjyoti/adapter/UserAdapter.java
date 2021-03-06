package com.example.jeevanjyoti.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.ArrayList;
import java.util.List;

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
    public void onBindViewHolder(@NonNull UserAdapterViewHolder aVieHolder, final int aPosition) {
        if (mUserRoot.getJUser().get(aPosition).getParent().equals("yes")) {
            aVieHolder.mFullName.setText(mUserRoot.getJUser().get(aPosition).getFull_name());
                aVieHolder.mFullName.setTextColor(Color.YELLOW);
            }else if(mUserRoot.getJUser().get(aPosition).getParent().equals("no")){
                aVieHolder.mFullName.setText(mUserRoot.getJUser().get(aPosition).getFull_name());
                aVieHolder.mFullName.setTextColor(Color.WHITE);
        }
        aVieHolder.mUniqueId.setText(mUserRoot.getJUser().get(aPosition).getUuid());
        aVieHolder.mFatherName.setText(mUserRoot.getJUser().get(aPosition).getFather_name());
        aVieHolder.mMotherName.setText(mUserRoot.getJUser().get(aPosition).getMother_name());
        aVieHolder.mMobileNumber.setText(mUserRoot.getJUser().get(aPosition).getMobile_no());
        aVieHolder.mGender.setText(mUserRoot.getJUser().get(aPosition).getGender());
        aVieHolder.mDOB.setText(mUserRoot.getJUser().get(aPosition).getDob());
        aVieHolder.mMaritalStatus.setText(mUserRoot.getJUser().get(aPosition).getMarital());
        aVieHolder.mQualificationStatus.setText(mUserRoot.getJUser().get(aPosition).getEducation());
        aVieHolder.mEducationStatus.setText(mUserRoot.getJUser().get(aPosition).getEducation_status());
        aVieHolder.mEdudisc.setText(mUserRoot.getJUser().get(aPosition).getEducation_disc());
        aVieHolder.mOccupation.setText(mUserRoot.getJUser().get(aPosition).getOccupation());
        aVieHolder.mOccupationDescription.setText(mUserRoot.getJUser().get(aPosition).getOccupation_disc());
        aVieHolder.mArea.setText(mUserRoot.getJUser().get(aPosition).getFlat_no()+" "+
                mUserRoot.getJUser().get(aPosition).getBuilding_no()+" "+
                mUserRoot.getJUser().get(aPosition).getRoad_street()+" "+
                mUserRoot.getJUser().get(aPosition).getPin_code()+" "+
                mUserRoot.getJUser().get(aPosition).getState()+" "+
                mUserRoot.getJUser().get(aPosition).getDistrict());

        aVieHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("UserAdapter", "OnClick: "+mUserRoot.getJUser().get(aPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserRoot.getJUser().size();
    }

    class UserAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView mFullName, mFatherName, mMotherName, mMobileNumber, mGender, mDOB,
        mMaritalStatus, mQualificationStatus, mEducationStatus, mOccupation, mOccupationDescription,
        mArea, mUniqueId, mEdudisc;
        LinearLayout mLinearLayout;
        UserAdapterViewHolder(@NonNull View itemView) {
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
            mArea = itemView.findViewById(R.id.area_locality_text_view);
            mUniqueId = itemView.findViewById(R.id.d_unique_id);
            mEdudisc = itemView.findViewById(R.id.education_disc_text_view);
            mLinearLayout = itemView.findViewById(R.id.linear_layout_position);




        }
    }
}
