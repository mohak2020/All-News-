package com.example.allnews.network;

import com.example.allnews.model.NewsSource;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsAPI {

    @GET("v2/everything?language=en&domains=nytimes.com&apiKey=332458b7bec94cbba64fef7ab6f1725a")
    Call<NewsSource> getNytimesNews();

    @GET("v2/everything?language=en&domains=wsj.com&apiKey=332458b7bec94cbba64fef7ab6f1725a")
    Call<NewsSource> getWsjNews();

    @GET("v2/everything?language=en&domains=bbc.com&apiKey=332458b7bec94cbba64fef7ab6f1725a")
    Call<NewsSource> getBbcNews();




}
