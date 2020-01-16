package com.example.jeevanjyoti.retrofit;

import com.example.jeevanjyoti.otpPojo.OtpRoot;
import com.example.jeevanjyoti.otpPojo.RegisterConfirmOtp;
import com.example.jeevanjyoti.userPojo.UserRoot;
import com.example.jeevanjyoti.volunteerPojo.VolunteerRoot;


import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserRegisterApi {
    @Headers({"Content-Type:application/json"})
    @POST("/user_register")
    Call<ResponseBody> sendUserData(@Body Map<String, String> userDetails);

    @Headers({"Content-Type:application/json"})
    @POST("/GetOtp")
    Call<ResponseBody> sendOtp(@Body Map<String, String> userDetails);

    @Headers({"Content-Type:application/json"})
    @POST("/ConfirmOtp")
    Call<RegisterConfirmOtp> confirmOtp(@Body Map<String, String> userMobile);

    @POST("/admin_registration/")
    @FormUrlEncoded
    Call<AdminRegister> sendAdminNumber(@Field("mobile") String mobile);

    @POST("/admin_registration/")
    @FormUrlEncoded
    Call<OtpRoot> sendOtpNumber(@Field("otp") String otp);

    @Multipart
    @POST("volunteer_registration/")
    Call<Volunteer> registerVolunteer(@Part MultipartBody.Part file,
                                      @Part("name") RequestBody name,
                                      @Part("gender") RequestBody gender,
                                      @Part("mobile") RequestBody mobile,
                                      @Part("fathername") RequestBody fathername,
                                      @Part("dateofbirth") RequestBody dateofbirth,
                                      @Part("flat_room_block_no") RequestBody flat_room_block_no,
                                      @Part("premises_building_villa") RequestBody premises_building_villa,
                                      @Part("road_street_lane") RequestBody road_street_lane,
                                      @Part("area_locality_taluk") RequestBody area_locality_taluk,
                                      @Part("pin_code") RequestBody pin_code,
                                      @Part("state") RequestBody state,
                                      @Part("district") RequestBody district);

    @FormUrlEncoded
    @POST("/volunteer_registration/")
    Call<OtpRoot> verifyVolunteerOtp(@Field("otp") String otp);

    @FormUrlEncoded
    @POST("/registration_form/")
    Call<OtpRoot> verifyUserOtp(@Field("otp") String otp);




    @POST("fatch_ragistration_data/")
    Call<UserRoot> fetchUserData();

    @POST("fatch_volunteer_data/")
    Call<VolunteerRoot> fetchVolunteerData();

    @GET("media/excel_file/output.xlsx")
    Call<ResponseBody> fetchUserDataFile();

    @GET("get_users")
    Call<UserRoot> getUserData();
}
