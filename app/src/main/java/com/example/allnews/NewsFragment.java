package com.example.allnews;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allnews.adapter.NewsAdapter;
import com.example.allnews.model.NewsSource;
import com.example.allnews.model.articles.Articles;
import com.example.allnews.network.NewsAPI;
import com.example.allnews.network.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    private static final String TAG = "NewsFragment";

    ArrayList<Articles> mArticles;
    RecyclerView mRecyclerView;
    NewsAdapter mNewsAdapter;

    NewsSource mSource;


    public NewsFragment() {
        // Required empty public constructor
        Log.d(TAG, "NewsFragment: ");
    }

    public static NewsFragment newInstance(int index){


        NewsFragment newsFragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        return newsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        mArticles = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.news_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);


        NewsAPI newsAPI = RetrofitInstance
                .getRetrofitInstance().create(NewsAPI.class);
//
//        mSource = getArguments().getParcelable("source");
//        mArticles = mSource.getArticles();
        //Log.d(TAG, "onResponse: " + mSource.getArticles().get(3).getTitle());
//        initRecycleView();



        Call<NewsSource> call =newsAPI.getWsjNews();

        call.enqueue(new Callback<NewsSource>() {
            @Override
            public void onResponse(Call<NewsSource> call, Response<NewsSource> response) {

                NewsSource newsSource = response.body();
                Log.d(TAG, "onResponse: " + newsSource.getArticles().get(3).getTitle());
                mArticles = newsSource.getArticles();
                initRecycleView();
            }

            @Override
            public void onFailure(Call<NewsSource> call, Throwable t) {

            }
        });


        return view;
    }

    private void initRecycleView() {
        mNewsAdapter = new NewsAdapter(getContext(), mArticles);
        mRecyclerView.setAdapter(mNewsAdapter);
    }

}



//
//    Call<NewsSource> call;
//    int index = 0;
//        switch (index) {
//                case 0:
//                call = newsAPI.getWsjNews();
//                break;
//                case 1:
//                call = newsAPI.getBbcNews();
//                break;
//                case 2:
//                call = newsAPI.getNytimesNews();
//                break;
//
//default:
//        call = newsAPI.getWsjNews();
//
//
//        }
