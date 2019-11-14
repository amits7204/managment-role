package com.example.jeevanjyoti;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import com.example.jeevanjyoti.retrofit.RetrofitClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;
import com.example.jeevanjyoti.retrofit.Volunteer;

import java.io.File;

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
    private final static int PERMISSIONS_REQUEST_CODE = 1;
    private Uri mUri;
    private File mFile;
    private DisplayMetrics mDisplayMetrics;
    private int mWidth, mHieght;
    LinearLayout mMaleLinearLayout, mFemaleLinearLayout;
    private View.OnClickListener mOnClickListener;
    private String mGender;
    public String mMediaPath;
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
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Slect Gallery", Toast.LENGTH_SHORT).show();
                getImageFromeGallery();
            }
        });
        getGender();
        mMaleLinearLayout.setOnClickListener(mOnClickListener);
        mFemaleLinearLayout.setOnClickListener(mOnClickListener);
        getGender();
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
        mGalleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(Intent.createChooser(mGalleryIntent, "Select Image From Gallery"), 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 0 && resultCode == RESULT_OK) {

                getCropImage();

            } else if (requestCode == 2) {

                if (data != null) {

                    mUri = data.getData();

                    getCropImage();

                }
            } else if (requestCode == 1) {

                if (data != null) {

                    Bundle bundle = data.getExtras();

                    Bitmap bitmap = null;
                    if (bundle != null) {
                        bitmap = bundle.getParcelable("data");
                    }
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(mUri
                            , filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mMediaPath = cursor.getString(columnIndex);
                    Log.w(TAG,"MEDIA PATH string: "+ mMediaPath);
                    // Set the Image in ImageView for Previewing the Media
                    mImageView.setImageBitmap(BitmapFactory.decodeFile(mMediaPath));
                    cursor.close();
                    mImageView.setImageBitmap(bitmap);

                }
            }
        }catch (Exception e){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    public void getCropImage() {

        // Image Crop Code
        try {
            mCropIntent = new Intent("com.android.camera.action.CROP");

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

        }
    }

    public void getRespose(){
        mProgressDialog.show();
        final String lNameString = mNameEditText.getText().toString();
        final String lMobileNumberString = mMobileNumberEditText.getText().toString();
        final String lFullAddressString = mFullAddressEditText.getText().toString();
            Log.w(TAG,"Onclick Button");
            mVolunteer.setFullName(lNameString);
            mVolunteer.setMobileNumber(lMobileNumberString);
            mVolunteer.setAddress(lFullAddressString);
            // Map is used to multipart the file using okhttp3.RequestBody
            Log.w(TAG,"MEDIA PATH: "+ mMediaPath);

            File file = new File(mMediaPath);
            String root = Environment.getExternalStorageState(file);
//            file.mkdir();
            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), root);
            final MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData(
                    "file", root, requestBody);
//                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
            RequestBody lNameRequest = RequestBody.create(MediaType.parse("text/plain")
                    , lNameString);
            RequestBody lMobileRequest = RequestBody.create(MediaType.parse("text/plain")
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
                    Log.w("RegisterActivity", "Response: " + response+ " File Path: "+ fileToUpload.body());
                    Intent lIntent = new Intent(VolunteerActivity.this, OtpVerficationActivity.class);
                    startActivity(lIntent);
                    mProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<Volunteer> call, Throwable t) {
                    Log.w("RegisterActivity", "Response Failed: " + t.toString() + call.toString());
                    mProgressDialog.dismiss();
                }
            });
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
