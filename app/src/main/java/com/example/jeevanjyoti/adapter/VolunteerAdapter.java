package com.example.jeevanjyoti.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jeevanjyoti.R;

public class VolunteerAdapter extends RecyclerView.Adapter<VolunteerAdapter.VolunteerViewHolder> {

    @NonNull
    @Override
    public VolunteerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.volunteer_details_layout,
                parent, false);
        return new VolunteerViewHolder(lView);
    }

    @Override
    public void onBindViewHolder(@NonNull VolunteerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class VolunteerViewHolder extends RecyclerView.ViewHolder {
        public VolunteerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
