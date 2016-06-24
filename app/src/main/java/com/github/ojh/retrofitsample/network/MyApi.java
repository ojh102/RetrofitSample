package com.github.ojh.retrofitsample.network;

import com.github.ojh.retrofitsample.data.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyApi {
    @GET("search/image")
    Call<SearchResult> getImageList(@Query("apikey") String apikey,
                                    @Query("q") String q,
                                    @Query("result") int result,
                                    @Query("pageno") int pageno,
                                    @Query("output") String output);
}