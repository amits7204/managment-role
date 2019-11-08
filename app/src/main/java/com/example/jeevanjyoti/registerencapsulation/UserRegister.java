package com.example.jeevanjyoti.registerencapsulation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRegister {
    @SerializedName("family_unique_id")
    @Expose
    private String mFamilyId;
    @SerializedName("name")
    @Expose
    private String mFullName;
    @SerializedName("father_husband_name")
    @Expose
    private String mFatherName;
    @SerializedName("mother_name")
    @Expose
    private String mMotherName;
    @SerializedName("mobile")
    @Expose
    private String mMobileNumber;
    @SerializedName("gender")
    @Expose
    private String mGender;
    @SerializedName("DOB")
    @Expose
    private String mDob;
    @SerializedName("marital_status")
    @Expose
    private String mMaritalStatus;
    @SerializedName("education")
    @Expose
    private String mQualification;
    @SerializedName("education_status")
    @Expose
    private String mEducationStatus;
    @SerializedName("occupation")
    @Expose
    private String mOccupation;
    @SerializedName("occupation_description")
    @Expose
    private String mOccupationDescription;
    @SerializedName("flat_room_block_no")
    @Expose
    private String mFlat;
    @SerializedName("premises_building_villa")
    @Expose
    private String mBuilding;
    @SerializedName("road_street_lane")
    @Expose
    private String mArea;

    public String getmFamilyId() {
        return mFamilyId;
    }

    public void setmFamilyId(String mFamilyId) {
        this.mFamilyId = mFamilyId;
    }

    public String getmEducationStatus() {
        return mEducationStatus;
    }

    public void setmEducationStatus(String mEducationStatus) {
        this.mEducationStatus = mEducationStatus;
    }

    public String getmPinCode() {
        return mPinCode;
    }

    public void setmPinCode(String mPinCode) {
        this.mPinCode = mPinCode;
    }

    @SerializedName("pin_code")
    @Expose
    private String mPinCode;
    @SerializedName("state")
    @Expose
    private String mSelectState;
    @SerializedName("district")
    @Expose
    private String mDistrict;

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String aFullName) {
        mFullName = aFullName;
    }

    public String getFatherName() {
        return mFatherName;
    }

    public void setFatherName(String aFatherName) {
        mFatherName = aFatherName;
    }

    public String getMotherName() {
        return mMotherName;
    }

    public void setMotherName(String aMotherName) {
        mMotherName = aMotherName;
    }

    public String getMobileNumber() {
        return mMobileNumber;
    }

    public void setMobileNumber(String aMobileNumber) {
        mMobileNumber = aMobileNumber;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String aGender) {
        mGender = aGender;
    }

    public String getDob() {
        return mDob;
    }

    public void setDob(String aDob) {
        mDob = mDob;
    }

    public String getMaritalStatus() {
        return mMaritalStatus;
    }

    public void setMaritalStatus(String aMaritalStatus) {
        mMaritalStatus = aMaritalStatus;
    }

    public String getQualification() {
        return mQualification;
    }

    public void setQualification(String aQualification) {
        mQualification = aQualification;
    }

    public String getOccupation() {
        return mOccupation;
    }

    public void setOccupation(String aOccupation) {
        mOccupation = aOccupation;
    }

    public String getOccupationDescription() {
        return mOccupationDescription;
    }

    public void setOccupationDescription(String aOccupationDescription) {
        mOccupationDescription = aOccupationDescription;
    }

    public String getFlat() {
        return mFlat;
    }

    public void setFlat(String aFlat) {
        mFlat = aFlat;
    }

    public String getBuilding() {
        return mBuilding;
    }

    public void setBuilding(String aBuilding) {
        mBuilding = aBuilding;
    }

    public String getArea() {
        return mArea;
    }

    public void setArea(String aArea) {
        mArea = aArea;
    }

    public String getSelectState() {
        return mSelectState;
    }

    public void setSelectState(String aSelectState) {
        mSelectState = aSelectState;
    }

    public String getDistrict() {
        return mDistrict;
    }

    public void setDistrict(String aDistrict) {
        mDistrict = aDistrict;
    }
}
