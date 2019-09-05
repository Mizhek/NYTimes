package com.example.nytimes;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static MostPopularApi mostPopularApi;
    private Retrofit mRetrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.nytimes.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mostPopularApi = mRetrofit.create(MostPopularApi.class);


    }

    public static MostPopularApi getMostPopularApi() {
        return mostPopularApi;
    }

}

