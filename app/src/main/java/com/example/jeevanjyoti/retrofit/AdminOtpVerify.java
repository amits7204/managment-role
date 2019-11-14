package com.example.jeevanjyoti.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminOtpVerify {
    @SerializedName("otp")
    @Expose
    public String mOtp;
}
