package com.example.allnews.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.allnews.R;
import com.example.allnews.model.NewsSource;
import com.example.allnews.model.articles.Articles;
import com.example.allnews.network.NewsAPI;
import com.example.allnews.network.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsWidgetService extends RemoteViewsService {

    private static final String TAG = "NewsWidgetService";

    
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new NewsWidgetItemFactory(getApplicationContext(),intent);
    }

    class NewsWidgetItemFactory implements RemoteViewsFactory{
        private Context context;
        private int appWidgetId;
        private String[] exampleData = {"one", "two", "three", "four",
                "five", "six", "seven", "eight", "nine", "ten"};


        ArrayList<Articles>mArticles;





        NewsWidgetItemFactory(Context context, Intent intent) {
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        @Override
        public void onCreate() {

            Log.d(TAG, "onCreate: ");

            NewsAPI newsAPI = RetrofitInstance
                    .getRetrofitInstance().create(NewsAPI.class);

            Call<NewsSource> call = newsAPI.getNytimesNews();

            call.enqueue(new Callback<NewsSource>() {
                @Override
                public void onResponse(Call<NewsSource> call, Response<NewsSource> response) {

                    NewsSource newsSource = response.body();
                    //Log.d(TAG, "onResponse: " + newsSource.getArticles().get(0).getTitle());
                    mArticles = newsSource.getArticles();
                    //mNewsSource = newsSource;
                    Log.d(TAG, "onResponse: "+ mArticles.get(0).getTitle());
                    //initRecycleView();
                    //onSucess(newsSource);

//                if (response.isSuccessful()){
//                    onSucess(newsSource);
//                }

                }

                @Override
                public void onFailure(Call<NewsSource> call, Throwable t) {

                }

            });



        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
           // return exampleData.length;
            if(mArticles==null){
                return 0;
            }
           return mArticles.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

//            NewsAPI newsAPI = RetrofitInstance
//                    .getRetrofitInstance().create(NewsAPI.class);
//
//            call = newsAPI.getNytimesNews();

//            call.enqueue(new Callback<NewsSource>() {
//                @Override
//                public void onResponse(Call<NewsSource> call, Response<NewsSource> response) {
//
//                    NewsSource newsSource = response.body();
//                    //Log.d(TAG, "onResponse: " + newsSource.getArticles().get(0).getTitle());
//                    mArticles = newsSource.getArticles();
//                    //mNewsSource = newsSource;
//                    Log.d(TAG, "onResponse: "+ mArticles.get(0).getTitle());
//                    //initRecycleView();
//                    //onSucess(newsSource);
//
////                if (response.isSuccessful()){
////                    onSucess(newsSource);
////                }
//
//                }
//
//                @Override
//                public void onFailure(Call<NewsSource> call, Throwable t) {
//
//                }
//
//
//
//            });

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.news_widget_item);
            views.setTextViewText(R.id.news_title_item, mArticles.get(position).getTitle());
            //SystemClock.sleep(500);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }


}
