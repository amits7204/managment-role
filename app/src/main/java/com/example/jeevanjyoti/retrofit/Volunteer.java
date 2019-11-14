package com.example.jeevanjyoti.retrofit;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Volunteer {
    @SerializedName("image")
    @Expose
    private String mProfileImage;
    @SerializedName("gender")
    @Expose
    private String mGender;
    @SerializedName("name")
    @Expose
    private String mFullName;
    @SerializedName("mobile")
    @Expose
    private String mMobileNumber;
    @SerializedName("address")
    @Expose
    private String mAddress;

//    public Bitmap getProfileImage() {
//        return mProfileImage;
//    }
//
//    public void setProfileImage(Bitmap aProfileImage) {
//        this.mProfileImage = aProfileImage;
//    }

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
