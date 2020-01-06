package com.example.jeevanjyoti;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.jeevanjyoti.customSpinner.CustomAdapter;
import com.example.jeevanjyoti.customSpinner.CustomItems;
import com.example.jeevanjyoti.retrofit.RetrofitClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;
import com.example.jeevanjyoti.retrofit.Volunteer;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolunteerActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "VolunteerActivity";
    private ImageView mImageView, mMenImageView;
    private TextView mMaleTextView, mFemaleTextView;
    private EditText mNameEditText, mMobileNumberEditText, mFlatNo, mBuilding, mRoadStreet, mDobEdit,
    mArea, mPinCode, mFatherName;
    private Spinner mState, mDistrict;
    private Button mButton;
    private Uri mUri;
    private File mFile;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private DisplayMetrics mDisplayMetrics;
    private int mWidth, mHieght;
    private String mStateString, mDistrictString;
    LinearLayout mMaleLinearLayout, mFemaleLinearLayout;
    private View.OnClickListener mOnClickListener;
    private String mGender = "";
    public String mMediaPath = "";
    private ProgressDialog mProgressDialog;
    Volunteer mVolunteer = new Volunteer();
    String imageString = "";
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private FrameLayout mFrameLAyout;
    private ImageView mAddDrawable;
    ArrayList<CustomItems> mStateCustomList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volunteer_activity);
        mImageView = findViewById(R.id.profile_image);
        mMaleTextView = findViewById(R.id.male_textview);
        mDobEdit = findViewById(R.id.dob_edit_text);
        mFemaleTextView = findViewById(R.id.female_textView);
        mNameEditText = findViewById(R.id.name_edit_textview);
        mMobileNumberEditText = findViewById(R.id.mobile_edit_textview);
        mFrameLAyout = findViewById(R.id.address_framelayout);
        mAddDrawable = findViewById(R.id.add_drawable);
        mFatherName = findViewById(R.id.father_name_edit_textview);
        mButton = findViewById(R.id.button);
        mFlatNo = findViewById(R.id.v_flat_room_edit_text);
        mBuilding = findViewById(R.id.v_building_edit_text);
        mRoadStreet = findViewById(R.id.v_road_lane);
        mArea = findViewById(R.id.v_area_edit_text);
        mPinCode = findViewById(R.id.v_pin_code_edit_text);
        mState = findViewById(R.id.v_state_spinner);
        mDistrict = findViewById(R.id.v_district_spinner);
        mMaleLinearLayout = findViewById(R.id.male_linear_layout);
        mFemaleLinearLayout = findViewById(R.id.female_linear_layout);
        mMenImageView = findViewById(R.id.men_imageView);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Processing...");
        getDob();
        getState();
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Slect Gallery", Toast.LENGTH_SHORT).show();
          //      getImageFromeGallery();
