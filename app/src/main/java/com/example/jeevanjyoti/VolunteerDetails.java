package com.example.jeevanjyoti;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.jeevanjyoti.adapter.VolunteerAdapter;

public class VolunteerDetails extends AppCompatActivity {
    VolunteerAdapter mVolunteerAdapter = new VolunteerAdapter();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volunteer_details_recycler_view);
        RecyclerView lRecyclerView = findViewById(R.id.volunteer_recylerview);
        LinearLayoutManager lLinearLayOutManager = new LinearLayoutManager(getApplicationContext());
        lRecyclerView.setLayoutManager(lLinearLayOutManager);
        lRecyclerView.setAdapter(mVolunteerAdapter);

    }
}
