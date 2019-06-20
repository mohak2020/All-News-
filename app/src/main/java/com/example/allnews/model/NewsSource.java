package com.example.allnews.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.allnews.model.articles.Articles;

import java.util.ArrayList;

public class NewsSource implements Parcelable {

    private ArrayList<Articles> articles;


    protected NewsSource(Parcel in) {
        articles = in.createTypedArrayList(Articles.CREATOR);
    }

    public static final Creator<NewsSource> CREATOR = new Creator<NewsSource>() {
        @Override
        public NewsSource createFromParcel(Parcel in) {
            return new NewsSource(in);
        }

        @Override
        public NewsSource[] newArray(int size) {
            return new NewsSource[size];
        }
    };

    public ArrayList<Articles> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Articles> articles) {
        this.articles = articles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(articles);
    }
}
