package com.maxim_ilinov_gmail.feedsin.db.dao;


import com.maxim_ilinov_gmail.feedsin.db.entities.RssFeed;
import com.maxim_ilinov_gmail.feedsin.db.entities.RssFeedGroup;
import com.maxim_ilinov_gmail.feedsin.db.entities.RssItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface RssDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    long insertRssFeed(RssFeed rssFeed);

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    long insertRssItem(RssItem rssItem);


    @Insert (onConflict = OnConflictStrategy.IGNORE)
    long createDefaultRssGroup (RssFeedGroup feedGroup);

    @Query("select id from rssfeedgroup order BY id ASC LIMIT 1")
    int getFirstRssFeedGroupId();

    @Query("select count(id) from rssfeedgroup where id=:groupId")
    int countRssFeedGroupsWithId(int groupId);


    @Query("SELECT * FROM rssfeedgroup ORDER BY id")
    LiveData <List<RssFeedGroup>> selectAllRssFeedGroups();

    @Query("SELECT * FROM rssfeed ORDER BY id")
    LiveData <List<RssFeed>> selectAllRssFeeds();

    @Query("SELECT * FROM rssfeed WHERE isSelected = 1")
    LiveData <List<RssFeed>> selectSelectedRssFeeds();

    @Query("SELECT * FROM rssfeed WHERE isSelected = 1")
    List<RssFeed> selectSelectedRssFeedsSync();

    @Query("SELECT ri.* FROM rssfeed rf, rssitem ri WHERE ri.rssFeedId = rf.id and rf.isSelected = 1")
    LiveData <List<RssItem>> selectItemsForSelectedFeeds ();

    @Query("select count(id) from RssItem where guid=:itemGuid")
    int countRssItemWithGuid(String itemGuid);















    @Update
    int updateFeed(RssFeed rssFeed);

    @Query ("select count(id) from rssfeed where rssFeedLink =:rssLink")
    long feedWithUrlCount(String rssLink);



    @Query("SELECT * from rssitem WHERE rssFeedId IN (:feedIds)")
    LiveData<List<RssItem>> selectItemsByFeedIds(int[] feedIds);


    @Query("DELETE FROM rssfeed")
    void deleteAllFeeds();






}
