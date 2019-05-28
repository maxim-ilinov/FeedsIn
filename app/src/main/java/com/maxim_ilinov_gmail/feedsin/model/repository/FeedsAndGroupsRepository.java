package com.maxim_ilinov_gmail.feedsin.model.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;

import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.model.FeedForList;
import com.maxim_ilinov_gmail.feedsin.model.GroupForDrawerMenu;
import com.maxim_ilinov_gmail.feedsin.model.GroupForList;
import com.maxim_ilinov_gmail.feedsin.model.GroupWithFeeds;
import com.maxim_ilinov_gmail.feedsin.model.Group;
import com.maxim_ilinov_gmail.feedsin.model.RvItem;
import com.maxim_ilinov_gmail.feedsin.model.data.db.RssDao;
import com.maxim_ilinov_gmail.feedsin.model.data.db.RssRoomDatabase;
import com.maxim_ilinov_gmail.feedsin.model.data.webservices.RssWebservice;
import com.maxim_ilinov_gmail.feedsin.model.data.webservices.RssWebserviceClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FeedsAndGroupsRepository {
    private static final String TAG = "FeedsAndGroupsRepository";


    private final RssWebservice rssWebservice;
    private final RssDao rssDao;


    private final Executor executor;

    private final RssRoomDatabase db;

    private int defaultRssFeedGroupId;


    public FeedsAndGroupsRepository(Context context) {

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

              //  ((MutableLiveData<Long>) insertedId).setValue((int)rssDao.insertFeedGroup(new Group(groupName)));
                rssDao.insertRssFeed(
                        new FeedEntity(feedName, feedLink));

            }
        });

    }

    public void addGroup(String groupName) {


        executor.execute(() -> {

            rssDao.insertFeedGroup(new Group(groupName));
        });

    }

   /* private LiveData<List<FeedEntity>> getSelectRssFeedsForGroupSync(int groupId)
    {

       return  rssDao.getS(groupId);

    }*/

    public void addFeedToGroup(FeedEntity rssFeedEntity, int groupId) {

        executor.execute(() -> {

            rssFeedEntity.setFeedGroupId(groupId);

            rssDao.updateFeed(rssFeedEntity);
        });

    }

    public LiveData<List<FeedEntity>> getRssFeeds() {

        return rssDao.selectAllRssFeeds();
    }

    public LiveData<List<FeedEntity>> getSelectedRssFeeds() {

        return rssDao.selectSelectedRssFeeds();
    }

    public LiveData<List<GroupWithFeeds>> getGroupsWithAllFeeds() {


        return rssDao.selectGroupsWithAllFeeds();
    }


    public LiveData<List<Group>> getFeedGroups() {


        return rssDao.selectFeedGroups();
    }

    public LiveData<List<GroupForDrawerMenu>> selectGroupsForDrawerMenu() {


        //return rssDao.selectGroupsForDrawerMenu();



        return rssDao.selectDistinctGroupsForDrawerMenu();
    }






    public LiveData<PagedList<RvItem>> getRvItemsForFeedAndGroupList()
    {
        return   new LivePagedListBuilder<>(


         new DataSource.Factory<Integer, RvItem>() {
            @Override
            public PositionalDataSource<RvItem> create() {

                return new PositionalDataSource<RvItem>() {

                    private int computeCount() {
                        // actual count code here

                        int countI = rssDao.countGroups() + rssDao.countFeeds();


                        return countI;
                    }

                    private List<RvItem> loadRangeInternal(int startPosition, int loadCount) {
                        // actual load code here

                        List <RvItem> rvItems = new ArrayList<RvItem>();

                        for (GroupForList g : rssDao.selectGroupsForList(startPosition, loadCount))
                        {
                            rvItems.add(g);

                           //todo check for null

                            for (FeedForList f : rssDao.selectFeedsForListByGroupId(g.getId()))
                            {
                                rvItems.add(f);
                            }


                        }

                        return rvItems;
                    }

                    @Override
                    public void loadInitial(@NonNull LoadInitialParams params,
                                            @NonNull LoadInitialCallback<RvItem> callback) {

                        int totalCount = computeCount();
                        int position = computeInitialLoadPosition(params, totalCount);
                        int loadSize = computeInitialLoadSize(params, position, totalCount);
                        callback.onResult(loadRangeInternal(position, loadSize), position, totalCount);

                    }

                    @Override
                    public void loadRange(@NonNull LoadRangeParams params,
                                          @NonNull LoadRangeCallback<RvItem> callback) {
                        callback.onResult(loadRangeInternal(params.startPosition, params.loadSize));
                    }


                };
            }
            }
                            , 10).build();

    }

    public LiveData<List<GroupWithFeeds>> getFeedsWithGroupsForMenu() {


        return rssDao.selectGroupsWithAllFeeds();
    }

    public void setFeedsSelected(List<FeedEntity> selectedFeedEntities) {


        executor.execute(() -> {
            rssDao.updateFeeds(selectedFeedEntities);

        });

    }

    public void setFeedsToBeShownForGroup(Group fg) {

        executor.execute(() -> {
            rssDao.setFeedsToBeShownForGroup(fg.getId());

        });
    }

    public void unsetFeedsToBeShownForGroup(Group fg) {

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
