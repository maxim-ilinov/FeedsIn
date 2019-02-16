package com.maxim_ilinov_gmail.feedsin.model.repository;


import android.content.Context;
import android.util.Log;

import com.maxim_ilinov_gmail.feedsin.model.data.db.RssItemDao;
import com.maxim_ilinov_gmail.feedsin.model.data.db.RssRoomDatabase;
import com.maxim_ilinov_gmail.feedsin.model.RssFeed;
import com.maxim_ilinov_gmail.feedsin.model.RssItem;
import com.maxim_ilinov_gmail.feedsin.model.data.webservices.RssWebservice;
import com.maxim_ilinov_gmail.feedsin.model.data.webservices.RssWebserviceClient;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.maxim_ilinov_gmail.feedsin.model.DateUtils.parseStringToDate;


public class RssItemsRepository {


    private static final String TAG = "RssItemsRepository";



    private final RssWebservice rssWebservice;
    private final RssItemDao rssItemDao;


    private final Executor executor;

    private final RssRoomDatabase db;

    private int defaultRssFeedGroupId;


    public RssItemsRepository(Context context) {

       db = RssRoomDatabase.getInstance(context);


        this.rssItemDao = db.getRssDao();

       /* this.languageDao = db.getLanguageDao();
        this.countryLanguageJoinDao = db.getCountryLanguageJoinDao();
        this.currencyDao = db.getCurrencyDao();
        this.countryCurrencyJoinDao = db.getCountryCurrencyJoinDao();
        this.timezoneDao = db.getTimezoneDao();
        this.countryTimezoneJoinDao = db.getCountryTimezoneJoinDao();
*/



       this.executor =  Executors.newSingleThreadExecutor();

       rssWebservice =   RssWebserviceClient.getInstance().getRssWebservice();

            loadRssData();

    }

    public void loadRssData() {

        executor.execute(() -> {

            //for test purposes
           // rssItemDao.deleteAllRssItems();
           // rssItemDao.deleteAllFeeds();

           List<RssFeed> rssFeeds = rssItemDao.selectRssFeedsSync();

           for (RssFeed rf : rssFeeds)
           {
               loadRssItemsFromWeb(rf.getId(), rf.getRssFeedLink());
           }

        });

    }

    public LiveData<PagedList<RssItem>> getItemsForSelectedFeedsPl()
    {
        return   new LivePagedListBuilder<>(
                rssItemDao.selectItemsForSelectedFeedsPl(), /* page size */ 20).build();

    }

    public LiveData<List<RssItem>> getItemsForSelectedFeeds() {
        return   rssItemDao.selectItemsForSelectedFeeds();
    }


    private void loadRssItemsFromWeb(int feedId, String rssLink) {


        executor.execute(() -> {

            Call<RssFeed> rssFeedCall = rssWebservice.getItems(rssLink);

            rssFeedCall.enqueue(new Callback<RssFeed>() {
                @Override
                public void onResponse(Call<RssFeed> call, final Response<RssFeed> response) {

                    executor.execute(()-> {
                            if (response.isSuccessful()) {
                            //TODO check type of response

                               // Log.d(TAG,"Response raw: " + response.raw());

                               // Log.d(TAG,"Response message: " + response.toString());

                                RssFeed rssFeed = response.body();

                              //  Log.d(TAG, "Current feed: " + rssFeed.toString());

                                if (rssFeed.getRssItemList()!=null ) {


                                 //   Log.d(TAG, "Items in list: " + rssFeed.getRssItemList().size());

                                    for (RssItem ri : rssFeed.getRssItemList()) {

                                        String strDate = ri.getPubDate();

                                  //      Log.d(TAG, "Item pubdate: " + strDate);

                                        Date pubDateNorm = parseStringToDate(strDate);

                                        ri.setPubDateNorm(pubDateNorm);

                                        if (pubDateNorm!=null)
                                        {

                                        }
                                        else
                                        {

                                        }
                                    /*    Log.d(TAG, "Item pubDateNorm: " + pubDateNorm);
                                        Log.d(TAG, "Item title: " + ri.getTitle());
                                        Log.d(TAG, "Item desc: " + ri.getDescription());
*/
                                        ri.setRssFeedId(feedId);

                                        if (rssItemDao.countRssItemWithHash(ri.hashCode()) == 0) {

                                            ri.setHash(ri.hashCode());
                                            rssItemDao.insertRssItem(ri);
                                        }


                                    }
                                }


                            } else {
                              //  Log.d(TAG,"response code " + response.code());
                                //todo use this to inform user
                            }
                    });
                }
                @Override
                public void onFailure(Call<RssFeed> call, Throwable t) {
                    Log.d(TAG,"failure " + t);
                }

            });


        });


    }



}
