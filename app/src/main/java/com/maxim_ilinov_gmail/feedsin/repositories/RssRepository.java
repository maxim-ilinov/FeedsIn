package com.maxim_ilinov_gmail.feedsin.repositories;


import android.content.Context;
import android.util.Log;


import com.maxim_ilinov_gmail.feedsin.db.RssRoomDatabase;
import com.maxim_ilinov_gmail.feedsin.db.dao.RssDao;
import com.maxim_ilinov_gmail.feedsin.db.entities.RssFeed;
import com.maxim_ilinov_gmail.feedsin.db.entities.RssFeedGroup;
import com.maxim_ilinov_gmail.feedsin.db.entities.RssItem;
import com.maxim_ilinov_gmail.feedsin.webservices.RssWebservice;
import com.maxim_ilinov_gmail.feedsin.webservices.RssWebserviceClient;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class RssRepository {

    private static final String TAG = "RssRepository";



    private final RssWebservice rssWebservice;
    private final RssDao rssDao;


    private final Executor executor;

    private final RssRoomDatabase db;

    private int defaultRssFeedGroupId;


    public RssRepository(Context context) {

       db = RssRoomDatabase.getInstance(context);


        this.rssDao = db.getRssDao();

       /* this.languageDao = db.getLanguageDao();
        this.countryLanguageJoinDao = db.getCountryLanguageJoinDao();
        this.currencyDao = db.getCurrencyDao();
        this.countryCurrencyJoinDao = db.getCountryCurrencyJoinDao();
        this.timezoneDao = db.getTimezoneDao();
        this.countryTimezoneJoinDao = db.getCountryTimezoneJoinDao();
*/



       this.executor =  Executors.newSingleThreadExecutor();

       rssWebservice =   RssWebserviceClient.getInstance().getRssWebservice();

//TODO: remove debug and make tests
        addNewFeed ("http://www.ixbt.com/export/articles.rss", true);
        addNewFeed ("https://habr.com/rss/hub/apps_design/all/?hl=ru&fl=ru", true);



            loadRssData();



    }

    private void loadRssData() {

        executor.execute(() -> {

           List<RssFeed> rssFeeds = rssDao.selectSelectedRssFeedsSync();

           for (RssFeed rf : rssFeeds)
           {
               loadRssItemsFromWeb(rf.getId(), rf.getRssFeedLink());
           }

        });

    }

    public void addNewFeed(String rssUrl,  boolean selected)
    {
        executor.execute(() -> {

            if (rssDao.feedWithUrlCount(rssUrl) == 0) {

                rssDao.insertRssFeed(new RssFeed(rssUrl, selected));

            }
     });

    }


    public void addFeedToGroup(int feedId, int groupId)
    {
        int useGroupId;

        if (rssDao.countRssFeedGroupsWithId(groupId)>0 )
        {
            useGroupId = groupId;
        }
        else
        {
            useGroupId = 0;
        }
    }
    public LiveData<List<RssFeed>> getRssFeeds(){

        return rssDao.selectAllRssFeeds();
    }

    public LiveData<List<RssFeed>> getSelectedRssFeeds(){

        return rssDao.selectSelectedRssFeeds();
    }

    public LiveData<List<RssItem>> getItemsForSelectedFeeds() {



        return   rssDao.selectItemsForSelectedFeeds();


       /* if (rssFeeds != null)
        {*/
            //check availability of connection

            //loadRssItemsFromWeb(rssFeeds.get(0));


           /* int[] rssFeedIds = new int[rssFeeds.size()];

            for (int i = 0; i<rssFeeds.size(); i++){

                rssFeedIds[i]= rssFeeds.get(i).getId();

            }

            return rssDao.selectItemsByFeedIds(rssFeedIds);*/
       /* }
        return null;
*/

    }

    public LiveData<List<RssFeedGroup>> getRssFeedGroups(){
      return   rssDao.selectAllRssFeedGroups();
    }

    private void loadRssItemsFromWeb(int feedId, String rssLink) {



        executor.execute(() -> {

            Call<RssFeed> rssFeedCall = rssWebservice.getItems(rssLink);

            rssFeedCall.enqueue(new Callback<RssFeed>() {
                @Override
                public void onResponse(Call<RssFeed> call, final Response<RssFeed> response) {

                    executor.execute(()-> {
                            if (response.isSuccessful()) {


                                RssFeed rssFeed = response.body();

                                Log.d(TAG,"Items in list: " + rssFeed.getRssItemList().size());



                                for (RssItem ri : rssFeed.getRssItemList())
                                {
                                    Log.d(TAG,"Item title: " + ri.getTitle());
                                    Log.d(TAG,"Item desc: " + ri.getDescription());

                                    ri.setRssFeedId(feedId);


                                    if (rssDao.countRssItemWithGuid(ri.getGuid())==0)
                                    {
                                        rssDao.insertRssItem(ri);
                                    }




                                }



                            } else {
                                Log.d(TAG,"response code " + response.code());
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
