package com.example.allnews;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.allnews.adapter.NewsAdapter;
import com.example.allnews.model.articles.Articles;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    CollapsingToolbarLayout collapsingToolbar;
    Toolbar toolbar;
    FloatingActionButton actionButton;
    private View mPhotoContainerView;
    private ImageView mPhotoView;
    private ImageView mFavoriteView;
    private ImageView mUnFavView;
    NewsAdapter mNewsAdapter;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    boolean addedToFav = true;
    String pushId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        final Articles article = intent.getParcelableExtra("article");


        Log.d(TAG, "onCreate: ");


        TextView titleView = (TextView) findViewById(R.id.article_title);
        TextView bodyView = (TextView) findViewById(R.id.article_body);
        toolbar = (Toolbar) findViewById(R.id.frag_detail_toolbar);
        mPhotoView = (ImageView) findViewById(R.id.photo);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        mFavoriteView = (ImageView) findViewById(R.id.fav_view);
        //mUnFavView = (ImageView) findViewById(R.id.unfav_view);

        EditText editText = (EditText) findViewById(R.id.add_comment);

        titleView.setText(article.getTitle());
        bodyView.setText(article.getContent());


        boolean flag = article.getUrlToImage().contains("https");
        Log.d(TAG, "onBindViewHolder: " + flag);

        String imageUrl = article.getUrlToImage();

        if (flag) {
            Picasso.with(this).load(imageUrl).into(mPhotoView);

        } else {
            imageUrl = article.getUrlToImage().replace("http", "https");
            Picasso.with(this).load(imageUrl).into(mPhotoView);
        }

        //

        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(article.getTitle());
                    //collapsingToolbar.setExpandedTitleMargin(30,0,0,0);
                    actionButton = (FloatingActionButton) findViewById(R.id.share_fab);
                    // actionButton.setVisibility(View.INVISIBLE);
                    mFavoriteView.setVisibility(View.INVISIBLE);

                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle("");//carefull there should a space between double quote otherwise it wont work
                    //actionButton.setVisibility(View.VISIBLE);
                    mFavoriteView.setVisibility(View.VISIBLE);

                    isShow = false;
                }
            }
        });

        //


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("articles");


        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // Log.d(TAG, "onChildAdded: ");
                Articles articles = dataSnapshot.getValue(Articles.class);
                ArrayList<String> stringArrayList = new ArrayList<>();
                stringArrayList.add(articles.getTitle());



                Log.d(TAG, "onChildAdded children:  "+ dataSnapshot.getChildren());

                if (stringArrayList.contains(article.getTitle())) {
                    Log.d(TAG, "onChildAdded Array:  "+stringArrayList.get(0));
                    mFavoriteView.setImageResource(R.drawable.ic_favorite_black_24dp);
                } else {
                    Log.d(TAG, "onChildAdded else:   "+ stringArrayList.get(0));
                    //mFavoriteView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }

                Log.d(TAG, "onChildAdded: " + articles.getTitle());

//                if(article.getTitle().equals(articles)){
//                    //mFavoriteView.setImageResource(R.drawable.ic_favorite_black_24dp);
//                }else{
//                    mFavoriteView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
//                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildChanged: " + dataSnapshot.getValue(Articles.class).getTitle());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved: ");
                //dataSnapshot.child(pushId);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildMoved: ");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: ");

            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);

        //mFavoriteView.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        //Query query


        mFavoriteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: first click " + pushId);

                //mDatabaseReference.child(article.getTitle()).setValue(article);lse

                //String push = mDatabaseReference.child(article.getTitle()).getKey();

                if (pushId == null) {

                    Log.d(TAG, "onClick: " + article.getTitle());
                    Log.d(TAG, "pushId: " + pushId);
                    mFavoriteView.setImageResource(R.drawable.ic_favorite_black_24dp);
                    pushId = mDatabaseReference.child(article.getTitle()).getKey();
                    mDatabaseReference.child(pushId).setValue(article);
                    //pushId = mDatabaseReference.push().getKey();
                    //Log.d(TAG, "onClick: true " + pushId);
                    //addedToFav = false;


                } else {
                    Log.d(TAG, "onClick: else" + pushId);
                    mFavoriteView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    mDatabaseReference.child(article.getTitle()).removeValue();
                    pushId = null;
                }


//
//                if((mFavoriteView.equals(R.drawable.ic_favorite_black_24dp))){
//                    Log.d(TAG, "onClick: " + pushId);
//                    //pushId = mDatabaseReference.push().getKey();
//                    mDatabaseReference.child(article.getTitle()).removeValue();
//                   // addedToFav = true;
//                    mFavoriteView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
//
//                }


            }

        });

//        mUnFavView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDatabaseReference.child(article.getTitle()).removeValue();
//               // mNewsAdapter.notifyDataSetChanged();
//
//            }
//        });

    }

}


// mChildEventListener = new ChildEventListener() {
//@Override
//public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
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
//        dataSnapshot.getRef().removeValue();
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