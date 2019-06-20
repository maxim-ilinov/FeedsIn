package com.maxim_ilinov_gmail.feedsin.model.data.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.model.FeedForList;

import java.util.List;

@Dao
public abstract class FeedDao {


    @Query("SELECT * FROM FeedEntity")
    public abstract List<FeedForList> selectFeedsForList();

    @Query("SELECT * FROM FeedEntity where feedGroupId =:groupId")
    public abstract List<FeedForList> selectFeedsForListByGroupId(long groupId);

    @Query("UPDATE FeedEntity SET toBeShown =1 WHERE feedGroupId =:groupId")
    public abstract void setFeedsToBeShownForGroup(int groupId);

    @Query("UPDATE FeedEntity SET toBeShown = 0 WHERE feedGroupId =:groupId")
    public abstract void unsetFeedsToBeShownForGroup(int groupId);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void updateFeed(FeedEntity rssFeedEntity);

    @Query("select count(id) from feedentity")
    public abstract int countFeeds();

    @Query("select sum(rows) from (select count(id) as rows from `group` union all select count(id) as rows from FeedEntity)")
    public abstract int countGroupsAndFeeds();

    @Update
    public abstract void updateFeeds(List<FeedEntity> selectedFeedEntities);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insertRssFeed(FeedEntity rssFeedEntity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insertRssFeeds(List<FeedEntity> feedEntities);

    @Query("SELECT * FROM FeedEntity ORDER BY id")
    public abstract LiveData<List<FeedEntity>> selectAllRssFeeds();

    @Query("SELECT * FROM FeedEntity WHERE toBeShown = 1")
    public abstract LiveData<List<FeedEntity>> selectSelectedRssFeeds();

    @Query("SELECT * FROM FeedEntity WHERE toBeShown = 1")
    public abstract List<FeedEntity> selectSelectedRssFeedsSynchronised();

    @Query("SELECT * FROM FeedEntity")
    public abstract List<FeedEntity> selectRssFeedsSync();

    @Query("SELECT * FROM FeedEntity WHERE feedGroupId =:groupId")
    public abstract List<FeedEntity> selectRssFeedsForGroupSync(int groupId);

    @Query("select count(id) from FeedEntity where rssFeedLink =:rssLink")
    public abstract long feedWithUrlCount(String rssLink);

    @Query("DELETE FROM FeedEntity")
    public abstract void deleteAllFeeds();


}
