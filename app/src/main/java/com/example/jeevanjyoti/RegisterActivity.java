package com.example.jeevanjyoti;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jeevanjyoti.customSpinner.CustomAdapter;
import com.example.jeevanjyoti.customSpinner.CustomItems;
import com.example.jeevanjyoti.registerencapsulation.UserRegister;
import com.example.jeevanjyoti.retrofit.RetrofitClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "RegisterActivity";
    private DatePickerDialog.OnDateSetListener mDateListener;
    private Spinner mMaritalSpinner, mEducationSpinner, mOccupationSpinner, mStateSpinner,
            mDistrictSpinner, mGenderSpinner, mEducationStatusSpinner;
    private EditText mFullName, mFatherName, mMotherName, mMobileNumber, mOccupationEditText,
            mFlatEditText, mBuildingEditText, mAreaEditText, mPinCodeEditText;
    TextView mDOBTextView;
    Button mRegisterButton;
    ArrayList<CustomItems> mStateCustomList = new ArrayList<>();
    ArrayList<CustomItems> mGenderCustomList = new ArrayList<>();
    ArrayList<CustomItems> mMaritalcustomList = new ArrayList<>();
    ArrayList<CustomItems> mEducationCustomList = new ArrayList<>();
    ArrayList<CustomItems> mOccupationCustomList = new ArrayList<>();
    ArrayList<CustomItems> mEducationStatusList = new ArrayList<>();
    String mMaritalStatus, mEducation, mOccupation, mEducationStatus;
    private LinearLayout mMaleLinearLayout, mFemaleLinearLayout;
    private UserRegister mUserRegister = new UserRegister();
    private String mGender;
    private String[] mAPdistrict = {"Anantapur", "Chittoor","East Godavari", "Guntur", "Kadapa",
                                    "Krishna","Kurnool", "Prakasam", "Sri Potti Sriramulu Nellore",
                                    "Srikakulam", "Visakhapatnam", "Vizianagaram", "West Godavari"};
    private String[] mARpradesh = {	"Anjaw", "Changlang", "East Kameng", "East Siang", "Kamle",
                                    "Kra Daadi", "Kurung Kumey", "Lepa Rada", "Lohit", "Longding",
                                    "Lower Dibang Valley", "Lower Siang", "Lower Subansiri",
                                    "Namsai", "Pakke-Kessang", "Papum Pare", "Shi Yomi", "Siang",
                                    "Tawang", "Tirap", "Upper Dibang Valley", "Upper Siang",
                                    "Upper Subansiri"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        mDistrictSpinner = findViewById(R.id.district_spinner);
        mFullName = findViewById(R.id.full_name_edit_text);
        mFatherName = findViewById(R.id.father_name_edit_Text);
        mMotherName = findViewById(R.id.mother_name_edit_Text);
        mMobileNumber = findViewById(R.id.mobile_number_edit_text);
        mOccupationEditText = findViewById(R.id.occupation_edit_text);
        mFlatEditText = findViewById(R.id.flat_room_edit_text);
        mBuildingEditText = findViewById(R.id.building_edit_text);
        mAreaEditText = findViewById(R.id.area_edit_text);
        mPinCodeEditText = findViewById(R.id.pin_code_edit_text);
        mGenderSpinner = findViewById(R.id.gender_spinner);
        mDOBTextView = findViewById(R.id.dob_text_view);
        mRegisterButton = findViewById(R.id.register_button);
        mMaleLinearLayout = findViewById(R.id.male_linear_layout);
        mFemaleLinearLayout = findViewById(R.id.female_linear_layout);
        mEducationStatusSpinner = findViewById(R.id.education_status_spinner);
        getMariatalStatus();
        getEducation();
        getOccupationStatus();
        getState();
        getDistrict();
        registerUser();
        getDob();
        getGender();
        getEducationStatus();
//        Log.w(TAG,"Get Gender: "+getGender());
    }

    public void getGender(){
        mGenderCustomList.add(new CustomItems("Select Gender"));
        mGenderCustomList.add(new CustomItems("Male"));
        mGenderCustomList.add(new CustomItems("Female"));


        // create Adapter for spinner
        CustomAdapter customAdapter = new CustomAdapter(this, mGenderCustomList);

        if (mGenderCustomList != null) {
            mGenderSpinner.setAdapter(customAdapter);
            mGenderSpinner.setOnItemSelectedListener(this);
        }

    }

    public void getEducationStatus(){
        mEducationStatusList.add(new CustomItems("Education Status"));
        mEducationStatusList.add(new CustomItems("Pursue"));
        mEducationStatusList.add(new CustomItems("Completed"));


        // create Adapter for spinner
        CustomAdapter customAdapter = new CustomAdapter(this, mEducationStatusList);

        if (mEducationStatusList != null) {
            mEducationStatusSpinner.setAdapter(customAdapter);
            mEducationStatusSpinner.setOnItemSelectedListener(this);
        }

    }

    public void getDob(){
        mDOBTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar lCal = Calendar.getInstance();
                int lYear = lCal.get(Calendar.YEAR);
                int lMonth = lCal.get(Calendar.MONTH);
                int lDay = lCal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog lDialog = new DatePickerDialog(
                        RegisterActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateListener,
                        lYear, lMonth, lDay);
                lDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                lDialog.show();
            }
        });

        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int aYear, int aMonth, int aDay) {
                Log.w("MainActivity", "DatePicker: "+aYear+ "/" +aMonth + "/" +aDay);
                aMonth = aMonth + 1;
                String lString = aYear + "/" + aMonth + "/" + aDay;
                mDOBTextView.setText(lString);
            }
        };

    }
    public void getMariatalStatus(){
        mMaritalSpinner = findViewById(R.id.marital_spinner);

        mMaritalcustomList.add(new CustomItems("Marital Status"));
        mMaritalcustomList.add(new CustomItems("Single"));
        mMaritalcustomList.add(new CustomItems("Married"));
        mMaritalcustomList.add(new CustomItems("Divorced"));
        mMaritalcustomList.add(new CustomItems("widow(er)"));


        // create Adapter for spinner
        CustomAdapter customAdapter = new CustomAdapter(this, mMaritalcustomList);

        if (mMaritalSpinner != null) {
            mMaritalSpinner.setAdapter(customAdapter);
            mMaritalSpinner.setOnItemSelectedListener(this);
        }

    }

    public void getEducation(){
        mEducationSpinner = findViewById(R.id.eucation_spinner);

        mEducationCustomList.add(new CustomItems("Qualification"));
        mEducationCustomList.add(new CustomItems("SSC"));
        mEducationCustomList.add(new CustomItems("HSC"));
        mEducationCustomList.add(new CustomItems("Graduation"));
        mEducationCustomList.add(new CustomItems("Post Graduation"));

        // create Adapter for spinner
        CustomAdapter customAdapter = new CustomAdapter(this, mEducationCustomList);

        if (mEducationSpinner != null) {
            mEducationSpinner.setAdapter(customAdapter);
            mEducationSpinner.setOnItemSelectedListener(this);
        }

    }

    public void getOccupationStatus(){
        mOccupationSpinner = findViewById(R.id.occupation_spinner);
        mOccupationCustomList.add(new CustomItems("Occupation"));
        mOccupationCustomList.add(new CustomItems("Private job"));
        mOccupationCustomList.add(new CustomItems("Govt job"));
        mOccupationCustomList.add(new CustomItems("House Wife"));
        mOccupationCustomList.add(new CustomItems("Business"));
        mOccupationCustomList.add(new CustomItems("Retire"));
        mOccupationCustomList.add(new CustomItems("Leader"));
        mOccupationCustomList.add(new CustomItems("Student"));
        mOccupationCustomList.add(new CustomItems("Other"));

        // create Adapter for spinner
        CustomAdapter customAdapter = new CustomAdapter(this, mOccupationCustomList);

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

        switch (adapterView.getId()){
            case R.id.marital_spinner:
                mMaritalStatus = mMaritalcustomList.get(aPosition).getSpinnerText();
                break;
            case R.id.eucation_spinner:
                mEducation = mEducationCustomList.get(aPosition).getSpinnerText();
                break;
            case R.id.occupation_spinner:
                mOccupation = mOccupationCustomList.get(aPosition).getSpinnerText();
                break;
            case R.id.gender_spinner:
                mGender = mGenderCustomList.get(aPosition).getSpinnerText();
                break;
            case R.id.education_status_spinner:
                mEducationStatus = mEducationStatusList.get(aPosition).getSpinnerText();
                break;
            default:
                break;
        }





    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }

    public void registerUser(){
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lFullNameString = mFullName.getText().toString();
                if (lFullNameString.isEmpty()){
                    mFullName.setError("Please fill the box");
                    mFullName.requestFocus();
                }
                String lFatherNameString = mFatherName.getText().toString();
                if(lFatherNameString.isEmpty()){
                    mFatherName.setError("Please fill the box");
                    mFatherName.requestFocus();
                }
                String lMotherNameString = mMotherName.getText().toString();
                if(lMotherNameString.isEmpty()){
                    mMotherName.setError("Please fill the box");
                    mMotherName.requestFocus();
                }
                String lMobileNumberString = mMobileNumber.getText().toString();
                if (lMobileNumberString.length() == 0){
                    mMobileNumber.setError("Please fill the box");
                    mMobileNumber.requestFocus();
                }
                String lDOBString = mDOBTextView.getText().toString();
                if (lDOBString.isEmpty()){
                    mDOBTextView.setError("Please Set DOB");
                    mDOBTextView.requestFocus();
                }else{
                    mDOBTextView.setError(null);
                }
                String lOccupationString = mOccupationEditText.getText().toString();
                if (lOccupationString.isEmpty()){
                    mOccupationEditText.setError("Please Fill Box");
                    mOccupationEditText.requestFocus();
                }
                String lFlatString = mFlatEditText.getText().toString();
                if(lFlatString.isEmpty()){
                    mFlatEditText.setError("Please fill the box");
                    mFlatEditText.requestFocus();
                }
                String lBuildingString = mBuildingEditText.getText().toString();
                if (lBuildingString.isEmpty()) {
                    mBuildingEditText.setError("Please fill the box");
                    mBuildingEditText.requestFocus();
                }
                String lAreaString = mAreaEditText.getText().toString();
                if (lAreaString.isEmpty()){
                    mAreaEditText.setError("Please fill the box");
                    mAreaEditText.requestFocus();
                }
                String lPinCodeString = mPinCodeEditText.getText().toString();
                if (lPinCodeString.isEmpty()){
                    mPinCodeEditText.setError("Please fill the box");
                    mPinCodeEditText.requestFocus();
                }
                Log.w(TAG,"Selected Spinner: "+mMaritalSpinner.getAdapter().toString());
                UserRegisterApi lUserRegisterApi = RetrofitClient.postUserdata();
                Call<UserRegister> lCallUserResponse = lUserRegisterApi.sendUserData(
                        "8",
                        lFullNameString,
                        lFatherNameString,
                        lMotherNameString,
                        lMobileNumberString,
                        mGender,
                        lDOBString,
                        mMaritalStatus,
                        mEducation,
                        mEducationStatus,
                        mOccupation,
                        lOccupationString,
                        lFlatString,
                        lBuildingString,
                        lAreaString,
                        lPinCodeString,
                        "Karnatak",
                        "Bangalore");

                lCallUserResponse.enqueue(new Callback<UserRegister>() {
                    @Override
                    public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {
                        Log.w("RegisterActivity", "Response: "+response);
                    }

                    @Override
                    public void onFailure(Call<UserRegister> call, Throwable t) {
                        Log.w("RegisterActivity", "Response Failed: "+t.toString() + call.toString());
                    }
                });
            }
        });
    }

}
