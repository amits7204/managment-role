package com.example.jeevanjyoti.retrofit;

import com.example.jeevanjyoti.otpPojo.OtpRoot;
import com.example.jeevanjyoti.registerencapsulation.UserRegister;
import com.example.jeevanjyoti.userPojo.UserRoot;
import com.example.jeevanjyoti.volunteerPojo.VolunteerRoot;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserRegisterApi {
    @POST("/registration_form/")
    @FormUrlEncoded
    Call<UserRegister> sendUserData(@Field("name") String name,
                                    @Field("unique_id") String uniqueId,
                                    @Field("father_husband_name") String fatherName,
                                    @Field("mother_name") String motherName,
                                    @Field("mobile") String mobile,
                                    @Field("gender") String gender,
                                    @Field("DOB") String DOB,
                                    @Field("marital_status") String marital,
                                    @Field("education") String education,
                                    @Field("education_status") String education_status,
                                    @Field("education_description") String eduDisc,
                                    @Field("occupation") String occupation,
                                    @Field("occupation_description") String occupation_description,
                                    @Field("flat_room_block_no") String flat_room_block_no,
                                    @Field("premises_building_villa") String premises_building_villa,
                                    @Field("road_street_lane") String road_street_lane,
                                    @Field("area_locality_taluk") String aArea,
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
}
