package com.maxim_ilinov_gmail.feedsin.model.data.db;


import com.maxim_ilinov_gmail.feedsin.model.RssFeed;
import com.maxim_ilinov_gmail.feedsin.model.RssFeedGroup;
import com.maxim_ilinov_gmail.feedsin.model.RssItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
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

    @Query("SELECT ri.* FROM rssfeed rf, rssitem ri WHERE ri.rssFeedId = rf.id and rf.isSelected = 1 order by pubDateNorm desc")
    LiveData <List<RssItem>> selectItemsForSelectedFeeds ();

    @Query("SELECT ri.* FROM rssfeed rf, rssitem ri WHERE ri.rssFeedId = rf.id and rf.isSelected = 1 order by pubDateNorm desc")
    DataSource.Factory<Integer, RssItem> selectItemsForSelectedFeedsPl();



    @Query("select count(id) from RssItem where guid=:itemGuid")
    int countRssItemWithGuid(String itemGuid);//TODO rewrite, guid is optional in rss item (see rfc)

    @Query("select count(id) from RssItem where title=:title and description = :desc")
    int countRssItemWithTitleAndDesc(String title, String desc);

    @Query("select count(id) from RssItem where hash=:hash")
    int countRssItemWithHash(int hash);


    @Query("DELETE FROM rssitem")
    void deleteAllRssItems();




    @Update
    int updateFeed(RssFeed rssFeed);

    @Query ("select count(id) from rssfeed where rssFeedLink =:rssLink")
    long feedWithUrlCount(String rssLink);



    @Query("SELECT * from rssitem WHERE rssFeedId IN (:feedIds)")
    LiveData<List<RssItem>> selectItemsByFeedIds(int[] feedIds);


    @Query("DELETE FROM rssfeed")
    void deleteAllFeeds();






}
