package com.example.nytimes.interfaces;

import com.example.nytimes.data.NYTimesApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MostPopularApi {
    @GET("/svc/mostpopular/v2/{mostPopularType}/{period}.json?api-key=X258G8t8AYoGZQMOuneVDsCq3OBFn7Nv")
    Call<NYTimesApi> getArticles(@Path("mostPopularType") String mostPopularType, @Path("period") int period);
}
