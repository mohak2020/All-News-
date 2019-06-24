package com.example.allnews;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.allnews.adapter.NewsAdapter;
import com.example.allnews.model.articles.Articles;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity implements NewsAdapter.NewsAdapterOnclickHandler {

    private static final String TAG = "FavoriteActivity";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    ArrayList<Articles> mArticles;
    RecyclerView mRecyclerView;
    NewsAdapter mNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Log.d(TAG, "onCreate: ");

        mArticles = new ArrayList<>();
        mRecyclerView = findViewById(R.id.favorite_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("articles");

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Articles articles = dataSnapshot.getValue(Articles.class);
                mArticles.add(articles);


                Log.d(TAG, "onChildAdded: " + articles.getTitle());
                initRecycleView();



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Articles articles = dataSnapshot.getValue(Articles.class);
                Log.d(TAG, "onChildChanged: ");
                initRecycleView();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {



            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);




    }

    private void initRecycleView() {
        Log.d(TAG, "initRecycleView: ");
        mNewsAdapter = new NewsAdapter(this, mArticles, (NewsAdapter.NewsAdapterOnclickHandler) this);
        mRecyclerView.setAdapter(mNewsAdapter);


    }

    @Override
    public void onItemClick(Articles article) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("article", article);

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
