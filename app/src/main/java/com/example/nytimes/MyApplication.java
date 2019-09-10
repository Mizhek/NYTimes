package com.example.nytimes;

import android.app.Application;

import com.example.nytimes.Interfaces.MostPopularApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {
    private static MostPopularApi mostPopularApi;

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nytimes.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mostPopularApi = retrofit.create(MostPopularApi.class);


    }

    public static MostPopularApi getMostPopularApi() {
        return mostPopularApi;
    }

}

