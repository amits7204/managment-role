package com.example.jeevanjyoti.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jeevanjyoti.R;
import com.example.jeevanjyoti.volunteerPojo.VolunteerRoot;

public class VolunteerAdapter extends RecyclerView.Adapter<VolunteerAdapter.VolunteerViewHolder> {
    public static final String TAG = "VolunteerAdapter";
    VolunteerRoot mVolunteerRoot;
    private Context mContext;

    public VolunteerAdapter(Context aContext, VolunteerRoot aVolunteerRoot){
        mContext = aContext;
        mVolunteerRoot = aVolunteerRoot;
    }

    @NonNull
    @Override
    public VolunteerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.volunteer_details_layout,
                parent, false);
        return new VolunteerViewHolder(lView);
    }

    @Override
    public void onBindViewHolder(@NonNull VolunteerViewHolder aViewholder, int position) {
        Glide.with(mContext)
                .load(mVolunteerRoot.getData().get(position).getImage_url())
                .centerCrop()
                .into(aViewholder.mImageView);
        Log.w(TAG,"IMAGE URL: "+mVolunteerRoot.getData().get(position).getImage_url());
        aViewholder.mFullName.setText(mVolunteerRoot.getData().get(position).getName());
        aViewholder.mFatherName.setText(mVolunteerRoot.getData().get(position).getFathername());
        aViewholder.mDob.setText(mVolunteerRoot.getData().get(position).getDateofbirth());
        aViewholder.mGender.setText(mVolunteerRoot.getData().get(position).getGender());
        aViewholder.mMobileNumber.setText(mVolunteerRoot.getData().get(position).getMobile());
        aViewholder.mFullAddress.setText(mVolunteerRoot.getData().get(position).getFlat_room_block_no()+
                " "+mVolunteerRoot.getData().get(position).getPremises_building_villa()+" "+
                mVolunteerRoot.getData().get(position).getRoad_street_lane()+" "+
                mVolunteerRoot.getData().get(position).getArea_locality_taluk()+" "+
                mVolunteerRoot.getData().get(position).getPin_code()+" "+
                mVolunteerRoot.getData().get(position).getState()+" "+
                mVolunteerRoot.getData().get(position).getDistrict());
    }

    @Override
    public int getItemCount() {
        return mVolunteerRoot.getData().size();
    }

    class VolunteerViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mFullName, mGender, mFullAddress, mMobileNumber, mFatherName, mDob;
        VolunteerViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.profile_imageview);
            mFatherName = itemView.findViewById(R.id.father_name_text_view);
            mDob = itemView.findViewById(R.id.v_dob_text_view);
            mFullName = itemView.findViewById(R.id.full_name_text_view);
            mGender = itemView.findViewById(R.id.gender_text_view);
            mMobileNumber = itemView.findViewById(R.id.mobile_text_view);
            mFullAddress = itemView.findViewById(R.id.full_address_text_view);
        }
    }
}
