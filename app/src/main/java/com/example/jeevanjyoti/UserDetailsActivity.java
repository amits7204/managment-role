package com.example.jeevanjyoti;

import android.Manifest;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.jeevanjyoti.adapter.UserAdapter;
import com.example.jeevanjyoti.database.DBHelper;
import com.example.jeevanjyoti.retrofit.RetrofitUserClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;
import com.example.jeevanjyoti.userPojo.UserRoot;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private UserRoot mUserData = new UserRoot();
    private UserAdapter mUserAdapter;
    private static final String TAG = "UserDetailsActivity";
    private DownloadManager downloadManager;
    private long refid;
    private LottieAnimationView mAnimationView;
    private Uri Download_Uri;
    ArrayList<Long> list = new ArrayList<>();
    BroadcastReceiver onComplete;
    private DBHelper mDB;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list_recycler_view);
        mRecyclerView = findViewById(R.id.user_recylerview);
        mAnimationView = findViewById(R.id.animation_view);
        mAnimationView.playAnimation();
//        mDownloadImageView = findViewById(R.id.download_image);
        mDB = new DBHelper(this);
        getUserData();
    }

    public void getUserData(){
        UserRegisterApi lUserDataApi = RetrofitUserClient.userdata();
        Call<UserRoot> lJsonObject = lUserDataApi.getUserData();
        lJsonObject.enqueue(new Callback<UserRoot>() {
            @Override
            public void onResponse(Call<UserRoot> call, Response<UserRoot> response) {
                if (response.isSuccessful() && response.body()!=null){
                    Log.w(TAG,"Get User Response: "+response.body().getJUser());
                    mUserData = response.body();
                    mUserAdapter = new UserAdapter(getApplicationContext(), mUserData);
                    LinearLayoutManager lLinearLayOutManager = new LinearLayoutManager(getApplicationContext());
                    mRecyclerView.setLayoutManager(lLinearLayOutManager);
                    mRecyclerView.setAdapter(mUserAdapter);
                    getDataFromLocalDatabase(response);
                    mAnimationView.cancelAnimation();
                    mAnimationView.setVisibility(View.GONE);
                }else{
                    Toast.makeText(getApplicationContext(),"Something is wrong", Toast.LENGTH_SHORT).show();
                    mAnimationView.cancelAnimation();
                    mAnimationView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<UserRoot> call, Throwable t) {
                Log.w(TAG,"Failed: "+t.getMessage());
                mAnimationView.cancelAnimation();
                mAnimationView.setVisibility(View.GONE);
            }
        });
    }

    public void getDataFromLocalDatabase(Response<UserRoot> response){
        DBHelper dbHelper = new DBHelper(getApplicationContext());
//        dbHelper.insertData();
        if (response.isSuccessful() && response.body()!=null){

            for (int i = 0; i<response.body().getJUser().size(); i++){
                Log.w(TAG,"Response: GetUser: "+response.body().getJUser().get(i).getFull_name());
                mDB.saveUserData(getApplicationContext(),
                        response.body().getJUser().get(i).getId(),
                        response.body().getJUser().get(i).getUuid(),
                        response.body().getJUser().get(i).getParent(),
                        response.body().getJUser().get(i).getFull_name(),
                        response.body().getJUser().get(i).getFather_name(),
                        response.body().getJUser().get(i).getMother_name(),
                        response.body().getJUser().get(i).getMobile_no(),
                        response.body().getJUser().get(i).getGender(),
                        response.body().getJUser().get(i).getDob(),
                        response.body().getJUser().get(i).getMarital(),
                        response.body().getJUser().get(i).getEducation(),
                        response.body().getJUser().get(i).getEducation_status(),
                        response.body().getJUser().get(i).getEducation_disc(),
                        response.body().getJUser().get(i).getOccupation(),
                        response.body().getJUser().get(i).getOccupation_disc(),
                        response.body().getJUser().get(i).getArea(),
                        response.body().getJUser().get(i).getFlat_no(),
                        response.body().getJUser().get(i).getBuilding_no(),
                        response.body().getJUser().get(i).getRoad_street(),
                        response.body().getJUser().get(i).getPin_code(),
                        response.body().getJUser().get(i).getState(),
                        response.body().getJUser().get(i).getDistrict());
            }
        }

        final Cursor cursor = dbHelper.getuser();

        File sd = Environment.getExternalStorageDirectory();
        String csvFile = "jjUserData.xls";

        File directory = new File(sd.getAbsolutePath());
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {

            //file path
            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("userList", 0);

            sheet.addCell(new Label(0, 0, "id")); // column and row
            sheet.addCell(new Label(1, 0, "UniqueId"));
            sheet.addCell(new Label(2, 0, "parent"));
            sheet.addCell(new Label(3, 0, "Full name"));
            sheet.addCell(new Label(4, 0, "Father Name"));
            sheet.addCell(new Label(5, 0, "Mother Name"));
            sheet.addCell(new Label(6, 0, "mobile"));
            sheet.addCell(new Label(7, 0, "Gender"));
            sheet.addCell(new Label(8, 0, "DOB"));
            sheet.addCell(new Label(9, 0, "Marital"));
            sheet.addCell(new Label(10, 0, "Education"));
            sheet.addCell(new Label(11, 0, "Education Status"));
            sheet.addCell(new Label(12, 0, "Education Discription"));
            sheet.addCell(new Label(13, 0, "Occupation"));
            sheet.addCell(new Label(14, 0, "OccupationDiscription"));
            sheet.addCell(new Label(15, 0, "Area"));
            sheet.addCell(new Label(16, 0, "Flat No"));
            sheet.addCell(new Label(17, 0, "Building No"));
            sheet.addCell(new Label(18, 0, "Road Street"));
            sheet.addCell(new Label(19, 0, "Pin Code"));
            sheet.addCell(new Label(20, 0, "State"));
            sheet.addCell(new Label(21, 0, "District"));

//            DBHelper uknowDatabase = DBHelper.getInstance(getApplicationContext());
//            SQLiteDatabase liteDatabase = uknowDatabase.getReadableDatabase();
            if (cursor.moveToFirst()) {
                do {
                    String lId = cursor.getString(cursor.getColumnIndex(DBHelper.mId));
                    String lUuid = cursor.getString(cursor.getColumnIndex(DBHelper.mUniqueId));
                    String lParent = cursor.getString(cursor.getColumnIndex(DBHelper.mParent));
                    String lFullnmae = cursor.getString(cursor.getColumnIndex(DBHelper.mFullName));
                    String lFatherName = cursor.getString(cursor.getColumnIndex(DBHelper.mFatherName));
                    String lMotherName = cursor.getString(cursor.getColumnIndex(DBHelper.mMotherName));
                    String lMobile = cursor.getString(cursor.getColumnIndex(DBHelper.mMobile));
                    String lGender = cursor.getString(cursor.getColumnIndex(DBHelper.mGender));
                    String lDob = cursor.getString(cursor.getColumnIndex(DBHelper.mDOB));
                    String lMarital = cursor.getString(cursor.getColumnIndex(DBHelper.mMarital));
                    String lEducation = cursor.getString(cursor.getColumnIndex(DBHelper.mQualification));
                    String lEducationStatus = cursor.getString(cursor.getColumnIndex(DBHelper.mEducationStatus));
                    String lEduDisc = cursor.getString(cursor.getColumnIndex(DBHelper.mEducDisc));
                    String lOccupation = cursor.getString(cursor.getColumnIndex(DBHelper.mOccupation));
                    String lOccupationDisc = cursor.getString(cursor.getColumnIndex(DBHelper.mOccupationDisc));
                    String lArea = cursor.getString(cursor.getColumnIndex(DBHelper.mArea));
                    String lFlat = cursor.getString(cursor.getColumnIndex(DBHelper.mFlat));
                    String lBuilding = cursor.getString(cursor.getColumnIndex(DBHelper.mBuilding));
                    String lRoadStreet = cursor.getString(cursor.getColumnIndex(DBHelper.mRoadStreet));
                    String lPinCode = cursor.getString(cursor.getColumnIndex(DBHelper.mPinCode));
                    String lState = cursor.getString(cursor.getColumnIndex(DBHelper.mState));
                    String lDistrict = cursor.getString(cursor.getColumnIndex(DBHelper.mDistrict));


                    Log.w(TAG,"tittle and Phone number: "+lId+" "+lUuid);
                    int i = cursor.getPosition() + 1;
                    sheet.addCell(new Label(0, i, lId));
                    sheet.addCell(new Label(1, i, lUuid));
                    sheet.addCell(new Label(2, i, lParent));
                    sheet.addCell(new Label(3, i, lFullnmae));
                    sheet.addCell(new Label(4, i, lFatherName));
                    sheet.addCell(new Label(5, i, lMotherName));
                    sheet.addCell(new Label(6, i, lMobile));
                    sheet.addCell(new Label(7, i, lGender));
                    sheet.addCell(new Label(8, i, lDob));
                    sheet.addCell(new Label(9, i, lMarital));
                    sheet.addCell(new Label(10, i, lEducation));
                    sheet.addCell(new Label(11, i, lEducationStatus));
                    sheet.addCell(new Label(12, i, lEduDisc));
                    sheet.addCell(new Label(13, i, lOccupation));
                    sheet.addCell(new Label(14, i, lOccupationDisc));
                    sheet.addCell(new Label(15, i, lArea));
                    sheet.addCell(new Label(16, i, lFlat));
                    sheet.addCell(new Label(17, i, lBuilding));
                    sheet.addCell(new Label(18, i, lRoadStreet));
                    sheet.addCell(new Label(19, i, lPinCode));
                    sheet.addCell(new Label(20, i, lState));
                    sheet.addCell(new Label(21, i, lDistrict));
                } while (cursor.moveToNext());
            }
            //closing cursor
            cursor.close();
            workbook.write();
            workbook.close();
            Toast.makeText(getApplication(), "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            Log.w(TAG,"Exception handle: "+e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(onComplete);
    }
}
