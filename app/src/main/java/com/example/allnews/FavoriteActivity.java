package com.example.allnews;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.allnews.adapter.NewsAdapter;
import com.example.allnews.model.articles.Articles;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child(user.getUid());

//        Button button = (Button)findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//
//                finish();
//                startActivity(getIntent());
////                Intent intent = new Intent(v.getContext(), FavoriteActivity.class);
////                v.getContext().startActivity(intent);
//            }
//        });



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
//                mArticles.remove(dataSnapshot.getValue(Articles.class));
//                initRecycleView();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                mArticles.remove(dataSnapshot.getValue(Articles.class));
//                mNewsAdapter.notifyDataSetChanged();
//                initRecycleView();


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
    protected void onRestart() {
        super.onRestart();
        Intent previewMessage = new Intent(FavoriteActivity.this, FavoriteActivity.class);
        startActivity(previewMessage);
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
//       finish();
//       startActivity(getIntent());
        Log.d(TAG, "onResume: ");
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        mDatabaseReference = mFirebaseDatabase.getReference().child("articles");

//
//        mChildEventListener = new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                Articles articles = dataSnapshot.getValue(Articles.class);
//                if(mArticles.contains(articles)) {
//
//                }else
//                    mArticles.add(articles);
//
//                Log.d(TAG, "onChildAdded: " + articles.getTitle());
//                initRecycleView();
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Articles articles = dataSnapshot.getValue(Articles.class);
//                Log.d(TAG, "onChildChanged: ");
////                mArticles.remove(dataSnapshot.getValue(Articles.class));
////                initRecycleView();
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
////                mArticles.remove(dataSnapshot.getValue(Articles.class));
////                mNewsAdapter.notifyDataSetChanged();
////                initRecycleView();
//
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        };
//        mDatabaseReference.addChildEventListener(mChildEventListener);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        // mDatabaseReference.addChildEventListener(mChildEventListener);

    }


}
