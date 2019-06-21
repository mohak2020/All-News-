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


    // NewsSource source1, NewsSource source2, NewsSource source3


    public NewsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        Log.d(TAG, "getItem: ");

        NewsFragment newsFragment = null;

        newsFragment = NewsFragment.newInstance(i);
        return newsFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
