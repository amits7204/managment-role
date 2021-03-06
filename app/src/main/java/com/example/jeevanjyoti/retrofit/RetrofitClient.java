package com.example.jeevanjyoti.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://35.224.167.35/";
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

    public static UserRegisterApi postUserdata(){
        return getClient().create(UserRegisterApi.class);
    }

}
