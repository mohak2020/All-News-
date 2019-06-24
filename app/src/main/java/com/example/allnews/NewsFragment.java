package com.example.allnews;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.allnews.adapter.NewsAdapter;
import com.example.allnews.model.NewsSource;
import com.example.allnews.model.articles.Articles;
import com.example.allnews.network.NewsAPI;
import com.example.allnews.network.RetrofitInstance;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements NewsAdapter.NewsAdapterOnclickHandler {

    private static final String TAG = "NewsFragment";
    NewsSource mNewsSource;
    ArrayList<Articles> mArticles;
    RecyclerView mRecyclerView;
    NewsAdapter mNewsAdapter;

    int mIndex;
    Call<NewsSource> call;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;


    public NewsFragment() {
        // Required empty public constructor
        Log.d(TAG, "NewsFragment: ");
    }

    public static NewsFragment newInstance(int index) {


        NewsFragment newsFragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        newsFragment.setArguments(bundle);
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
        setHasOptionsMenu(true);

        mIndex = getArguments().getInt("index");


        NewsAPI newsAPI = RetrofitInstance
                .getRetrofitInstance().create(NewsAPI.class);

        switch (mIndex) {
            case 0:
                call = newsAPI.getNytimesNews();
                break;
            case 1:
                call = newsAPI.getBbcNews();
                break;
            case 2:
                call = newsAPI.getWsjNews();
                break;

            default:
                call = newsAPI.getNytimesNews();
                break;

        }



        call.enqueue(new Callback<NewsSource>() {
            @Override
            public void onResponse(Call<NewsSource> call, Response<NewsSource> response) {

                 NewsSource newsSource = response.body();
                Log.d(TAG, "onResponse: " + newsSource.getArticles().get(0).getTitle());
                mArticles = newsSource.getArticles();
                mNewsSource = newsSource;
                //Log.d(TAG, "onCreateView: "+ mArticles.get(0).getTitle());
                initRecycleView();
                //onSucess(newsSource);

//                if (response.isSuccessful()){
//                    onSucess(newsSource);
//                }

            }

            @Override
            public void onFailure(Call<NewsSource> call, Throwable t) {

            }



        });

        //WidgetUtils.updateWidgetsData(getActivity(), mNewsSource);
        //Log.d(TAG, "onCreateView: "+ mArticles.get(0).getTitle());




        return view;
    }

    private void initRecycleView() {
        mNewsAdapter = new NewsAdapter(getContext(), mArticles, this);
        mRecyclerView.setAdapter(mNewsAdapter);

    }

    boolean flag =true;
    private void onSucess(NewsSource newsSource){
        if(flag) {
           // WidgetUtils.updateWidgetsData(getActivity(), newsSource);
            Log.d(TAG, "onSucess: " + newsSource.getArticles().get(1).getTitle());
            flag =false;
        }
    }


    @Override
    public void onItemClick(Articles article) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("article", article);

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.favorites){
            Toast.makeText(getActivity(), "favorite clicked",
                    Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getActivity(),FavoriteActivity.class);
            startActivity(intent);


        }else {
            Toast.makeText(getActivity(), "sign out clicked",
                    Toast.LENGTH_LONG).show();
        }
        return true;

    }
}


//
//mFirebaseDatabase = FirebaseDatabase.getInstance();
//        mDatabaseReference = mFirebaseDatabase.getReference().child("articles");
//
//        mChildEventListener = new ChildEventListener() {
//@Override
//public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//        Articles articles = dataSnapshot.getValue(Articles.class);
//        Log.d(TAG, "onChildChanged: "+ articles.getTitle());
//        mNewsAdapter.setNewsFavorite(articles);
//        mNewsAdapter.notifyDataSetChanged();
//
//        }
//
//@Override
//public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//        }
//
//@Override
//public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//        }
//
//@Override
//public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//        }
//
//@Override
//public void onCancelled(@NonNull DatabaseError databaseError) {
//
//        }
//        };
//        mDatabaseReference.addChildEventListener(mChildEventListener);
//
