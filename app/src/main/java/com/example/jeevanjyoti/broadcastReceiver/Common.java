package com.example.jeevanjyoti.broadcastReceiver;

public interface Common {
    interface OTPListener{
        void onOtpReceived(String otp);
    }
}
