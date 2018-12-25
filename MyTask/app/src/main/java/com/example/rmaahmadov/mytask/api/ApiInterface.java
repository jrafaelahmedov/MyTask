package com.example.rmaahmadov.mytask.api;

import com.example.rmaahmadov.mytask.models.News;
import com.example.rmaahmadov.mytask.models.Source;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {
    
    @GET("top-headlines")
    Call<News> getNews(@Query("country") String country, @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<News> getSportNews(@Query("sources") String sources, @Query("apiKey") String apiKey);
}
