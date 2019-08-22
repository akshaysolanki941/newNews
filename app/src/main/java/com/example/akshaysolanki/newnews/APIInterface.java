package com.example.akshaysolanki.newnews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("top-headlines?country=in")
    Call<FeedGeneral> getNews(@Query("category") String category, @Query("apiKey") String apiKey);

    @GET("everything")
    Call<FeedGeneral> searchNews(@Query("q") String query, @Query("apiKey") String apiKey);
}
