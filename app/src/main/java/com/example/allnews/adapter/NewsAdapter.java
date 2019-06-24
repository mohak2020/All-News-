package com.example.allnews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.allnews.R;
import com.example.allnews.model.articles.Articles;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private static final String TAG = "NewsAdapter";

    Context mContex;
    ArrayList<Articles> mArticles;
    NewsAdapterOnclickHandler mHandler;

    public interface NewsAdapterOnclickHandler {
        void onItemClick(Articles article);

    }

    public NewsAdapter(Context context, ArrayList<Articles> articles, NewsAdapterOnclickHandler handler) {
        this.mContex = context;
        this.mArticles = articles;
        this.mHandler = handler;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.topic_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view,mHandler);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.titleView.setText(mArticles.get(i).getTitle());

        boolean flag = mArticles.get(i).getUrlToImage().contains("https");
        Log.d(TAG, "onBindViewHolder: "+ flag);

        String imageUrl = mArticles.get(i).getUrlToImage();

        if (flag) {
            Picasso.with(mContex).load(imageUrl).into(viewHolder.imageView);

        }else {
            imageUrl = mArticles.get(i).getUrlToImage().replace("http", "https");
            Picasso.with(mContex).load(imageUrl).into(viewHolder.imageView);
        }



    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView, NewsAdapterOnclickHandler handler) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title_view);
            imageView = itemView.findViewById(R.id.topic_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            Articles article = mArticles.get(index);
            mHandler.onItemClick(article);
        }


    }

    public void setNewsFavorite(Articles article){

        mArticles.add(article);
        notifyDataSetChanged();
    }
}
