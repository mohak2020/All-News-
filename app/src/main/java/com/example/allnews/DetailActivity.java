package com.example.allnews;

import android.content.Intent;
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

import com.example.allnews.model.articles.Articles;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    CollapsingToolbarLayout collapsingToolbar;
    Toolbar toolbar;
    FloatingActionButton actionButton;
    private View mPhotoContainerView;
    private ImageView mPhotoView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        final Articles article = intent.getParcelableExtra("article");

        Log.d(TAG, "onCreate: "+article.getContent());


        TextView titleView = (TextView)findViewById(R.id.article_title);
        TextView bodyView = (TextView) findViewById(R.id.article_body);
        toolbar = (Toolbar) findViewById(R.id.frag_detail_toolbar);
        mPhotoView = (ImageView) findViewById(R.id.photo);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);

        EditText editText = (EditText)findViewById(R.id.add_comment);

        titleView.setText(article.getTitle());
        bodyView.setText(article.getContent());


        boolean flag = article.getUrlToImage().contains("https");
        Log.d(TAG, "onBindViewHolder: "+ flag);

        String imageUrl = article.getUrlToImage();

        if (flag) {
            Picasso.with(this).load(imageUrl).into(mPhotoView);

        }else {
            imageUrl = article.getUrlToImage().replace("http", "https");
            Picasso.with(this).load(imageUrl).into(mPhotoView);
        }

        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.appBar);
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

                    isShow = true;
                } else if(isShow) {
                    collapsingToolbar.setTitle("");//carefull there should a space between double quote otherwise it wont work
                    //actionButton.setVisibility(View.VISIBLE);

                    isShow = false;
                }
            }
        });


    }


}
