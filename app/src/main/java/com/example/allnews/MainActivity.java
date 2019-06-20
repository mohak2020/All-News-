package com.example.allnews;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.allnews.adapter.NewsPageAdapter;
import com.example.allnews.model.NewsSource;
import com.example.allnews.model.articles.Articles;
import com.example.allnews.network.NewsAPI;
import com.example.allnews.network.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
//    NewsPageAdapter mPagerAdapter;
//    ViewPager viewPager;
//
//    ArrayList<Articles> mArticles;
//    ArrayList<Articles> mArticles2;
//    ArrayList<Articles> mArticles3;
//
//    NewsSource source1;
//    NewsSource source2;
//    NewsSource source3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: called");

//
//        viewPager = findViewById(R.id.pager);
//
//
//        NewsAPI newsAPI = RetrofitInstance
//                .getRetrofitInstance().create(NewsAPI.class);
//
//        final Bundle  bundle = new Bundle();
//        Call<NewsSource> call = newsAPI.getNytimesNews();
//
//        call.enqueue(new Callback<NewsSource>() {
//            @Override
//            public void onResponse(Call<NewsSource> call, Response<NewsSource> response) {
//
//                //NewsSource newsSource = response.body();
//                source1 = response.body();
//
//                Log.d(TAG, "onResponse: " + source1.getArticles().get(3).getTitle());
//                mArticles = source1.getArticles();
//                bundle.putParcelable("source1", source1);
//
//            }
//
//            @Override
//            public void onFailure(Call<NewsSource> call, Throwable t) {
//
//            }
//        });
//
//        Call<NewsSource> call2 = newsAPI.getBbcNews();
//
//        call2.enqueue(new Callback<NewsSource>() {
//            @Override
//            public void onResponse(Call<NewsSource> call2, Response<NewsSource> response) {
//
//                source2 = response.body();
//                Log.d(TAG, "onResponse: " + source2.getArticles().get(3).getTitle());
//                mArticles2 = source2.getArticles();
//                bundle.putParcelable("source2", source2);
//
//            }
//
//            @Override
//            public void onFailure(Call<NewsSource> call2, Throwable t) {
//
//            }
//        });
//
//        Call<NewsSource> call3 = newsAPI.getWsjNews();
//
//        call3.enqueue(new Callback<NewsSource>() {
//            @Override
//            public void onResponse(Call<NewsSource> call3, Response<NewsSource> response) {
//                NewsSource newsSource = response.body();
//                source3 = newsSource;
//                Log.d(TAG, "onResponse: " + source3.getArticles().get(3).getTitle());
//                mArticles3 = source3.getArticles();
//                bundle.putParcelable("source3", source3);
//
//            }
//
//            @Override
//            public void onFailure(Call<NewsSource> call3, Throwable t) {
//
//            }
//        });
//
//        //Bundle bundle = new Bundle();
////        bundle.putParcelable("source1", source1);
////        bundle.putParcelable("source2", source2);
////        bundle.putParcelable("source3", source3);
//
//
//        mPagerAdapter = new NewsPageAdapter(getSupportFragmentManager(), bundle);
//        //mPagerAdapter = new NewsPageAdapter(getSupportFragmentManager());
//
//
//        viewPager.setAdapter(mPagerAdapter);
//
//
//    }

    }

}


//        NewsAPI newsAPI = RetrofitInstance
//                .getRetrofitInstance().create(NewsAPI.class);
//
//        Call<NewsSource> call =newsAPI.getWsjNews();
//
//        call.enqueue(new Callback<NewsSource>() {
//            @Override
//            public void onResponse(Call<NewsSource> call, Response<NewsSource> response) {
//
//                NewsSource newsSource = response.body();
//                Log.d(TAG, "onResponse: " + newsSource.getArticles().get(3).getTitle());
//            }
//
//            @Override
//            public void onFailure(Call<NewsSource> call, Throwable t) {
//
//            }
//        });

