package com.maxim_ilinov_gmail.feedsin.model.repository;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;

import com.maxim_ilinov_gmail.feedsin.model.Article;
import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.model.FeedForList;
import com.maxim_ilinov_gmail.feedsin.model.Group;
import com.maxim_ilinov_gmail.feedsin.model.GroupForDrawerMenu;
import com.maxim_ilinov_gmail.feedsin.model.GroupForList;
import com.maxim_ilinov_gmail.feedsin.model.GroupWithFeeds;
import com.maxim_ilinov_gmail.feedsin.model.RvItem;
import com.maxim_ilinov_gmail.feedsin.model.data.db.FeedDao;
import com.maxim_ilinov_gmail.feedsin.model.data.db.GroupDao;
import com.maxim_ilinov_gmail.feedsin.model.data.db.RssDao;
import com.maxim_ilinov_gmail.feedsin.model.data.db.RssRoomDatabase;
import com.maxim_ilinov_gmail.feedsin.model.data.webservices.RssWebservice;
import com.maxim_ilinov_gmail.feedsin.model.data.webservices.RssWebserviceClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.maxim_ilinov_gmail.feedsin.model.DateUtils.parseStringToDate;


public class FeedAndGroupRepository {


    private static final String TAG = "ArticleRepository";

    private final RssRoomDatabase db;

    private final FeedDao feedDao;

    private final GroupDao groupDao;

    private final Executor executor;



    private int defaultRssFeedGroupId;

    private static volatile FeedAndGroupRepository instance;

    private FeedAndGroupRepository(Context context) {

        db = RssRoomDatabase.getInstance(context);


        this.feedDao = db.getFeedDao();

        this.groupDao = db.getGroupDao();

        this.executor = Executors.newSingleThreadExecutor();




    }

    public static FeedAndGroupRepository getInstance(Context context){
        if (instance == null)
        {
            synchronized (FeedAndGroupRepository.class){
                if(instance == null){
                    instance = new FeedAndGroupRepository(context);
                }
            }
        }

        return instance;
    }


    public LiveData<List<GroupWithFeeds>> getCheckedFeedGroups() {

        return groupDao.getCheckedFeedGroups();

    }

    public LiveData<List<GroupWithFeeds>> getGroupsWithAllFeeds() {


        return groupDao.selectGroupsWithAllFeeds();
    }

    public void addFeed(String feedName, String feedLink) {

        // LiveData<Long> insertedId = new MutableLiveData();

        executor.execute(() -> {

            if (feedDao.feedWithUrlCount(feedLink) == 0) {

                //  ((MutableLiveData<Long>) insertedId).setValue((int)feedDao.insertFeedGroup(new Group(groupName)));
                feedDao.insertRssFeed(
                        new FeedEntity(feedName, feedLink));

            }
        });

    }

    public void addGroup(String groupName) {


        executor.execute(() -> {

            groupDao.insertFeedGroup(new Group(groupName));
        });

    }



    public void addFeedToGroup(FeedEntity rssFeedEntity, int groupId) {

        executor.execute(() -> {

            rssFeedEntity.setFeedGroupId(groupId);

            feedDao.updateFeed(rssFeedEntity);
        });

    }

    public LiveData<List<FeedEntity>> getRssFeeds() {

        return feedDao.selectAllRssFeeds();
    }

    public LiveData<List<FeedEntity>> getSelectedRssFeeds() {

        return feedDao.selectSelectedRssFeeds();
    }



    public LiveData<List<Group>> getFeedGroups() {


        return groupDao.selectFeedGroups();
    }

    public LiveData<List<GroupForDrawerMenu>> selectGroupsForDrawerMenu() {


        //return feedDao.selectGroupsForDrawerMenu();


        return groupDao.selectDistinctGroupsForDrawerMenu();
    }


    public LiveData<PagedList<RvItem>> getRvItemsForFeedAndGroupList() {
        return new LivePagedListBuilder<>(


                new DataSource.Factory<Integer, RvItem>() {
                    @Override
                    public PositionalDataSource<RvItem> create() {

                        return new PositionalDataSource<RvItem>() {

                            @Override
                            public void loadInitial(@NonNull LoadInitialParams params,
                                                    @NonNull LoadInitialCallback<RvItem> callback) {

                                int totalCount = computeCount();
                                int position = computeInitialLoadPosition(params, totalCount);
                                int loadSize = computeInitialLoadSize(params, position, totalCount);
                                callback.onResult(loadRangeInternal(position, loadSize), position, totalCount);

                            }

                            private int computeCount() {
                                // actual count code here

                                int countI = groupDao.countGroups() + feedDao.countFeeds();


                                return countI;
                            }

                            private List<RvItem> loadRangeInternal(int startPosition, int loadCount) {
                                // actual load code here

                                List<RvItem> rvItems = new ArrayList<RvItem>();

                                for (GroupForList g : groupDao.selectGroupsForList(startPosition, loadCount)) {
                                    rvItems.add(g);

                                    //todo check for null

                                    for (FeedForList f : feedDao.selectFeedsForListByGroupId(g.getId())) {
                                        rvItems.add(f);
                                    }


                                }

                                return rvItems;
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

        return groupDao.selectGroupsWithAllFeeds();

    }

    public void setFeedsSelected(List<FeedEntity> selectedFeedEntities) {


        executor.execute(() -> {
            feedDao.updateFeeds(selectedFeedEntities);

        });

    }

    public void setFeedsToBeShownForGroup(Group fg) {

        executor.execute(() -> {
            feedDao.setFeedsToBeShownForGroup(fg.getId());

        });
    }

    public void unsetFeedsToBeShownForGroup(Group fg) {

        executor.execute(() -> {
            feedDao.unsetFeedsToBeShownForGroup(fg.getId());

        });
    }

    public void setFeedGroupChecked(long feedGroupId) {

        executor.execute(() -> {
            groupDao.setFeedGroupChecked(feedGroupId);

        });

    }


    public void unsetFeedGroupChecked(long feedGroupId) {

        executor.execute(() -> {
            groupDao.unsetFeedGroupChecked(feedGroupId);

        });

    }
}
