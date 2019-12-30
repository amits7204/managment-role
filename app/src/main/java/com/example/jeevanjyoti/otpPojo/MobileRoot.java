package com.example.jeevanjyoti.otpPojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileRoot {

    /**
     * mobile : 8317363156
     */
    @SerializedName("mobile")
    @Expose
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
