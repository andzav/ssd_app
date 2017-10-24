package com.example.a1.myapplication.apihandlers;

import android.app.Application;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static ApiCalls loginAPI;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://someleltest.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        loginAPI = retrofit.create(ApiCalls.class);
    }

    public static ApiCalls getApi() {
        return loginAPI;
    }
}