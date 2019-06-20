package com.maxim_ilinov_gmail.feedsin.model.repository;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.maxim_ilinov_gmail.feedsin.model.Article;
import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.model.data.db.ArticleDao;
import com.maxim_ilinov_gmail.feedsin.model.data.db.FeedDao;
import com.maxim_ilinov_gmail.feedsin.model.data.db.RssRoomDatabase;
import com.maxim_ilinov_gmail.feedsin.model.data.webservices.RssWebservice;
import com.maxim_ilinov_gmail.feedsin.model.data.webservices.RssWebserviceClient;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.maxim_ilinov_gmail.feedsin.model.DateUtils.parseStringToDate;


public class ArticleRepository {


    private static final String TAG = "ArticleRepository";

    private final RssWebservice rssWebservice;

    private final ArticleDao articleDao;

    private final FeedDao feedDao;


    private final Executor executor;

    private final RssRoomDatabase db;

    private int defaultRssFeedGroupId;

    private static volatile ArticleRepository instance;


    private ArticleRepository(Context context) {

        db = RssRoomDatabase.getInstance(context);


        this.articleDao = db.getArticleDao();

        this.feedDao = db.getFeedDao();

        this.executor = Executors.newSingleThreadExecutor();

        rssWebservice = RssWebserviceClient.getInstance().getRssWebservice();

        loadRssData();

    }


    public static ArticleRepository getInstance(Context context){
        if (instance == null)
        {
            synchronized (ArticleRepository.class){
                if(instance == null){
                    instance = new ArticleRepository(context);
                }
            }
        }

        return instance;
    }

    public void loadRssData() {

        executor.execute(() -> {

            //for test purposes
            // articleDao.deleteAllArticles();
            // articleDao.deleteAllFeeds();

            List<FeedEntity> feedEntities = feedDao.selectRssFeedsSync();

            for (FeedEntity rf : feedEntities) {
                loadArticlesFromWeb(rf.getId(), rf.getRssFeedLink());
            }

        });

    }

    private void loadArticlesFromWeb(int feedId, String rssLink) {


        executor.execute(() -> {

            Call<FeedEntity> rssFeedCall = rssWebservice.getItems(rssLink);

            rssFeedCall.enqueue(new Callback<FeedEntity>() {
                @Override
                public void onResponse(Call<FeedEntity> call, final Response<FeedEntity> response) {

                    executor.execute(() -> {
                        if (response.isSuccessful()) {
                            //TODO check type of response

                            // Log.d(TAG,"Response raw: " + response.raw());

                            // Log.d(TAG,"Response message: " + response.toString());

                            FeedEntity rssFeedEntity = response.body();

                            //  Log.d(TAG, "Current feed: " + rssFeedEntity.toString());

                            if (rssFeedEntity.getArticleList() != null) {


                                //   Log.d(TAG, "Items in list: " + rssFeedEntity.getArticleList().size());

                                for (Article ri : rssFeedEntity.getArticleList()) {

                                    String strDate = ri.getPubDate();

                                    //      Log.d(TAG, "Item pubdate: " + strDate);

                                    Date pubDateNorm = parseStringToDate(strDate);

                                    ri.setPubDateNorm(pubDateNorm);

                                    if (pubDateNorm != null) {

                                    } else {

                                    }
                                    /*    Log.d(TAG, "Item pubDateNorm: " + pubDateNorm);
                                        Log.d(TAG, "Item title: " + ri.getTitle());
                                        Log.d(TAG, "Item desc: " + ri.getDescription());
*/
                                    ri.setRssFeedId(feedId);

                                    if (articleDao.countArticlesWithHash(ri.hashCode()) == 0) {

                                        ri.setHash(ri.hashCode());
                                        articleDao.insertRssItem(ri);
                                    }


                                }
                            }


                        } else {
                            //  Log.d(TAG,"response code " + response.code());
                            //todo place code to inform user
                        }
                    });
                }

                @Override
                public void onFailure(Call<FeedEntity> call, Throwable t) {
                    Log.d(TAG, "failure " + t);
                }

            });


        });


    }

    public LiveData<PagedList<Article>> getItemsForSelectedFeedsPl() {
        return new LivePagedListBuilder<>(
                articleDao.selectArticlesForSelectedFeedsPl(), /* page size */ 10).build();

    }

    public LiveData<List<Article>> getItemsForSelectedFeeds() {
        return articleDao.selectItemsForSelectedFeeds();
    }

}
