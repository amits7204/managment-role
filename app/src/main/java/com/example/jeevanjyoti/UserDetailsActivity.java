package com.example.jeevanjyoti;

import android.Manifest;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jeevanjyoti.adapter.UserAdapter;
import com.example.jeevanjyoti.retrofit.RetrofitClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;
import com.example.jeevanjyoti.userPojo.UserRoot;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private UserRoot mUserData = new UserRoot();
    private UserAdapter mUserAdapter;
    private ProgressBar mUserProgressBar;
    private static final String TAG = "UserDetailsActivity";
    private DownloadManager downloadManager;
    private long refid;
    private Uri Download_Uri;
    ArrayList<Long> list = new ArrayList<>();
    BroadcastReceiver onComplete;
    ImageView mDownloadImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list_recycler_view);
        mRecyclerView = findViewById(R.id.user_recylerview);
        mUserProgressBar = findViewById(R.id.user_progressbar);
        mUserProgressBar.setVisibility(View.VISIBLE);
        mDownloadImageView = findViewById(R.id.download_image);
        getUserData();
        downLoadFile();
//        if (!isStoragePermissionGranted()){
//            downLoadFile();
//        }
    }

    public void getUserData(){
        UserRegisterApi lUserDataApi = RetrofitClient.postUserdata();
        Call<UserRoot> lJsonObject = lUserDataApi.fetchUserData();
        lJsonObject.enqueue(new Callback<UserRoot>() {
            @Override
            public void onResponse(Call<UserRoot> call, Response<UserRoot> response) {
                if (response.isSuccessful()){
                    Log.w(TAG,"Get User Response: "+response.body().getData());
                    mUserData = response.body();
                    mUserAdapter = new UserAdapter(getApplicationContext(), mUserData);
                    LinearLayoutManager lLinearLayOutManager = new LinearLayoutManager(getApplicationContext());
                    mRecyclerView.setLayoutManager(lLinearLayOutManager);
                    mRecyclerView.setAdapter(mUserAdapter);
                    mUserProgressBar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(getApplicationContext(),"Something is wrong", Toast.LENGTH_SHORT).show();
                    mUserProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<UserRoot> call, Throwable t) {
                mUserProgressBar.setVisibility(View.GONE);
            }
        });
    }

    public void downLoadFile(){
         onComplete = new BroadcastReceiver() {

            public void onReceive(Context ctxt, Intent intent) {




                long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);


                Log.e("IN", "Reference id" + referenceId);

                list.remove(referenceId);


                if (list.isEmpty())
                {


                    Log.e("INSIDE", "" + referenceId);
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(UserDetailsActivity.this)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle("GadgetSaint")
                                    .setContentText("All Download completed");


                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(455, mBuilder.build());


                }

            }
        };
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


        Download_Uri = Uri.parse("http://35.224.167.35/media/excel_file/output.xlsx");

        mDownloadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                Toast.makeText(getApplicationContext(),"Downloading start",Toast.LENGTH_SHORT).show();
                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setAllowedOverRoaming(false);
                request.setTitle("Jeevan Jyoti Downloading " + "user" + ".xlsx");
                request.setDescription("Downloading " + "JeevanJyoti User" + ".xlsx");
                request.setVisibleInDownloadsUi(true);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Jeevan Jyoti/"  + "/" + "user" + ".xlsx");


                refid = downloadManager.enqueue(request);


                Log.e("OUT", "REFID" + refid);

                list.add(refid);
            }
        });



    }
//    public  boolean isStoragePermissionGranted() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    == PackageManager.PERMISSION_GRANTED) {
//                return true;
//            } else {
//
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                return false;
//            }
//        }
//        else { //permission is automatically granted on sdk<23 upon installation
//            return true;
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(onComplete);
    }
}
