package com.example.allnews;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements NewsAdapter.NewsAdapterOnclickHandler {

    private static final String TAG = "NewsFragment";
    public static final int RC_SIGN_IN = 1;
    NewsSource mNewsSource;
    ArrayList<Articles> mArticles;
    RecyclerView mRecyclerView;
    NewsAdapter mNewsAdapter;

    int mIndex;
    Call<NewsSource> call;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
//        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//
//
//
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//                FirebaseUser user = firebaseAuth.getCurrentUser();
////               Log.d(TAG, "onAuthStateChanged: "+user.getEmail());
//
//                if (user != null) {
//                    //Log.d(TAG, "onAuthStateChanged: "+user.getEmail());
//                    onSignedInInitialize();
//                    Toast.makeText(getActivity(), "You're now signed in. Welcome to News App.", Toast.LENGTH_SHORT).show();
//
//                } else {
//
//
//                    startActivityForResult(
//                            AuthUI.getInstance()
//                                    .createSignInIntentBuilder()
//                                    .setIsSmartLockEnabled(false)
//                                    .setAvailableProviders(Arrays.asList(
//
//                                            new AuthUI.IdpConfig.EmailBuilder().build()
//                                    ))
//                                    .build(),
//                            RC_SIGN_IN);
//
//                }
//
//                // RC_SIGN_IN
//
//            }
//        };


    }
    
    


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        Log.d(TAG, "onCreateView: called");

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

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

//        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//                FirebaseUser user = firebaseAuth.getCurrentUser();
////               Log.d(TAG, "onAuthStateChanged: "+user.getEmail());
//
//               if (user != null) {
//                   //Log.d(TAG, "onAuthStateChanged: "+user.getEmail());
//                   onSignedInInitialize();
//                   Toast.makeText(getActivity(), "You're now signed in. Welcome to News App.", Toast.LENGTH_SHORT).show();
//
//                } else {
//
//
//                    startActivityForResult(
//                            AuthUI.getInstance()
//                                    .createSignInIntentBuilder()
//                                    .setIsSmartLockEnabled(false)
//                                    .setAvailableProviders(Arrays.asList(
//
//                                            new AuthUI.IdpConfig.EmailBuilder().build()
//                                    ))
//                                    .build(),
//                            RC_SIGN_IN);
//
//                }
//
//                // RC_SIGN_IN
//
//            }
//        };


        return view;
    }

    private void onSignedInInitialize() {
        
        
        
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded, set up the UI
                Toast.makeText(getContext(), "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, finish the activity
                Toast.makeText(getContext(), "Sign in canceled", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
    }



    private void initRecycleView() {
        mNewsAdapter = new NewsAdapter(getContext(), mArticles, this);
        mRecyclerView.setAdapter(mNewsAdapter);

    }

    boolean flag = true;

    private void onSucess(NewsSource newsSource) {
        if (flag) {
            // WidgetUtils.updateWidgetsData(getActivity(), newsSource);
            Log.d(TAG, "onSucess: " + newsSource.getArticles().get(1).getTitle());
            flag = false;
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

        if (item.getItemId() == R.id.favorites) {
            Toast.makeText(getActivity(), "favorite clicked",
                    Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getActivity(), FavoriteActivity.class);
            startActivity(intent);


        } else {
            Toast.makeText(getActivity(), "sign out clicked",
                    Toast.LENGTH_LONG).show();
            AuthUI.getInstance().signOut(getContext());
            getActivity().finish();
        }
        return true;

    }



//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.d(TAG, "onResume: ");
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
//    }
//
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.d(TAG, "onPause: ");
//        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
//    }


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
