package com.example.jeevanjyoti.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitUserClient {

    private static final String BASE_URL = "http://35.222.58.94/";
//    private static final String BASE_URL = "http://192.168.1.8:80/";

    private static Retrofit mRetrofit = null;

    private static Retrofit getClient() {
        HttpLoggingInterceptor lHttp = new HttpLoggingInterceptor();
        lHttp.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient lClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(lHttp)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(lClient)
                .build();
        return mRetrofit;
    }

    public static UserRegisterApi userdata(){
        return getClient().create(UserRegisterApi.class);
    }

}
