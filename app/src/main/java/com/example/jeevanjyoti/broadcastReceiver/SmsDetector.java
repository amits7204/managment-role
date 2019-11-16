package com.example.jeevanjyoti.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class SmsDetector extends BroadcastReceiver {
    private static Common.OTPListener mListener; // this listener will do the magic of throwing the extracted OTP to all the bound views.
    private static final String TAG = "SmsDetector";
    @Override
    public void onReceive(Context context, Intent intent) {

        // this function is trigged when each time a new SMS is received on device.

        Bundle data = intent.getExtras();

        Object[] pdus = new Object[0];
        if (data != null) {
            pdus = (Object[]) data.get("pdus"); // the pdus key will contain the newly received SMS
        }

        if (pdus != null) {
            for (Object pdu : pdus) { // loop through and pick up the SMS of interest
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                Log.w(TAG,"Get SMS Receiver: "+smsMessage.getMessageBody());
                // your custom logic to filter and extract the OTP from relevant SMS - with regex or any other way.
                String message = smsMessage.getDisplayMessageBody().replaceAll("\\D", "");
                Log.w("SmsReceiver", "message: " + message);
                Intent myIntent = new Intent("otp");
                myIntent.putExtra("message", message);
                LocalBroadcastManager.getInstance(context).sendBroadcast(myIntent);
                if (mListener!=null)
                    mListener.onOtpReceived(message);
                break;
            }
        }
    }

    public static void bindListener(Common.OTPListener listener) {
        mListener = listener;
    }

    public static void unbindListener() {
        mListener = null;
    }
}
