package com.example.allnews.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.allnews.NewsFragment;
import com.example.allnews.model.NewsSource;
import com.example.allnews.model.articles.Articles;
import com.example.allnews.network.NewsAPI;
import com.example.allnews.network.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsPageAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "NewsPageAdapter";

    Call<NewsSource> call;
    ArrayList<Articles> mArticles = new ArrayList<>();
    ArrayList<Articles> mArticles2 = new ArrayList<>();
    ArrayList<Articles> mArticles3 = new ArrayList<>();

    NewsSource mSource1;
    NewsSource mSource2;
    NewsSource mSource3;
    Bundle bundle;

   // NewsSource source1, NewsSource source2, NewsSource source3


    public NewsPageAdapter(FragmentManager fm, Bundle bundle) {
        super(fm);
//        mSource1 = source1;
//        mSource2 = source2;
//        mSource3 = source3;
        Log.d(TAG, "NewsPageAdapter: constructor ");
        mSource1 = bundle.getParcelable("source1");
        mSource2 = bundle.getParcelable("source2");
        mSource3 = bundle.getParcelable("source3");

    }

    @Override
    public Fragment getItem(int i) {

        Log.d(TAG, "getItem: "+mSource3);

        NewsFragment newsFragment = new NewsFragment();

        NewsAPI newsAPI = RetrofitInstance
                .getRetrofitInstance().create(NewsAPI.class);

        Bundle bundle = new Bundle();

        switch (i) {
            case 0:

                bundle.putParcelable("source", mSource1);
                newsFragment.setArguments(bundle);
                break;
            case 1:
                bundle.putParcelable("source", mSource2);
                newsFragment.setArguments(bundle);
                break;
            case 2:
                bundle.putParcelable("source", mSource3);
                newsFragment.setArguments(bundle);
                break;

            default:
                bundle.putParcelable("source", mSource1);
                newsFragment.setArguments(bundle);

        }

            return newsFragment;


        }

//        switch (i) {
//            case 0:
//                call = newsAPI.getWsjNews();
//                //Call<NewsSource> call = newsAPI.getWsjNews();
//
//                call.enqueue(new Callback<NewsSource>() {
//                    @Override
//                    public void onResponse(Call<NewsSource> call, Response<NewsSource> response) {
//
//                        NewsSource newsSource = response.body();
//                        mArticles = newsSource.getArticles();
//                        Bundle bundle = new Bundle();
//                        bundle.putParcelable("news_source",newsSource);
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<NewsSource> call, Throwable t) {
//
//                    }
//                });
//
//                break;
//            case 1:
//                call = newsAPI.getBbcNews();
//                call.enqueue(new Callback<NewsSource>() {
//                    @Override
//                    public void onResponse(Call<NewsSource> call, Response<NewsSource> response) {
//
//                        NewsSource newsSource = response.body();
//                        mArticles = newsSource.getArticles();
//                        Bundle bundle = new Bundle();
//                        bundle.putParcelable("news_source",newsSource);
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<NewsSource> call, Throwable t) {
//
//                    }
//                });
//                break;
//            case 2:
//                call = newsAPI.getNytimesNews();
//                call.enqueue(new Callback<NewsSource>() {
//                    @Override
//                    public void onResponse(Call<NewsSource> call, Response<NewsSource> response) {
//
//                        NewsSource newsSource = response.body();
//                        mArticles = newsSource.getArticles();
//                        Bundle bundle = new Bundle();
//                        bundle.putParcelable("news_source",newsSource);
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<NewsSource> call, Throwable t) {
//
//                    }
//                });
//                break;
//
//            default:
//                call = newsAPI.getWsjNews();
//                call.enqueue(new Callback<NewsSource>() {
//                    @Override
//                    public void onResponse(Call<NewsSource> call, Response<NewsSource> response) {
//
//                        NewsSource newsSource = response.body();
//                        mArticles = newsSource.getArticles();
//                        Bundle bundle = new Bundle();
//                        bundle.putParcelable("news_source",newsSource);
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<NewsSource> call, Throwable t) {
//
//                    }
//                });
//
//        }





    @Override
    public int getCount() {
        return 3;
    }

    public void apiCall() {
        call.enqueue(new Callback<NewsSource>() {
            @Override
            public void onResponse(Call<NewsSource> call, Response<NewsSource> response) {

                NewsSource newsSource = response.body();
                //Log.d(TAG, "onResponse: " + newsSource.getArticles().get(3).getTitle());
            }

            @Override
            public void onFailure(Call<NewsSource> call, Throwable t) {

            }
        });
    }
}
