package com.example.jeevanjyoti;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jeevanjyoti.customSpinner.CustomAdapter;
import com.example.jeevanjyoti.customSpinner.CustomItems;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "RegisterActivity";
    private Spinner mMaritalSpinner, mEducationSpinner, mOccupationSpinner, mStateSpinner, mDistrictSpinner;
    ArrayList<CustomItems> mStateCustomList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        mDistrictSpinner = findViewById(R.id.district_spinner);

        getMariatalStatus();
        getEducationStatus();
        getOccupationStatus();
        getState();
        getDistrict();
    }

    public void getMariatalStatus(){
        mMaritalSpinner = findViewById(R.id.marital_spinner);
        ArrayList<CustomItems> customList = new ArrayList<>();
        customList.add(new CustomItems("Marital Status", R.drawable.ic_relation_ship_status));
        customList.add(new CustomItems("Single", R.drawable.ic_single));
        customList.add(new CustomItems("Married", R.drawable.ic_married));

        // create Adapter for spinner
        CustomAdapter customAdapter = new CustomAdapter(this, customList);

        if (mMaritalSpinner != null) {
            mMaritalSpinner.setAdapter(customAdapter);
            mMaritalSpinner.setOnItemSelectedListener(this);
        }

    }

    public void getEducationStatus(){
        mEducationSpinner = findViewById(R.id.eucation_spinner);
        ArrayList<CustomItems> customList = new ArrayList<>();
        customList.add(new CustomItems("Qualification", R.drawable.ic_relation_ship_status));
        customList.add(new CustomItems("SSC", R.drawable.ic_single));
        customList.add(new CustomItems("HSC", R.drawable.ic_married));
        customList.add(new CustomItems("Graduation", R.drawable.ic_married));
        customList.add(new CustomItems("Post Graduation", R.drawable.ic_married));

        // create Adapter for spinner
        CustomAdapter customAdapter = new CustomAdapter(this, customList);

        if (mEducationSpinner != null) {
            mEducationSpinner.setAdapter(customAdapter);
            mEducationSpinner.setOnItemSelectedListener(this);
        }

    }

    public void getOccupationStatus(){
        mOccupationSpinner = findViewById(R.id.occupation_spinner);
        ArrayList<CustomItems> customList = new ArrayList<>();
        customList.add(new CustomItems("Occupation", R.drawable.ic_relation_ship_status));
        customList.add(new CustomItems("SSC", R.drawable.ic_single));
        customList.add(new CustomItems("HSC", R.drawable.ic_married));
        customList.add(new CustomItems("Graduation", R.drawable.ic_married));
        customList.add(new CustomItems("Post Graduation", R.drawable.ic_married));

        // create Adapter for spinner
        CustomAdapter customAdapter = new CustomAdapter(this, customList);

        if (mOccupationSpinner != null) {
            mOccupationSpinner.setAdapter(customAdapter);
            mOccupationSpinner.setOnItemSelectedListener(this);
        }

    }

    public void getState(){
        mStateSpinner = findViewById(R.id.state_spinner);
        mStateCustomList.add(new CustomItems("Select State"));
        mStateCustomList.add(new CustomItems("Andhra Pradesh"));
        mStateCustomList.add(new CustomItems("Arunachal Pradesh"));
        mStateCustomList.add(new CustomItems("Assam"));
        mStateCustomList.add(new CustomItems("Bihar"));
        mStateCustomList.add(new CustomItems("Chhattisgarh"));
        mStateCustomList.add(new CustomItems("Goa"));
        mStateCustomList.add(new CustomItems("Gujarat"));
        mStateCustomList.add(new CustomItems("Haryana"));
        mStateCustomList.add(new CustomItems("Himachal Pradesh"));
        mStateCustomList.add(new CustomItems("Jammu and kashmir"));
        mStateCustomList.add(new CustomItems("Ladakh"));
        mStateCustomList.add(new CustomItems("Jharkhand"));
        mStateCustomList.add(new CustomItems("Karnataka"));
        mStateCustomList.add(new CustomItems("Kerala"));
        mStateCustomList.add(new CustomItems("Madhya Pradesh"));
        mStateCustomList.add(new CustomItems("Manipur"));
        mStateCustomList.add(new CustomItems("Meghalaya"));
        mStateCustomList.add(new CustomItems("Mizoram"));
        mStateCustomList.add(new CustomItems("Nagaland"));
        mStateCustomList.add(new CustomItems("Odisha"));
        mStateCustomList.add(new CustomItems("Punjab"));
        mStateCustomList.add(new CustomItems("Rajasthan"));
        mStateCustomList.add(new CustomItems("Sikkim"));
        mStateCustomList.add(new CustomItems("Tamil Nadu"));
        mStateCustomList.add(new CustomItems("Telangana"));
        mStateCustomList.add(new CustomItems("Uttarakhand"));
        mStateCustomList.add(new CustomItems("Uttar Pradesh"));
        mStateCustomList.add(new CustomItems("West Bengal"));


        // create Adapter for spinner
        CustomAdapter customAdapter = new CustomAdapter(this, mStateCustomList);

        if (mStateSpinner != null) {
            mStateSpinner.setAdapter(customAdapter);
            mStateSpinner.setOnItemSelectedListener(this);
        }
    }

    public void getDistrict(){
        mStateCustomList.add(new CustomItems("Select District"));
        mStateCustomList.add(new CustomItems("Jaunpur"));
        mStateCustomList.add(new CustomItems("Arunachal Pradesh"));
        mStateCustomList.add(new CustomItems("Assam"));
        mStateCustomList.add(new CustomItems("Bihar"));
        mStateCustomList.add(new CustomItems("Chhattisgarh"));
        mStateCustomList.add(new CustomItems("Goa"));
        mStateCustomList.add(new CustomItems("Gujarat"));
        mStateCustomList.add(new CustomItems("Haryana"));
        mStateCustomList.add(new CustomItems("Himachal Pradesh"));
        mStateCustomList.add(new CustomItems("Jammu and kashmir"));
        mStateCustomList.add(new CustomItems("Ladakh"));
        mStateCustomList.add(new CustomItems("Jharkhand"));
        mStateCustomList.add(new CustomItems("Karnataka"));
        mStateCustomList.add(new CustomItems("Kerala"));
        mStateCustomList.add(new CustomItems("Madhya Pradesh"));
        mStateCustomList.add(new CustomItems("Manipur"));
        mStateCustomList.add(new CustomItems("Meghalaya"));
        mStateCustomList.add(new CustomItems("Mizoram"));
        mStateCustomList.add(new CustomItems("Nagaland"));
        mStateCustomList.add(new CustomItems("Odisha"));
        mStateCustomList.add(new CustomItems("Punjab"));
        mStateCustomList.add(new CustomItems("Rajasthan"));
        mStateCustomList.add(new CustomItems("Sikkim"));
        mStateCustomList.add(new CustomItems("Tamil Nadu"));
        mStateCustomList.add(new CustomItems("Telangana"));
        mStateCustomList.add(new CustomItems("Uttarakhand"));
        mStateCustomList.add(new CustomItems("Uttar Pradesh"));
        mStateCustomList.add(new CustomItems("West Bengal"));


        // create Adapter for spinner
        CustomAdapter customAdapter = new CustomAdapter(this, mStateCustomList);

        if (mDistrictSpinner != null) {
            mDistrictSpinner.setAdapter(customAdapter);
            customAdapter.notifyDataSetChanged();
            mDistrictSpinner.setOnItemSelectedListener(this);
         }
    }

    public void getDistrict1(){

        mStateCustomList.add(new CustomItems("Select District"));
        mStateCustomList.add(new CustomItems("Sultanpur"));
        mStateCustomList.add(new CustomItems("Arunachal Pradesh"));
        mStateCustomList.add(new CustomItems("Assam"));
        mStateCustomList.add(new CustomItems("Bihar"));
        mStateCustomList.add(new CustomItems("Chhattisgarh"));
        mStateCustomList.add(new CustomItems("Goa"));
        mStateCustomList.add(new CustomItems("Gujarat"));
        mStateCustomList.add(new CustomItems("Haryana"));
        mStateCustomList.add(new CustomItems("Himachal Pradesh"));
        mStateCustomList.add(new CustomItems("Jammu and kashmir"));
        mStateCustomList.add(new CustomItems("Ladakh"));
        mStateCustomList.add(new CustomItems("Jharkhand"));
        mStateCustomList.add(new CustomItems("Karnataka"));
        mStateCustomList.add(new CustomItems("Kerala"));
        mStateCustomList.add(new CustomItems("Madhya Pradesh"));
        mStateCustomList.add(new CustomItems("Manipur"));
        mStateCustomList.add(new CustomItems("Meghalaya"));
        mStateCustomList.add(new CustomItems("Mizoram"));
        mStateCustomList.add(new CustomItems("Nagaland"));
        mStateCustomList.add(new CustomItems("Odisha"));
        mStateCustomList.add(new CustomItems("Punjab"));
        mStateCustomList.add(new CustomItems("Rajasthan"));
        mStateCustomList.add(new CustomItems("Sikkim"));
        mStateCustomList.add(new CustomItems("Tamil Nadu"));
        mStateCustomList.add(new CustomItems("Telangana"));
        mStateCustomList.add(new CustomItems("Uttarakhand"));
        mStateCustomList.add(new CustomItems("Uttar Pradesh"));
        mStateCustomList.add(new CustomItems("West Bengal"));


        // create Adapter for spinner
        CustomAdapter customAdapter = new CustomAdapter(this, mStateCustomList);

        if (mDistrictSpinner != null) {
            mDistrictSpinner.setAdapter(customAdapter);
            customAdapter.notifyDataSetChanged();
            mDistrictSpinner.setOnItemSelectedListener(this);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int aPosition, long l) {

            // Showing selected spinner item
//        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
        String lStateString = String.valueOf(mStateSpinner.getSelectedItemPosition());

        Log.w(TAG,"Get State: "+lStateString);
        if(lStateString.contentEquals("1")){
            getDistrict();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
