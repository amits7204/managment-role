package com.example.jeevanjyoti;

import android.graphics.Bitmap;

public class Volunteer {

    private Bitmap mProfileImage;
    private String mGender;
    private String mFullName;
    private String mMobileNumber;
    private String mAddress;

    public Bitmap getProfileImage() {
        return mProfileImage;
    }

    public void setProfileImage(Bitmap aProfileImage) {
        this.mProfileImage = aProfileImage;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String aGender) {
        this.mGender = aGender;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String aFullName) {
        this.mFullName = aFullName;
    }

    public String getMobileNumber() {
        return mMobileNumber;
    }

    public void setMobileNumber(String aMobileNumber) {
        this.mMobileNumber = aMobileNumber;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String aAddress) {
        this.mAddress = aAddress;
    }
}
