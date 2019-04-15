package com.maxim_ilinov_gmail.feedsin.model.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.maxim_ilinov_gmail.feedsin.model.FeedGroupForDrawerMenu;
import com.maxim_ilinov_gmail.feedsin.model.FeedGroupWithFeeds;
import com.maxim_ilinov_gmail.feedsin.model.RssFeed;
import com.maxim_ilinov_gmail.feedsin.model.FeedGroup;
import com.maxim_ilinov_gmail.feedsin.model.data.db.RssDao;
import com.maxim_ilinov_gmail.feedsin.model.data.db.RssRoomDatabase;
import com.maxim_ilinov_gmail.feedsin.model.data.webservices.RssWebservice;
import com.maxim_ilinov_gmail.feedsin.model.data.webservices.RssWebserviceClient;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FeedsGroupsRepository {
    private static final String TAG = "FeedsGroupsRepository";


    private final RssWebservice rssWebservice;
    private final RssDao rssDao;


    private final Executor executor;

    private final RssRoomDatabase db;

    private int defaultRssFeedGroupId;


    public FeedsGroupsRepository(Context context) {

        db = RssRoomDatabase.getInstance(context);


        this.rssDao = db.getRssDao();

       /* this.languageDao = db.getLanguageDao();
        this.countryLanguageJoinDao = db.getCountryLanguageJoinDao();
        this.currencyDao = db.getCurrencyDao();
        this.countryCurrencyJoinDao = db.getCountryCurrencyJoinDao();
        this.timezoneDao = db.getTimezoneDao();
        this.countryTimezoneJoinDao = db.getCountryTimezoneJoinDao();
*/
        this.executor = Executors.newSingleThreadExecutor();

        rssWebservice = RssWebserviceClient.getInstance().getRssWebservice();

     //   addTestMenuItems();


    }


    public void addFeed(String feedName, String feedLink) {

       // LiveData<Long> insertedId = new MutableLiveData();

        executor.execute(() -> {

            if (rssDao.feedWithUrlCount(feedLink) == 0) {

              //  ((MutableLiveData<Long>) insertedId).setValue((int)rssDao.insertFeedGroup(new FeedGroup(groupName)));
                rssDao.insertRssFeed(
                        new RssFeed(feedName, feedLink));

            }
        });

    }

    public void addGroup(String groupName) {


        executor.execute(() -> {

            rssDao.insertFeedGroup(new FeedGroup(groupName));
        });

    }

   /* private LiveData<List<RssFeed>> getSelectRssFeedsForGroupSync(int groupId)
    {

       return  rssDao.getS(groupId);

    }*/

    public void addFeedToGroup(RssFeed rssFeed, int groupId) {

        executor.execute(() -> {

            rssFeed.setFeedGroupId(groupId);

            rssDao.updateFeed(rssFeed);
        });

    }

    public LiveData<List<RssFeed>> getRssFeeds() {

        return rssDao.selectAllRssFeeds();
    }

    public LiveData<List<RssFeed>> getSelectedRssFeeds() {

        return rssDao.selectSelectedRssFeeds();
    }

    public LiveData<List<FeedGroupWithFeeds>> getGroupsWithAllFeeds() {


        return rssDao.selectGroupsWithAllFeeds();
    }


    public LiveData<List<FeedGroup>> getFeedGroups() {


        return rssDao.selectFeedGroups();
    }

    public LiveData<List<FeedGroupForDrawerMenu>> selectGroupsForDrawerMenu() {


        //return rssDao.selectGroupsForDrawerMenu();

        return rssDao.selectDistinctGroupsForDrawerMenu();
    }



    public LiveData<List<FeedGroupWithFeeds>> getFeedsWithGroupsForMenu() {


        return rssDao.selectGroupsWithAllFeeds();
    }

    public void setFeedsSelected(List<RssFeed> selectedFeeds) {


        executor.execute(() -> {
            rssDao.updateFeeds(selectedFeeds);

        });

    }

    public void setFeedsToBeShownForGroup(FeedGroup fg) {

        executor.execute(() -> {
            rssDao.setFeedsToBeShownForGroup(fg.getId());

        });
    }

    public void unsetFeedsToBeShownForGroup(FeedGroup fg) {

        executor.execute(() -> {
            rssDao.unsetFeedsToBeShownForGroup(fg.getId());

        });
    }

    public void setFeedGroupChecked(long feedGroupId) {

        executor.execute(() -> {
            rssDao.setFeedGroupChecked(feedGroupId);

        });

    }


    public void unsetFeedGroupChecked(long feedGroupId) {

        executor.execute(() -> {
            rssDao.unsetFeedGroupChecked(feedGroupId);

        });

    }
}
