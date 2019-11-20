package com.example.jeevanjyoti;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.jeevanjyoti.retrofit.RetrofitClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;
import com.example.jeevanjyoti.retrofit.Volunteer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolunteerActivity extends AppCompatActivity {
    private static final String TAG = "VolunteerActivity";
    private ImageView mImageView, mMenImageView;
    private TextView mMaleTextView, mFemaleTextView;
    private EditText mNameEditText, mMobileNumberEditText, mFullAddressEditText;
    private Button mButton;
    private Intent mGalleryIntent, mCropIntent;
    private static final int REQUEST_PHOTO_FROM_GOOGLE_PHOTOS = 100;
    private final static int PERMISSIONS_REQUEST_CODE = 1;
    private Uri mUri;
    private File mFile;
    private DisplayMetrics mDisplayMetrics;
    private int mWidth, mHieght;
    LinearLayout mMaleLinearLayout, mFemaleLinearLayout;
    private View.OnClickListener mOnClickListener;
    private String mGender = "";
    public String mMediaPath = "";
    private ProgressDialog mProgressDialog;
    Volunteer mVolunteer = new Volunteer();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volunteer_activity);
        mImageView = findViewById(R.id.profile_image);
        mMaleTextView = findViewById(R.id.male_textview);
        mFemaleTextView = findViewById(R.id.female_textView);
        mNameEditText = findViewById(R.id.name_edit_textview);
        mMobileNumberEditText = findViewById(R.id.mobile_edit_textview);
        mFullAddressEditText = findViewById(R.id.address_textView);
        mButton = findViewById(R.id.button);
        mMaleLinearLayout = findViewById(R.id.male_linear_layout);
        mFemaleLinearLayout = findViewById(R.id.female_linear_layout);
        mMenImageView = findViewById(R.id.men_imageView);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Processing...");
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
                getImageFromeGallery();
