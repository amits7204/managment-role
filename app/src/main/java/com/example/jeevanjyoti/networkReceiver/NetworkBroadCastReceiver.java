package com.example.jeevanjyoti.networkReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtils.getConnectivityStatusString(context);

        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
}
