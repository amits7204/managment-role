package com.example.jeevanjyoti.retrofit;

import com.example.jeevanjyoti.registerencapsulation.UserRegister;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserRegisterApi {
    @POST("/registration_form/")
    @FormUrlEncoded
    Call<UserRegister> sendUserData(@Field("family_unique_id") String family_unique_id,
                                    @Field("name") String name,
                                    @Field("father_husband_name") String fatherName,
                                    @Field("mother_name") String motherName,
                                    @Field("mobile") String mobile,
                                    @Field("gender") String gender,
                                    @Field("DOB") String DOB,
                                    @Field("marital_status") String marital,
                                    @Field("education") String education,
                                    @Field("education_status") String education_status,
                                    @Field("occupation") String occupation,
                                    @Field("occupation_description") String occupation_description,
                                    @Field("flat_room_block_no") String flat_room_block_no,
                                    @Field("premises_building_villa") String premises_building_villa,
                                    @Field("road_street_lane") String road_street_lane,
                                    @Field("pin_code") String pin_code,
                                    @Field("state") String state,
                                    @Field("district") String district);

    @POST("/admin_registration/")
    @FormUrlEncoded
    Call<AdminRegister> sendAdminNumber(@Field("mobile") String mobile);

    @POST("/admin_registration/")
    @FormUrlEncoded
    Call<AdminOtpVerify> sendOtpNumber(@Field("otp") String otp);

    @Multipart
    @POST("/volunteer_registration/")
    Call<Volunteer> registerVolunteer(@Part MultipartBody.Part file,
                                      @Part("gender") RequestBody gender,
                                      @Part("name") RequestBody name,
                                      @Part("mobile") RequestBody mobile,
                                      @Part("address") RequestBody address);
}