//                launchGooglePhotosPicker(VolunteerActivity.this);
            }
        });
        getGender();
        mMaleLinearLayout.setOnClickListener(mOnClickListener);
        mFemaleLinearLayout.setOnClickListener(mOnClickListener);
        registerVolunteer();
    }

    public void getGender(){
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.male_linear_layout:
                        mMaleLinearLayout.setBackground(getDrawable(R.drawable.text_view_shape));
                        mMaleTextView.setTextColor(getResources().getColor(R.color.colorPrimaryBlack));
                        mMenImageView.setImageDrawable(getDrawable(R.drawable.ic_men_black));
                        mFemaleTextView.setTextColor(getResources().getColor(R.color.colorAccent));
                        mFemaleLinearLayout.setBackground(getDrawable(R.drawable.secondary_primary_box));
                        mGender = mMaleTextView.getText().toString();
                        Log.w(TAG,"Gender Status: "+mGender);
                        mVolunteer.setGender(mGender);
                        break;
                    case R.id.female_linear_layout:
                        mFemaleLinearLayout.setBackground(getDrawable(R.drawable.text_view_shape));
                        mFemaleTextView.setTextColor(getResources().getColor(R.color.colorPrimaryBlack));
                        mMaleTextView.setTextColor(getResources().getColor(R.color.colorAccent));
                        mMenImageView.setImageDrawable(getDrawable(R.drawable.ic_men));
                        mMaleLinearLayout.setBackground(getDrawable(R.drawable.secondary_primary_box));

                        mGender = mFemaleTextView.getText().toString();
                        Log.w(TAG,"Gender Status: "+mGender);
                        mVolunteer.setGender(mGender);
                        break;
                }
            }
        };
    }

    public void getImageFromeGallery(){
        mGalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        mGalleryIntent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        mGalleryIntent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(mGalleryIntent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 0 && resultCode == RESULT_OK) {
                Log.w(TAG,"RequestCode: "+requestCode);
                getCropImage();

            }else if (requestCode == 2) {
                Log.w(TAG,"RequestCode: "+requestCode);
                if (data != null) {

                    mUri = data.getData();

                    getCropImage();

                }
            } else if (requestCode == 1) {
                Log.w(TAG,"RequestCode: "+requestCode);
                if (data != null) {

                    Bundle bundle = data.getExtras();

                    Bitmap bitmap = null;
                    if (bundle != null) {
                        bitmap = bundle.getParcelable("data");
                    }
                    Cursor cursor = getContentResolver().query(mUri, null, null, null, null);
                    cursor.moveToFirst();
                    String document_id = cursor.getString(0);
                    document_id = document_id.substring(document_id.lastIndexOf(":")+1);
                    cursor.close();

                    cursor = getContentResolver().query(
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
                    cursor.moveToFirst();
                    mMediaPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    cursor.close();

                    // Set the Image in ImageView for Previewing the Media
                    mImageView.setImageBitmap(BitmapFactory.decodeFile(mMediaPath));
                    cursor.close();
                    mImageView.setImageBitmap(bitmap);

                }
            }
        }catch (Exception e){
            Log.w(TAG, "Failed Exception :"+e.getMessage());
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public void getCropImage() {

        // Image Crop Code
        try {
            mCropIntent = new Intent("com.android.camera.action.CROP");
            Log.w(TAG,"URI: "+mUri);
            mCropIntent.setDataAndType(mUri, "image/*");

            mCropIntent.putExtra("crop", "true");
            mCropIntent.putExtra("outputX", 180);
            mCropIntent.putExtra("outputY", 180);
            mCropIntent.putExtra("aspectX", 3);
            mCropIntent.putExtra("aspectY", 4);
            mCropIntent.putExtra("scaleUpIfNeeded", true);
            mCropIntent.putExtra("return-data", true);

            startActivityForResult(mCropIntent, 1);

        } catch (ActivityNotFoundException e) {
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void getRespose(){
        mProgressDialog.show();
        final String lNameString = mNameEditText.getText().toString();
        final String lMobileNumberString = mMobileNumberEditText.getText().toString();
        final String lFullAddressString = mFullAddressEditText.getText().toString();
        if(lNameString.isEmpty()){
            mNameEditText.setError("Please fill Full name");
            mNameEditText.requestFocus();
        }
        else if (lMobileNumberString.isEmpty()){
            mMobileNumberEditText.setError("Please Fill the mobile number");
            mMobileNumberEditText.requestFocus();
        }
        else if (lFullAddressString.isEmpty()){
            mFullAddressEditText.setError("Please fill the Full Address");
            mFullAddressEditText.requestFocus();
        }else if (mGender.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please choose Your gender", Toast.LENGTH_SHORT).show();
        } else {
            Log.w(TAG, "Onclick Button");
            mVolunteer.setFullName(lNameString);
            mVolunteer.setMobileNumber(lMobileNumberString);
            mVolunteer.setAddress(lFullAddressString);
            // Map is used to multipart the file using okhttp3.RequestBody
            Log.w(TAG, "MEDIA PATH: " + mMediaPath);
            try {
                if (mMediaPath != null) {
                    mFile = new File(mMediaPath);
                } else {
                    Toast.makeText(getApplicationContext(), "Plese choose Profile pic", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), mMediaPath, Toast.LENGTH_SHORT).show();
            }

            String root = Environment.getExternalStorageState(mFile);
            Log.w(TAG, "Root PATH: " + mFile);
//            file.mkdir();
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
                    , lFullAddressString);

            RequestBody lGender = RequestBody.create(MediaType.parse("text/plain"), mGender);
            UserRegisterApi lUserRegisterApi = RetrofitClient.postUserdata();
            Call<Volunteer> lCallUserResponse = lUserRegisterApi
                    .registerVolunteer(fileToUpload, lNameRequest, lMobileRequest,
                            lFUllAddressRequest, lGender);
            lCallUserResponse.enqueue(new Callback<Volunteer>() {
                @Override
                public void onResponse(Call<Volunteer> call, Response<Volunteer> response) {
                    Log.w("RegisterActivity", "Response: " + response.body() + " File Path: " + fileToUpload.toString());
                    if (response.isSuccessful()) {
                        Intent lIntent = new Intent(VolunteerActivity.this, OtpVerficationActivity.class);
                        lIntent.putExtra("vmobile", lMobileNumberString);
                        startActivity(lIntent);
                        mProgressDialog.dismiss();
                    } else {
//                        Log.w(TAG,"500 Internal Error: "+response.body());
                        Toast.makeText(getApplicationContext(), "Something is wrong", Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
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
                getRespose();
            }
        });
    }
}