//                launchGooglePhotosPicker(VolunteerActivity.this);
                selectImage();
            }
        });
        getGender();
        mMaleLinearLayout.setOnClickListener(mOnClickListener);
        mFemaleLinearLayout.setOnClickListener(mOnClickListener);

        mAddDrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFrameLAyout.setVisibility(View.VISIBLE);
            }
        });

        registerVolunteer();
    }
    public void getDob(){
        mDobEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar lCal = Calendar.getInstance();
                int lYear = lCal.get(Calendar.YEAR);
                int lMonth = lCal.get(Calendar.MONTH);
                int lDay = lCal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog lDialog = new DatePickerDialog(
                        VolunteerActivity.this,
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
                mDobEdit.setText(lString);
            }
        };

    }
    private void selectImage() {



        final CharSequence[] items = {"Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(VolunteerActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
               boolean result = Utility.checkPermission(VolunteerActivity.this);

                if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();


    }



    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
         intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }


    public void getGender(){
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.male_linear_layout:
                        mMaleLinearLayout.setBackground(getDrawable(R.drawable.about_us_circle));
                        mMaleTextView.setTextColor(getResources().getColor(R.color.colorPrimaryBlack));
                        mMenImageView.setImageDrawable(getDrawable(R.drawable.ic_men_black));
                        mAddDrawable.setImageDrawable(getDrawable(R.drawable.ic_add_circle));
                        mFemaleTextView.setTextColor(getResources().getColor(R.color.colorAccent));
                        mFemaleLinearLayout.setBackground(getDrawable(R.drawable.secondary_primary_box));
                        mButton.setBackground(getDrawable(R.drawable.about_us_circle));
                        mGender = mMaleTextView.getText().toString();
                        Log.w(TAG,"Gender Status: "+mGender);
                        break;
                    case R.id.female_linear_layout:
                        mFemaleLinearLayout.setBackground(getDrawable(R.drawable.female_rounded_box));
                        mFemaleTextView.setTextColor(getResources().getColor(R.color.colorPrimaryBlack));
                        mMaleTextView.setTextColor(getResources().getColor(R.color.colorAccent));
                        mMenImageView.setImageDrawable(getDrawable(R.drawable.ic_men));
                        mAddDrawable.setImageDrawable(getDrawable(R.drawable.ic_female_add_circle));
                        mMaleLinearLayout.setBackground(getDrawable(R.drawable.secondary_primary_box));
                        mButton.setBackground(getDrawable(R.drawable.female_rounded_box));
                        mGender = mFemaleTextView.getText().toString();
                        Log.w(TAG,"Gender Status: "+mGender);
                        break;
                }
            }
        };
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
                mUri = data.getData();
                Cursor cursor = getContentResolver().query(mUri, null, null, null, null);
                cursor.moveToFirst();
                String document_id = cursor.getString(0);
                document_id = document_id.substring(document_id.lastIndexOf(":")+1);
                cursor.close();

                cursor = getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
                cursor.moveToFirst();
                mMediaPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
            }
        }
    }
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap    bm = null;
        if (data != null) {
            try {
                    bm = MediaStore.Images.Media.getBitmap(VolunteerActivity.this.getContentResolver(), data.getData());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        mImageView.setImageBitmap(bm);
    }
    public void getRespose(){
        mProgressDialog.show();
        final String lNameString = mNameEditText.getText().toString();
        final String lMobileNumberString = mMobileNumberEditText.getText().toString();
        final String lFlatString = mFlatNo.getText().toString();
        final String lBuildingString = mBuilding.getText().toString();
        final String lRoadStrig = mRoadStreet.getText().toString();
        final String lAreaString = mArea.getText().toString();
        final String lPinCodeString = mPinCode.getText().toString();
        final String lFatherNameString = mFatherName.getText().toString();
        final String lDOBString = mDobEdit.getText().toString();
        if(lNameString.isEmpty()){
            mNameEditText.setError("Please fill Full name");
            mNameEditText.requestFocus();
        }
        else if (lMobileNumberString.isEmpty()){
            mMobileNumberEditText.setError("Please Fill the mobile number");
            mMobileNumberEditText.requestFocus();
        }else if(lFatherNameString.isEmpty()){
            mFatherName.setError("Pease Fill the father name");
            mFatherName.requestFocus();
        }else if (lDOBString.isEmpty()){
            mDobEdit.setError("Please Select DOB");
            mDobEdit.requestFocus();
        }
        else if (lFlatString.isEmpty()){
            mFlatNo.setError("Please fill the Flat No.");
            mFlatNo.requestFocus();
        }else if (mGender.isEmpty()) {
            Log.w(TAG,"Check selected Gender: "+mGender);
            Toast.makeText(getApplicationContext(), "Please choose Your gender", Toast.LENGTH_SHORT).show();
        }  else if (lBuildingString.isEmpty()) {
            mBuilding.setError("Please fill the Value.");
            mBuilding.requestFocus();
        }
        else if (lRoadStrig.isEmpty()) {
            mRoadStreet.setError("Please fill the Value.");
            mRoadStreet.requestFocus();
        }
        else if (lAreaString.isEmpty()) {
            mArea.setError("Please fill the Value.");
            mArea.requestFocus();
        }
        else if (lPinCodeString.isEmpty()) {
            mPinCode.setError("Please fill the Value.");
            mPinCode.requestFocus();
        }
        else {
            // Map is used to multipart the file using okhttp3.RequestBody
            Log.w(TAG, "MEDIA PATH: " + mMediaPath);
            try {
                if (mMediaPath != null) {
                    mFile = new File(String.valueOf(mMediaPath));
                } else {
                    Toast.makeText(getApplicationContext(), "Plese choose Profile pic", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), mMediaPath, Toast.LENGTH_SHORT).show();
            }
            Log.w(TAG, "Root PATH: " + mFile);
            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), mFile);
            final MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData(
                    "image", String.valueOf(mFile), requestBody);
//                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
            RequestBody lNameRequest = RequestBody.create(MediaType.parse("text/plain")
                    , lNameString);
            final RequestBody lMobileRequest = RequestBody.create(MediaType.parse("text/plain")
                    , lMobileNumberString);
            RequestBody lFUllAddressRequest = RequestBody.create(MediaType.parse("text/plain")
                    , lFlatString);

            RequestBody lBildingString = RequestBody.create(MediaType.parse("text/plain")
                    , lBuildingString);
            RequestBody lArea = RequestBody.create(MediaType.parse("text/plain")
                    , lAreaString);
            RequestBody lFatherName = RequestBody.create(MediaType.parse("text/plain")
                    , lFatherNameString);
            RequestBody lDob = RequestBody.create(MediaType.parse("text/plain")
                    , lDOBString);
            RequestBody lRoad = RequestBody.create(MediaType.parse("text/plain")
                    , lRoadStrig);
            RequestBody lPinCode = RequestBody.create(MediaType.parse("text/plain")
                    , lPinCodeString);
            RequestBody lState = RequestBody.create(MediaType.parse("text/plain")
                    , mStateString);
            RequestBody lDistrict = RequestBody.create(MediaType.parse("text/plain")
                    , mDistrictString);


            RequestBody lGender = RequestBody.create(MediaType.parse("text/plain"), mGender);
            UserRegisterApi lUserRegisterApi = RetrofitClient.postUserdata();
            Call<Volunteer> lCallUserResponse = lUserRegisterApi
                    .registerVolunteer(fileToUpload, lNameRequest, lGender, lMobileRequest, lFatherName,
                            lDob, lFUllAddressRequest, lBildingString, lRoad, lArea, lPinCode, lState,
                            lDistrict);
            lCallUserResponse.enqueue(new Callback<Volunteer>() {
                @Override
                public void onResponse(Call<Volunteer> call, Response<Volunteer> response) {
                    Log.w("RegisterActivity", "Response: " + response.body() + " File Path: " + fileToUpload.toString());
                    if (response.body() != null) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus()) {
                                Intent lIntent = new Intent(VolunteerActivity.this, OtpVerficationActivity.class);
                                lIntent.putExtra("vmobile", lMobileNumberString);
                                startActivity(lIntent);
                                mProgressDialog.dismiss();
                            }else {
                                Toast.makeText(getApplicationContext(), "Acoount already Exist", Toast.LENGTH_SHORT).show();
                                mProgressDialog.dismiss();
                            }
                        } else {
    //                        Log.w(TAG,"500 Internal Error: "+response.body());
                            Toast.makeText(getApplicationContext(), "Something is wrong", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<Volunteer> call, Throwable t) {
                    Log.w("RegisterActivity", "Response Failed: " + t.toString() + call.toString());
                    mProgressDialog.dismiss();
                }
            });
        }
    }

    public void registerVolunteer(){
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButton.setEnabled(false);
                mButton.getBackground().setAlpha(128);
                Log.w(TAG,"BITMAP PATH: "+mGender);
                final String MyPREFERENCES = "MyPrefs" ;
                SharedPreferences lSharedPref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor lEdit = lSharedPref.edit();
                lEdit.putString("gender", mGender);
                lEdit.apply();
                getRespose();
            }
        });
    }

    public void getState(){
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

        if (mState != null) {
            mState.setAdapter(customAdapter);
            mState.setOnItemSelectedListener(this);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int aPosition, long l) {
        Log.w(TAG,"AdapterViewID: "+adapterView.getId());
        switch (adapterView.getId()){
            case R.id.v_state_spinner:
                Log.w(TAG,"State Spinner: ");
                if (aPosition!=0)
                    mStateString = mStateCustomList.get(aPosition).getSpinnerText();
                Log.w(TAG,"SELECTED State: "+mStateCustomList.get(aPosition).getSpinnerText());

            case R.id.v_district_spinner:
                Log.w(TAG,"SELECTED ITEM: "+mStateString);
                String sp1= String.valueOf(mStateCustomList.get(aPosition).getSpinnerText());
                Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
                switch(sp1){
                    case "Andhra Pradesh":
                        List<String> lAplist = new ArrayList<String>();
                        lAplist.add("Anantapur");
                        lAplist.add("Chittoor");
                        lAplist.add("East Godavari");
                        lAplist.add("Guntur");
                        lAplist.add("Kadapa");
                        lAplist.add("Krishna");
                        lAplist.add("Kurnool");
                        lAplist.add("Prakasam");
                        lAplist.add("Sri Potti Sriramulu Nellore");
                        lAplist.add("Srikakulam");
                        lAplist.add("Visakhapatnam");
                        lAplist.add("Vizianagaram");
                        lAplist.add("West Godavari");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lAplist);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.add("Select District");
                        dataAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(dataAdapter);
                        mDistrictString = adapterView.getItemAtPosition(aPosition).toString();
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;

                    case "Arunachal Pradesh":
                        List<String> lArunachalPradeshDistrictlist = new ArrayList<String>();
                        lArunachalPradeshDistrictlist.add("Select District");
                        lArunachalPradeshDistrictlist.add("Anjaw");
                        lArunachalPradeshDistrictlist.add("Changlang");
                        lArunachalPradeshDistrictlist.add("East Kameng");
                        lArunachalPradeshDistrictlist.add("East Siang");
                        lArunachalPradeshDistrictlist.add("Kamle");
                        lArunachalPradeshDistrictlist.add("Kra Daadi");
                        lArunachalPradeshDistrictlist.add("Kurung Kumey");
                        lArunachalPradeshDistrictlist.add("Lepa Rada");
                        lArunachalPradeshDistrictlist.add("Lohit");
                        lArunachalPradeshDistrictlist.add("Longding");
                        lArunachalPradeshDistrictlist.add("Lower Dibang Valley");
                        lArunachalPradeshDistrictlist.add("Lower Siang");
                        lArunachalPradeshDistrictlist.add("Lower Subansiri");
                        lArunachalPradeshDistrictlist.add("Namsai");
                        lArunachalPradeshDistrictlist.add("Pakke-Kessang");
                        lArunachalPradeshDistrictlist.add("Papum Pare");
                        lArunachalPradeshDistrictlist.add("Shi Yomi");
                        lArunachalPradeshDistrictlist.add("Siang");
                        lArunachalPradeshDistrictlist.add("Tawang");
                        lArunachalPradeshDistrictlist.add("Tirap");
                        lArunachalPradeshDistrictlist.add("Upper Dibang Valley");
                        lArunachalPradeshDistrictlist.add("Upper Siang");
                        lArunachalPradeshDistrictlist.add("Upper Subansiri");
                        lArunachalPradeshDistrictlist.add("West Kameng");
                        lArunachalPradeshDistrictlist.add("West Siang");
                        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lArunachalPradeshDistrictlist);
                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter2.notifyDataSetChanged();
                        mDistrict.setAdapter(dataAdapter2);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;

                    case "Assam":
                        List<String> lAssamDistrictlist = new ArrayList<String>();
                        lAssamDistrictlist.add("Select District");
                        lAssamDistrictlist.add("Baksa");
                        lAssamDistrictlist.add("Barpeta");
                        lAssamDistrictlist.add("Bishwanath");
                        lAssamDistrictlist.add("Bongaigaon");
                        lAssamDistrictlist.add("Cachar");
                        lAssamDistrictlist.add("Charaideo");
                        lAssamDistrictlist.add("Chirang");
                        lAssamDistrictlist.add("Darrang");
                        lAssamDistrictlist.add("Dhemaji");
                        lAssamDistrictlist.add("Dhubri");
                        lAssamDistrictlist.add("Dibrugarh");
                        lAssamDistrictlist.add("Dima Hasao");
                        lAssamDistrictlist.add("Goalpara");
                        lAssamDistrictlist.add("Golaghat");
                        lAssamDistrictlist.add("Hailakandi");
                        lAssamDistrictlist.add("Hojai");
                        lAssamDistrictlist.add("Jorhat");
                        lAssamDistrictlist.add("Kamrup");
                        lAssamDistrictlist.add("Kamrup Metropolitan");
                        lAssamDistrictlist.add("Karbi Anglong");
                        lAssamDistrictlist.add("Karimganj");
                        lAssamDistrictlist.add("Kokrajhar");
                        lAssamDistrictlist.add("Lakhimpur");
                        lAssamDistrictlist.add("Majuli");
                        lAssamDistrictlist.add("Morigaon");
                        lAssamDistrictlist.add("Nagaon");
                        lAssamDistrictlist.add("Nalbari");
                        lAssamDistrictlist.add("Sivasagar");
                        lAssamDistrictlist.add("South Salmara");
                        lAssamDistrictlist.add("Sonitpur");
                        lAssamDistrictlist.add("Tinsukia");
                        lAssamDistrictlist.add("Udalguri");
                        lAssamDistrictlist.add("West Karbi Anglong");
                        ArrayAdapter<String> lAssamAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lAssamDistrictlist);
                        lAssamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lAssamAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lAssamAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Bihar":
                        List<String> lBiharDistrictlist = new ArrayList<String>();
                        lBiharDistrictlist.add("Select District");
                        lBiharDistrictlist.add("Araria");
                        lBiharDistrictlist.add("Arwal");
                        lBiharDistrictlist.add("Aurangabad");
                        lBiharDistrictlist.add("Banka");
                        lBiharDistrictlist.add("Begusarai");
                        lBiharDistrictlist.add("Bhagalpur");
                        lBiharDistrictlist.add("Bhojpur");
                        lBiharDistrictlist.add("Buxar");
                        lBiharDistrictlist.add("Darbhanga");
                        lBiharDistrictlist.add("East Champaran");
                        lBiharDistrictlist.add("Gaya");
                        lBiharDistrictlist.add("Gopalganj");
                        lBiharDistrictlist.add("Jamui");
                        lBiharDistrictlist.add("Jehanabad");
                        lBiharDistrictlist.add("Kaimur");
                        lBiharDistrictlist.add("Katihar");
                        lBiharDistrictlist.add("Khagaria");
                        lBiharDistrictlist.add("Kishanganj");
                        lBiharDistrictlist.add("Lakhisarai");
                        lBiharDistrictlist.add("Madhepura");
                        lBiharDistrictlist.add("Madhubani");
                        lBiharDistrictlist.add("Munger");
                        lBiharDistrictlist.add("Muzaffarpur");
                        lBiharDistrictlist.add("Nalanda");
                        lBiharDistrictlist.add("Nawada");
                        lBiharDistrictlist.add("Patna");
                        lBiharDistrictlist.add("Purnia");
                        lBiharDistrictlist.add("Rohtas");
                        lBiharDistrictlist.add("Saharsa");
                        lBiharDistrictlist.add("Samastipur");
                        lBiharDistrictlist.add("Saran");
                        lBiharDistrictlist.add("Sheikhpura");
                        lBiharDistrictlist.add("Sheohar");
                        lBiharDistrictlist.add("Sitamarhi");
                        lBiharDistrictlist.add("Siwan");
                        lBiharDistrictlist.add("Supaul");
                        lBiharDistrictlist.add("Vaishali");
                        lBiharDistrictlist.add("West Champaran");
                        ArrayAdapter<String> lBiharAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lBiharDistrictlist);
                        lBiharAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lBiharAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lBiharAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Chhattisgarh":
                        List<String> lChhattisgarhDistrictlist = new ArrayList<String>();
                        lChhattisgarhDistrictlist.add("Select District");
                        lChhattisgarhDistrictlist.add("Balod");
                        lChhattisgarhDistrictlist.add("Baloda Bazar");
                        lChhattisgarhDistrictlist.add("Balrampur");
                        lChhattisgarhDistrictlist.add("Bastar");
                        lChhattisgarhDistrictlist.add("Bemetara");
                        lChhattisgarhDistrictlist.add("Bijapur");
                        lChhattisgarhDistrictlist.add("Bilaspur");
                        lChhattisgarhDistrictlist.add("Dantewada");
                        lChhattisgarhDistrictlist.add("Dhamtari");
                        lChhattisgarhDistrictlist.add("Durg");
                        lChhattisgarhDistrictlist.add("Gariaband");
                        lChhattisgarhDistrictlist.add("Gaurela-Pendra-Marwahi\t");
                        lChhattisgarhDistrictlist.add("Janjgir-Champa");
                        lChhattisgarhDistrictlist.add("Jashpur");
                        lChhattisgarhDistrictlist.add("Kabirdham");
                        lChhattisgarhDistrictlist.add("Kanker");
                        lChhattisgarhDistrictlist.add("Kondagaon");
                        lChhattisgarhDistrictlist.add("Korba");
                        lChhattisgarhDistrictlist.add("Koriya");
                        lChhattisgarhDistrictlist.add("Mahasamund");
                        lChhattisgarhDistrictlist.add("Mungeli");
                        lChhattisgarhDistrictlist.add("Narayanpur");
                        lChhattisgarhDistrictlist.add("Raigarh");
                        lChhattisgarhDistrictlist.add("Raipur");
                        lChhattisgarhDistrictlist.add("Rajnandgaon");
                        lChhattisgarhDistrictlist.add("Sukma");
                        lChhattisgarhDistrictlist.add("Surajpur");
                        lChhattisgarhDistrictlist.add("Surguja");
                        ArrayAdapter<String> lChhattisgarhAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lChhattisgarhDistrictlist);
                        lChhattisgarhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lChhattisgarhAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lChhattisgarhAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Goa":
                        List<String> lGoaDistrictlist = new ArrayList<String>();
                        lGoaDistrictlist.add("Select District");
                        lGoaDistrictlist.add("North Goa");
                        lGoaDistrictlist.add("South Goa");
                        ArrayAdapter<String> lGoaAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lGoaDistrictlist);
                        lGoaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lGoaAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lGoaAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Gujarat":
                        List<String> lGujaratDistrictlist = new ArrayList<String>();
                        lGujaratDistrictlist.add("Select District");
                        lGujaratDistrictlist.add("Vadodara");
                        lGujaratDistrictlist.add("Rajkot");
                        lGujaratDistrictlist.add("Valsad");
                        lGujaratDistrictlist.add("Surat");
                        lGujaratDistrictlist.add("Patan");
                        lGujaratDistrictlist.add("Bharuch");
                        lGujaratDistrictlist.add("The Dangs");
                        lGujaratDistrictlist.add("Bhavnagar");
                        lGujaratDistrictlist.add("Ahmadabad");
                        lGujaratDistrictlist.add("Dahod");
                        lGujaratDistrictlist.add("Kheda");
                        lGujaratDistrictlist.add("Sabar Kantha");
                        lGujaratDistrictlist.add("Surendranagar");
                        lGujaratDistrictlist.add("Gandhinagar");
                        lGujaratDistrictlist.add("Banas Kantha");
                        lGujaratDistrictlist.add("Kachchh");
                        lGujaratDistrictlist.add("Junagadh");
                        lGujaratDistrictlist.add("Jamnagar");
                        lGujaratDistrictlist.add("Mahesana");
                        lGujaratDistrictlist.add("Rann of Kachchh");
                        lGujaratDistrictlist.add("Navsari");
                        lGujaratDistrictlist.add("Rajpipla");
                        lGujaratDistrictlist.add("Amreli");
                        lGujaratDistrictlist.add("Panch Mahals");
                        lGujaratDistrictlist.add("Anand");
                        ArrayAdapter<String> lGujaratAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lGujaratDistrictlist);
                        lGujaratAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lGujaratAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lGujaratAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Haryana":
                        List<String> lHaryanaDistrictlist = new ArrayList<String>();
                        lHaryanaDistrictlist.add("Select District");
                        lHaryanaDistrictlist.add("Ambala");
                        lHaryanaDistrictlist.add("Bhiwani");
                        lHaryanaDistrictlist.add("Charkhi Dadri");
                        lHaryanaDistrictlist.add("Faridabad");
                        lHaryanaDistrictlist.add("Fatehabad");
                        lHaryanaDistrictlist.add("Gurugram");
                        lHaryanaDistrictlist.add("Hissar");
                        lHaryanaDistrictlist.add("Jhajjar");
                        lHaryanaDistrictlist.add("Jind");
                        lHaryanaDistrictlist.add("Kaithal");
                        lHaryanaDistrictlist.add("Karnal");
                        lHaryanaDistrictlist.add("Kurukshetra");
                        lHaryanaDistrictlist.add("Mahendragarh");
                        lHaryanaDistrictlist.add("Nuh");
                        lHaryanaDistrictlist.add("Palwal");
                        lHaryanaDistrictlist.add("Panchkula");
                        lHaryanaDistrictlist.add("Panipat");
                        lHaryanaDistrictlist.add("Rewari");
                        lHaryanaDistrictlist.add("Rohtak");
                        lHaryanaDistrictlist.add("Sirsa");
                        lHaryanaDistrictlist.add("Sonipat");
                        lHaryanaDistrictlist.add("Yamuna Nagar");
                        ArrayAdapter<String> lHaryanaAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lHaryanaDistrictlist);
                        lHaryanaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lHaryanaAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lHaryanaAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Himachal Pradesh":
                        List<String> lHpDistrictlist = new ArrayList<String>();
                        lHpDistrictlist.add("Select District");
                        lHpDistrictlist.add("Bilaspur");
                        lHpDistrictlist.add("Chamba");
                        lHpDistrictlist.add("Hamirpur");
                        lHpDistrictlist.add("Kangra");
                        lHpDistrictlist.add("Kinnaur");
                        lHpDistrictlist.add("Kullu");
                        lHpDistrictlist.add("Lahaul and Spiti");
                        lHpDistrictlist.add("Mandi");
                        lHpDistrictlist.add("Shimla");
                        lHpDistrictlist.add("Sirmaur");
                        lHpDistrictlist.add("Solan");
                        lHpDistrictlist.add("Una");
                        ArrayAdapter<String> lHPAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lHpDistrictlist);
                        lHPAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lHPAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lHPAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Jammu and kashmir":
                        List<String> lJKDistrictlist = new ArrayList<String>();
                        lJKDistrictlist.add("Select District");
                        lJKDistrictlist.add("Anantnag");
                        lJKDistrictlist.add("Bandipora");
                        lJKDistrictlist.add("Baramulla");
                        lJKDistrictlist.add("Badgam");
                        lJKDistrictlist.add("Doda");
                        lJKDistrictlist.add("Ganderbal");
                        lJKDistrictlist.add("Jammu");
                        lJKDistrictlist.add("Kathua");
                        lJKDistrictlist.add("Kishtwar");
                        lJKDistrictlist.add("Kulgam");
                        lJKDistrictlist.add("Kupwara");
                        lJKDistrictlist.add("Poonch");
                        lJKDistrictlist.add("Pulwama");
                        lJKDistrictlist.add("Rajouri");
                        lJKDistrictlist.add("Ramban");
                        lJKDistrictlist.add("Reasi");
                        lJKDistrictlist.add("Samba");
                        lJKDistrictlist.add("Shopian");
                        lJKDistrictlist.add("Srinagar");
                        lJKDistrictlist.add("Udhampur");
                        ArrayAdapter<String> lJKAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lJKDistrictlist);
                        lJKAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lJKAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lJKAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Ladakh":
                        List<String> lLadakhistrictlist = new ArrayList<String>();
                        lLadakhistrictlist.add("Select District");
                        lLadakhistrictlist.add("Kargil");
                        lLadakhistrictlist.add("Leh");
                        ArrayAdapter<String> lLadakhAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lLadakhistrictlist);
                        lLadakhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lLadakhAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lLadakhAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Jharkhand":
                        List<String> lJDistrictlist = new ArrayList<String>();
                        lJDistrictlist.add("Select District");
                        lJDistrictlist.add("Bokaro");
                        lJDistrictlist.add("Chatra");
                        lJDistrictlist.add("Deoghar");
                        lJDistrictlist.add("Dhanbad");
                        lJDistrictlist.add("Dumka");
                        lJDistrictlist.add("East Singhbhum");
                        lJDistrictlist.add("Garhwa");
                        lJDistrictlist.add("Giridih");
                        lJDistrictlist.add("Godda");
                        lJDistrictlist.add("Gumla");
                        lJDistrictlist.add("Hazaribag");
                        lJDistrictlist.add("Jamtara");
                        lJDistrictlist.add("Khunti");
                        lJDistrictlist.add("Koderma");
                        lJDistrictlist.add("Latehar");
                        lJDistrictlist.add("Lohardaga");
                        lJDistrictlist.add("Pakur");
                        lJDistrictlist.add("Palamu");
                        lJDistrictlist.add("Ramgarh");
                        lJDistrictlist.add("Ranchi");
                        lJDistrictlist.add("Sahibganj");
                        lJDistrictlist.add("Seraikela Kharsawan");
                        lJDistrictlist.add("Simdega");
                        lJDistrictlist.add("West Singhbhum");
                        ArrayAdapter<String> lJAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lJDistrictlist);
                        lJAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lJAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lJAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Karnataka":
                        List<String> lKDistrictlist = new ArrayList<String>();
                        lKDistrictlist.add("Select District");
                        lKDistrictlist.add("Bagalkot");
                        lKDistrictlist.add("Ballari");
                        lKDistrictlist.add("Belagavi");
                        lKDistrictlist.add("Bengaluru Rural");
                        lKDistrictlist.add("Bengaluru Urban");
                        lKDistrictlist.add("Bidar");
                        lKDistrictlist.add("Chamarajnagar");
                        lKDistrictlist.add("Chikkaballapur");
                        lKDistrictlist.add("Chikkamagaluru");
                        lKDistrictlist.add("Chitradurga");
                        lKDistrictlist.add("Dakshina Kannada");
                        lKDistrictlist.add("Davanagere");
                        lKDistrictlist.add("Dharwad");
                        lKDistrictlist.add("Gadag");
                        lKDistrictlist.add("Hassan");
                        lKDistrictlist.add("Haveri");
                        lKDistrictlist.add("Kalaburagi");
                        lKDistrictlist.add("Kodagu");
                        lKDistrictlist.add("Kolar");
                        lKDistrictlist.add("Koppal");
                        lKDistrictlist.add("Mandya");
                        lKDistrictlist.add("Mysuru");
                        lKDistrictlist.add("Raichur");
                        lKDistrictlist.add("Ramanagara");
                        lKDistrictlist.add("Shivamogga");
                        lKDistrictlist.add("Tumakuru");
                        lKDistrictlist.add("Udupi");
                        lKDistrictlist.add("Uttara Kannada");
                        lKDistrictlist.add("Vijayapura");
                        lKDistrictlist.add("Yadgir");
                        ArrayAdapter<String> lKAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lKDistrictlist);
                        lKAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lKAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lKAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Kerala":
                        List<String> lKeralaDistrictlist = new ArrayList<String>();
                        lKeralaDistrictlist.add("Select District");
                        lKeralaDistrictlist.add("Alappuzha");
                        lKeralaDistrictlist.add("Ernakulam");
                        lKeralaDistrictlist.add("Idukki");
                        lKeralaDistrictlist.add("Kannur");
                        lKeralaDistrictlist.add("Kasaragod");
                        lKeralaDistrictlist.add("Kollam");
                        lKeralaDistrictlist.add("Kottayam");
                        lKeralaDistrictlist.add("Kozhikode");
                        lKeralaDistrictlist.add("Malappuram");
                        lKeralaDistrictlist.add("Palakkad");
                        lKeralaDistrictlist.add("Pathanamthitta");
                        lKeralaDistrictlist.add("Thrissur");
                        lKeralaDistrictlist.add("Thiruvananthapuram");
                        lKeralaDistrictlist.add("Wayanad");
                        ArrayAdapter<String> lKeralaAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lKeralaDistrictlist);
                        lKeralaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lKeralaAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lKeralaAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Madhya Pradesh":
                        List<String> lMpDistrictlist = new ArrayList<String>();
                        lMpDistrictlist.add("Select District");
                        lMpDistrictlist.add("Agar Malwa");
                        lMpDistrictlist.add("Alirajpur");
                        lMpDistrictlist.add("Anuppur");
                        lMpDistrictlist.add("Ashok Nagar");
                        lMpDistrictlist.add("Balaghat");
                        lMpDistrictlist.add("Barwani");
                        lMpDistrictlist.add("Betul");
                        lMpDistrictlist.add("Bhind");
                        lMpDistrictlist.add("Bhopal");
                        lMpDistrictlist.add("Burhanpur");
                        lMpDistrictlist.add("Chhatarpur");
                        lMpDistrictlist.add("Chhindwara");
                        lMpDistrictlist.add("Damoh");
                        lMpDistrictlist.add("Datia");
                        lMpDistrictlist.add("Dewas");
                        lMpDistrictlist.add("Dhar");
                        lMpDistrictlist.add("Dindori");
                        lMpDistrictlist.add("Guna");
                        lMpDistrictlist.add("Gwalior");
                        lMpDistrictlist.add("Harda");
                        lMpDistrictlist.add("Hoshangabad");
                        lMpDistrictlist.add("Indore");
                        lMpDistrictlist.add("Jabalpur");
                        lMpDistrictlist.add("Jhabua");
                        lMpDistrictlist.add("Katni");
                        lMpDistrictlist.add("Khandwa");
                        lMpDistrictlist.add("Khargone");
                        lMpDistrictlist.add("Mandla");
                        lMpDistrictlist.add("Mandsaur");
                        lMpDistrictlist.add("Morena");
                        lMpDistrictlist.add("Narsinghpur");
                        lMpDistrictlist.add("Neemuch");
                        lMpDistrictlist.add("Niwari");
                        lMpDistrictlist.add("Panna");
                        lMpDistrictlist.add("Raisen");
                        lMpDistrictlist.add("Rajgarh");
                        lMpDistrictlist.add("Ratlam");
                        lMpDistrictlist.add("Rewa");
                        lMpDistrictlist.add("Sagar");
                        lMpDistrictlist.add("Satna");
                        lMpDistrictlist.add("Sehore");
                        lMpDistrictlist.add("Seoni");
                        lMpDistrictlist.add("Shahdol");
                        lMpDistrictlist.add("Shajapur");
                        lMpDistrictlist.add("Sheopur");
                        lMpDistrictlist.add("Shivpuri");
                        lMpDistrictlist.add("Sidhi");
                        lMpDistrictlist.add("Singrauli");
                        lMpDistrictlist.add("Tikamgarh");
                        lMpDistrictlist.add("Ujjain");
                        lMpDistrictlist.add("Umaria");
                        lMpDistrictlist.add("Vidisha");
                        ArrayAdapter<String> lMpAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lMpDistrictlist);
                        lMpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lMpAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lMpAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Manipur":
                        List<String> lManipurDistrictlist = new ArrayList<String>();
                        lManipurDistrictlist.add("Select District");
                        lManipurDistrictlist.add("Bishnupur");
                        lManipurDistrictlist.add("Chandel");
                        lManipurDistrictlist.add("Churachandpur");
                        lManipurDistrictlist.add("Imphal East");
                        lManipurDistrictlist.add("Imphal West");
                        lManipurDistrictlist.add("Jiribam");
                        lManipurDistrictlist.add("Kakching");
                        lManipurDistrictlist.add("Kamjong");
                        lManipurDistrictlist.add("Kangpokpi");
                        lManipurDistrictlist.add("Noney");
                        lManipurDistrictlist.add("Pherzawl");
                        lManipurDistrictlist.add("Senapati");
                        lManipurDistrictlist.add("Tamenglong");
                        lManipurDistrictlist.add("Tengnoupal");
                        lManipurDistrictlist.add("Thoubal");
                        lManipurDistrictlist.add("Ukhrul");
                        ArrayAdapter<String> lManipurAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lManipurDistrictlist);
                        lManipurAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lManipurAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lManipurAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Meghalaya":
                        List<String> lMeghalayaDistrictlist = new ArrayList<String>();
                        lMeghalayaDistrictlist.add("Select District");
                        lMeghalayaDistrictlist.add("East Garo Hills");
                        lMeghalayaDistrictlist.add("East Khasi Hills");
                        lMeghalayaDistrictlist.add("East Jaintia Hills");
                        lMeghalayaDistrictlist.add("North Garo Hills");
                        lMeghalayaDistrictlist.add("Ri Bhoi");
                        lMeghalayaDistrictlist.add("South Garo Hills");
                        lMeghalayaDistrictlist.add("South West Garo Hills");
                        lMeghalayaDistrictlist.add("South West Khasi Hills");
                        lMeghalayaDistrictlist.add("West Jaintia Hills");
                        lMeghalayaDistrictlist.add("West Garo Hills");
                        lMeghalayaDistrictlist.add("West Khasi Hills");
                        ArrayAdapter<String> lMeghalayaAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lMeghalayaDistrictlist);
                        lMeghalayaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lMeghalayaAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lMeghalayaAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Mizoram":
                        List<String> lMZDistrictlist = new ArrayList<String>();
                        lMZDistrictlist.add("Select District");
                        lMZDistrictlist.add("Aizawl");
                        lMZDistrictlist.add("Champhai");
                        lMZDistrictlist.add("Kolasib");
                        lMZDistrictlist.add("Lawngtlai");
                        lMZDistrictlist.add("Lunglei");
                        lMZDistrictlist.add("Mamit");
                        lMZDistrictlist.add("Saiha");
                        lMZDistrictlist.add("Serchhip");
                        ArrayAdapter<String> lMZAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lMZDistrictlist);
                        lMZAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lMZAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lMZAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Nagaland":
                        List<String> lNLDistrictlist = new ArrayList<String>();
                        lNLDistrictlist.add("Select District");
                        lNLDistrictlist.add("Dimapur");
                        lNLDistrictlist.add("Kiphire");
                        lNLDistrictlist.add("Kohima");
                        lNLDistrictlist.add("Longleng");
                        lNLDistrictlist.add("Mokokchung");
                        lNLDistrictlist.add("Mon");
                        lNLDistrictlist.add("Noklak");
                        lNLDistrictlist.add("Peren");
                        lNLDistrictlist.add("Phek");
                        lNLDistrictlist.add("Tuensang");
                        lNLDistrictlist.add("Wokha");
                        lNLDistrictlist.add("Zunheboto");
                        ArrayAdapter<String> lNlAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lNLDistrictlist);
                        lNlAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lNlAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lNlAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Odisha":
                        List<String> lODDistrictlist = new ArrayList<String>();
                        lODDistrictlist.add("Select District");
                        lODDistrictlist.add("Angul");
                        lODDistrictlist.add("Boudh");
                        lODDistrictlist.add("Bhadrak");
                        lODDistrictlist.add("Balangir");
                        lODDistrictlist.add("Balasore");
                        lODDistrictlist.add("Cuttack");
                        lODDistrictlist.add("Debagarh");
                        lODDistrictlist.add("Dhenkanal");
                        lODDistrictlist.add("Ganjam");
                        lODDistrictlist.add("Gajapati");
                        lODDistrictlist.add("Jharsuguda");
                        lODDistrictlist.add("Jajpur");
                        lODDistrictlist.add("Jagatsinghpur");
                        lODDistrictlist.add("Khordha");
                        lODDistrictlist.add("Kendujhar");
                        lODDistrictlist.add("Kalahandi");
                        lODDistrictlist.add("Kandhamal");
                        lODDistrictlist.add("Koraput");
                        lODDistrictlist.add("Kendrapara");
                        lODDistrictlist.add("Malkangiri");
                        lODDistrictlist.add("Mayurbhanj");
                        lODDistrictlist.add("Nabarangpur");
                        lODDistrictlist.add("Nuapada");
                        lODDistrictlist.add("Nayagarh");
                        lODDistrictlist.add("Puri");
                        lODDistrictlist.add("Rayagada");
                        lODDistrictlist.add("Sambalpur");
                        lODDistrictlist.add("Subarnapur");
                        lODDistrictlist.add("Sundargarh");
                        ArrayAdapter<String> lODAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lODDistrictlist);
                        lODAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lODAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lODAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Punjab":
                        List<String> lPBDistrictlist = new ArrayList<String>();
                        lPBDistrictlist.add("Select District");
                        lPBDistrictlist.add("Amritsar");
                        lPBDistrictlist.add("Barnala");
                        lPBDistrictlist.add("Bathinda");
                        lPBDistrictlist.add("Firozpur");
                        lPBDistrictlist.add("Faridkot");
                        lPBDistrictlist.add("Fatehgarh Sahib");
                        lPBDistrictlist.add("Fazilka");
                        lPBDistrictlist.add("Gurdaspur");
                        lPBDistrictlist.add("Hoshiarpur");
                        lPBDistrictlist.add("Jalandhar");
                        lPBDistrictlist.add("Kapurthala");
                        lPBDistrictlist.add("Ludhiana");
                        lPBDistrictlist.add("Mansa");
                        lPBDistrictlist.add("Moga");
                        lPBDistrictlist.add("Sri Muktsar Sahib");
                        lPBDistrictlist.add("Pathankot");
                        lPBDistrictlist.add("Patiala");
                        lPBDistrictlist.add("Rupnagar");
                        lPBDistrictlist.add("Sahibzada Ajit Singh Nagar");
                        lPBDistrictlist.add("Sangrur");
                        lPBDistrictlist.add("Shahid Bhagat Singh Nagar");
                        lPBDistrictlist.add("Tarn Taran");
                        ArrayAdapter<String> lPBAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lPBDistrictlist);
                        lPBAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lPBAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lPBAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Rajasthan":
                        List<String> lRJDistrictlist = new ArrayList<String>();
                        lRJDistrictlist.add("Select District");
                        lRJDistrictlist.add("Ajmer");
                        lRJDistrictlist.add("Alwar");
                        lRJDistrictlist.add("Bikaner");
                        lRJDistrictlist.add("Barmer");
                        lRJDistrictlist.add("Banswara");
                        lRJDistrictlist.add("Bharatpur");
                        lRJDistrictlist.add("Baran");
                        lRJDistrictlist.add("Bundi");
                        lRJDistrictlist.add("Bhilwara");
                        lRJDistrictlist.add("Churu");
                        lRJDistrictlist.add("Chittorgarh");
                        lRJDistrictlist.add("Dausa");
                        lRJDistrictlist.add("Dholpur");
                        lRJDistrictlist.add("Dungarpur");
                        lRJDistrictlist.add("Ganganagar");
                        lRJDistrictlist.add("Hanumangarh");
                        lRJDistrictlist.add("Jhunjhunu");
                        lRJDistrictlist.add("Jalore");
                        lRJDistrictlist.add("Jodhpur");
                        lRJDistrictlist.add("Jaipur");
                        lRJDistrictlist.add("Jaisalmer");
                        lRJDistrictlist.add("Jhalawar");
                        lRJDistrictlist.add("Karauli");
                        lRJDistrictlist.add("Kota");
                        lRJDistrictlist.add("Nagaur");
                        lRJDistrictlist.add("Pali");
                        lRJDistrictlist.add("Pratapgarh");
                        lRJDistrictlist.add("Rajsamand");
                        lRJDistrictlist.add("Sikar");
                        lRJDistrictlist.add("Sawai Madhopur");
                        lRJDistrictlist.add("Sirohi");
                        lRJDistrictlist.add("Tonk");
                        lRJDistrictlist.add("Udaipur");
                        ArrayAdapter<String> lRJAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lRJDistrictlist);
                        lRJAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lRJAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lRJAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Sikkim":
                        List<String> lSikkimDistrictlist = new ArrayList<String>();
                        lSikkimDistrictlist.add("Select District");
                        lSikkimDistrictlist.add("East Sikkim");
                        lSikkimDistrictlist.add("North Sikkim");
                        lSikkimDistrictlist.add("South Sikkim");
                        lSikkimDistrictlist.add("West Sikkim");
                        ArrayAdapter<String> SikkimAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lSikkimDistrictlist);
                        SikkimAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        SikkimAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(SikkimAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Tamil Nadu":
                        List<String> lTLDistrictlist = new ArrayList<String>();
                        lTLDistrictlist.add("Select District");
                        lTLDistrictlist.add("Ariyalur");
                        lTLDistrictlist.add("Chengalpattu");
                        lTLDistrictlist.add("Chennai");
                        lTLDistrictlist.add("Coimbatore");
                        lTLDistrictlist.add("Cuddalore");
                        lTLDistrictlist.add("Dharmapuri");
                        lTLDistrictlist.add("Dindigul");
                        lTLDistrictlist.add("Erode");
                        lTLDistrictlist.add("Kallakurichi");
                        lTLDistrictlist.add("Kanchipuram");
                        lTLDistrictlist.add("Kanyakumari");
                        lTLDistrictlist.add("Karur");
                        lTLDistrictlist.add("Krishnagiri");
                        lTLDistrictlist.add("Madurai");
                        lTLDistrictlist.add("Nagapattinam");
                        lTLDistrictlist.add("Nilgiris");
                        lTLDistrictlist.add("Namakkal");
                        lTLDistrictlist.add("Perambalur");
                        lTLDistrictlist.add("Pudukkottai");
                        lTLDistrictlist.add("Ramanathapuram");
                        lTLDistrictlist.add("Ranipet");
                        lTLDistrictlist.add("Salem");
                        lTLDistrictlist.add("Sivaganga");
                        lTLDistrictlist.add("Tenkasi");
                        lTLDistrictlist.add("Tirupur");
                        lTLDistrictlist.add("Tiruchirappalli");
                        lTLDistrictlist.add("Theni");
                        lTLDistrictlist.add("Tirunelveli");
                        lTLDistrictlist.add("Thanjavur");
                        lTLDistrictlist.add("Thoothukudi");
                        lTLDistrictlist.add("Tirupattur");
                        lTLDistrictlist.add("Tiruvallur");
                        lTLDistrictlist.add("Tiruvarur");
                        lTLDistrictlist.add("Tiruvannamalai");
                        lTLDistrictlist.add("Vellore");
                        lTLDistrictlist.add("Viluppuram");
                        lTLDistrictlist.add("Virudhunagar");
                        ArrayAdapter<String> lTNAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lTLDistrictlist);
                        lTNAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lTNAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lTNAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Telangana":
                        List<String> lTelanganaDistrictlist = new ArrayList<String>();
                        lTelanganaDistrictlist.add("Select District");
                        lTelanganaDistrictlist.add("Adilabad");
                        lTelanganaDistrictlist.add("Komaram Bheem");
                        lTelanganaDistrictlist.add("Bhadradri Kothagudem");
                        lTelanganaDistrictlist.add("Hyderabad");
                        lTelanganaDistrictlist.add("Jagtial");
                        lTelanganaDistrictlist.add("Jangaon");
                        lTelanganaDistrictlist.add("Jayashankar Bhupalpally");
                        lTelanganaDistrictlist.add("Jogulamba Gadwal");
                        lTelanganaDistrictlist.add("Kamareddy");
                        lTelanganaDistrictlist.add("Karimnagar");
                        lTelanganaDistrictlist.add("Khammam");
                        lTelanganaDistrictlist.add("Mahabubabad");
                        lTelanganaDistrictlist.add("Mahbubnagar");
                        lTelanganaDistrictlist.add("Mancherial");
                        lTelanganaDistrictlist.add("Medak");
                        lTelanganaDistrictlist.add("Medchal-Malkajgiri");
                        lTelanganaDistrictlist.add("Mulugu");
                        lTelanganaDistrictlist.add("Nalgonda");
                        lTelanganaDistrictlist.add("Narayanpet");
                        lTelanganaDistrictlist.add("Nagarkurnool");
                        lTelanganaDistrictlist.add("Nirmal");
                        lTelanganaDistrictlist.add("Nizamabad");
                        lTelanganaDistrictlist.add("Peddapalli");
                        lTelanganaDistrictlist.add("Rajanna Sircilla");
                        lTelanganaDistrictlist.add("Ranga Reddy");
                        lTelanganaDistrictlist.add("Sangareddy");
                        lTelanganaDistrictlist.add("Siddipet");
                        lTelanganaDistrictlist.add("Suryapet");
                        lTelanganaDistrictlist.add("Vikarabad");
                        lTelanganaDistrictlist.add("Wanaparthy");
                        lTelanganaDistrictlist.add("Warangal Urban");
                        lTelanganaDistrictlist.add("Warangal Rural");
                        lTelanganaDistrictlist.add("Yadadri Bhuvanagiri");
                        ArrayAdapter<String> lTelanganaAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lTelanganaDistrictlist);
                        lTelanganaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lTelanganaAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lTelanganaAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Uttarakhand":
                        List<String> lUKDistrictlist = new ArrayList<String>();
                        lUKDistrictlist.add("Select District");
                        lUKDistrictlist.add("Almora");
                        lUKDistrictlist.add("Bageshwar");
                        lUKDistrictlist.add("Chamoli");
                        lUKDistrictlist.add("Champawat");
                        lUKDistrictlist.add("Dehradun");
                        lUKDistrictlist.add("Haridwar");
                        lUKDistrictlist.add("Nainital");
                        lUKDistrictlist.add("Pauri Garhwal");
                        lUKDistrictlist.add("Pithoragarh");
                        lUKDistrictlist.add("Rudraprayag");
                        lUKDistrictlist.add("Tehri Garhwal");
                        lUKDistrictlist.add("Udham Singh Nagar");
                        lUKDistrictlist.add("Uttarkashi");
                        ArrayAdapter<String> lUKAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lUKDistrictlist);
                        lUKAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lUKAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lUKAdapter);
                        break;
                    case "Uttar Pradesh":
                        List<String> lUPDistrictlist = new ArrayList<String>();
                        lUPDistrictlist.add("Select District");
                        lUPDistrictlist.add("Agra");
                        lUPDistrictlist.add("Aligarh");
                        lUPDistrictlist.add("Allahabad");
                        lUPDistrictlist.add("Ambedkar Nagar");
                        lUPDistrictlist.add("Amethi");
                        lUPDistrictlist.add("Amroha");
                        lUPDistrictlist.add("Auraiya");
                        lUPDistrictlist.add("Azamgarh");
                        lUPDistrictlist.add("Bagpat");
                        lUPDistrictlist.add("Bahraich");
                        lUPDistrictlist.add("Ballia");
                        lUPDistrictlist.add("Balrampur");
                        lUPDistrictlist.add("Banda");
                        lUPDistrictlist.add("Barabanki");
                        lUPDistrictlist.add("Bareilly");
                        lUPDistrictlist.add("Basti");
                        lUPDistrictlist.add("Bhadohi");
                        lUPDistrictlist.add("Bijnor");
                        lUPDistrictlist.add("Budaun");
                        lUPDistrictlist.add("Bulandshahr");
                        lUPDistrictlist.add("Chandauli");
                        lUPDistrictlist.add("Chitrakoot");
                        lUPDistrictlist.add("Deoria");
                        lUPDistrictlist.add("Etah");
                        lUPDistrictlist.add("Etawah");
                        lUPDistrictlist.add("Faizabad");
                        lUPDistrictlist.add("Farrukhabad");
                        lUPDistrictlist.add("Fatehpur");
                        lUPDistrictlist.add("Firozabad");
                        lUPDistrictlist.add("Gautam Buddh Nagar");
                        lUPDistrictlist.add("Ghaziabad");
                        lUPDistrictlist.add("Ghazipur");
                        lUPDistrictlist.add("Gonda");
                        lUPDistrictlist.add("Gorakhpur");
                        lUPDistrictlist.add("Hamirpur");
                        lUPDistrictlist.add("Hapur");
                        lUPDistrictlist.add("Hardoi");
                        lUPDistrictlist.add("Hathras");
                        lUPDistrictlist.add("Jalaun");
                        lUPDistrictlist.add("Jaunpur");
                        lUPDistrictlist.add("Jhansi");
                        lUPDistrictlist.add("Kannauj");
                        lUPDistrictlist.add("Kanpur Dehat");
                        lUPDistrictlist.add("Kanpur Nagar");
                        lUPDistrictlist.add("Kasganj");
                        lUPDistrictlist.add("Kaushambi");
                        lUPDistrictlist.add("Kushinagar");
                        lUPDistrictlist.add("Lakhimpur Kheri");
                        lUPDistrictlist.add("Lalitpur");
                        lUPDistrictlist.add("Lucknow");
                        lUPDistrictlist.add("Maharajganj");
                        lUPDistrictlist.add("Mahoba");
                        lUPDistrictlist.add("Mainpuri");
                        lUPDistrictlist.add("Mathura");
                        lUPDistrictlist.add("Mau");
                        lUPDistrictlist.add("Meerut");
                        lUPDistrictlist.add("Mirzapur");
                        lUPDistrictlist.add("Moradabad");
                        lUPDistrictlist.add("Muzaffarnagar");
                        lUPDistrictlist.add("Pilibhit");
                        lUPDistrictlist.add("Pratapgarh");
                        lUPDistrictlist.add("Raebareli");
                        lUPDistrictlist.add("Rampur");
                        lUPDistrictlist.add("Saharanpur");
                        lUPDistrictlist.add("Sambhal");
                        lUPDistrictlist.add("Sant Kabir Nagar");
                        lUPDistrictlist.add("Shahjahanpur");
                        lUPDistrictlist.add("Shamli");
                        lUPDistrictlist.add("Shravasti");
                        lUPDistrictlist.add("Siddharthnagar");
                        lUPDistrictlist.add("Sitapur");
                        lUPDistrictlist.add("Sonbhadra");
                        lUPDistrictlist.add("Sultanpur");
                        lUPDistrictlist.add("Unnao");
                        lUPDistrictlist.add("Varanasi");
                        ArrayAdapter<String> lUPAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lUPDistrictlist);
                        lUPAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lUPAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lUPAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "West Bengal":
                        List<String> lWBDistrictlist = new ArrayList<String>();
                        lWBDistrictlist.add("Select District");
                        lWBDistrictlist.add("Alipurduar");
                        lWBDistrictlist.add("Bankura");
                        lWBDistrictlist.add("Paschim Bardhaman");
                        lWBDistrictlist.add("Purba Bardhaman");
                        lWBDistrictlist.add("Birbhum");
                        lWBDistrictlist.add("Cooch Behar");
                        lWBDistrictlist.add("Dakshin Dinajpur");
                        lWBDistrictlist.add("Darjeeling");
                        lWBDistrictlist.add("Hooghly");
                        lWBDistrictlist.add("Howrah");
                        lWBDistrictlist.add("Jalpaiguri");
                        lWBDistrictlist.add("Jhargram");
                        lWBDistrictlist.add("Kalimpong");
                        lWBDistrictlist.add("Kolkata");
                        lWBDistrictlist.add("Maldah");
                        lWBDistrictlist.add("Murshidabad");
                        lWBDistrictlist.add("Nadia");
                        lWBDistrictlist.add("North 24 Parganas");
                        lWBDistrictlist.add("Paschim Medinipur");
                        lWBDistrictlist.add("Purba Medinipur");
                        lWBDistrictlist.add("Purulia");
                        lWBDistrictlist.add("South 24 Parganas");
                        lWBDistrictlist.add("Uttar Dinajpur");
                        ArrayAdapter<String> lWBAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lWBDistrictlist);
                        lWBAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lWBAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lWBAdapter);
                        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i!=0)
                                    mDistrictString = mDistrict.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    default:
                        Log.w(TAG,"Default value: ");
                        List<String> lDfDistrictlist = new ArrayList<String>();
                        lDfDistrictlist.add("Select District");
                        ArrayAdapter<String> lDfAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lDfDistrictlist);
                        lDfAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lDfAdapter.notifyDataSetChanged();
                        mDistrict.setAdapter(lDfAdapter);
                        break;
                }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
