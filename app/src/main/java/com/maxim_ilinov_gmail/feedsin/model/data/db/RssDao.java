package com.maxim_ilinov_gmail.feedsin.model.data.db;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.maxim_ilinov_gmail.feedsin.model.FeedGroup;
import com.maxim_ilinov_gmail.feedsin.model.FeedGroupForDrawerMenu;
import com.maxim_ilinov_gmail.feedsin.model.FeedGroupWithFeeds;
import com.maxim_ilinov_gmail.feedsin.model.RssFeed;
import com.maxim_ilinov_gmail.feedsin.model.RssItem;

import java.util.List;

@Dao
public  abstract class RssDao {

    //RSS items

    @Insert(onConflict = OnConflictStrategy.IGNORE )
   public abstract long insertRssItem(RssItem rssItem);

    @Query("SELECT ri.* FROM rssfeed rf, rssitem ri WHERE ri.rssFeedId = rf.id and rf.toBeShown = 1 order by pubDateNorm desc")
    public abstract LiveData <List<RssItem>> selectItemsForSelectedFeeds ();

    @Query("select count(id) from RssItem where guid=:itemGuid")
    public abstract long countRssItemWithGuid(String itemGuid);//TODO rewrite, guid is optional in rss item (see rfc)

    @Query("select count(id) from RssItem where title=:title and description = :desc")
    public abstract long countRssItemWithTitleAndDesc(String title, String desc);

    @Query("select count(id) from RssItem where hash=:hash")
    public abstract long countRssItemWithHash(int hash);

    @Query("DELETE FROM rssitem")
    public abstract void deleteAllRssItems();

    @Query("SELECT * from rssitem WHERE rssFeedId IN (:feedIds)")
    public abstract LiveData<List<RssItem>> selectItemsByFeedIds(int[] feedIds);

    @Query("SELECT ri.* FROM rssfeed rf, rssitem ri, feedgroup fg WHERE ri.rssFeedId = rf.id and fg.id = rf.feedGroupId and  fg.checked = 1 order by pubDateNorm desc")
    public abstract DataSource.Factory<Integer, RssItem> selectItemsForSelectedFeedsPl();


    //groups



    @Insert (onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insertFeedGroups(List<FeedGroup> feedGroups);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insertFeedGroup(FeedGroup feedGroup);

    @Query("UPDATE feedgroup SET checked = 1 WHERE id =:feedGroupId")
    public abstract void setFeedGroupChecked(long feedGroupId);

    @Query("UPDATE feedgroup SET checked = 0 WHERE id =:feedGroupId")
    public abstract void unsetFeedGroupChecked(long feedGroupId);

    @Query("select * from FeedGroup where checked=1")
    public abstract LiveData<List<FeedGroupWithFeeds>> getCheckedFeedGroups();


    @Query("UPDATE rssfeed SET toBeShown =1 WHERE feedGroupId =:groupId")
    public abstract void setFeedsToBeShownForGroup(int groupId);






    @Query("UPDATE rssfeed SET toBeShown = 0 WHERE feedGroupId =:groupId")
    public abstract void unsetFeedsToBeShownForGroup(int groupId);


    @Update(onConflict= OnConflictStrategy.IGNORE)
    public abstract void updateFeed (RssFeed rssFeed);



    @Query("select id from FeedGroup where name=:groupName")
    public abstract long getGroupId(String groupName);

    @Query("select id from FeedGroup order BY id ASC LIMIT 1")
    public abstract long getFirstRssFeedGroupId();

    @Query("select count(id) from FeedGroup where id=:groupId")
    public abstract long countRssFeedGroupsWithId(int groupId);

    /*@Query("SELECT * FROM FeedGroup ORDER BY id")
    LiveData <List<FeedGroup>> selectAllRssFeedGroups();
    */

    @Transaction
    @Query("SELECT * from feedgroup")
    public abstract LiveData<List<FeedGroupWithFeeds>> selectGroupsWithAllFeeds();

    @Query("SELECT * from feedgroup")
    public abstract LiveData<List<FeedGroup>> selectFeedGroups();


    @Query("SELECT id,name from feedgroup")
    public abstract LiveData<List<FeedGroupForDrawerMenu>> selectGroupsForDrawerMenu();

    public LiveData<List<FeedGroupForDrawerMenu>> selectDistinctGroupsForDrawerMenu()
    {

     return Transformations.distinctUntilChanged(selectGroupsForDrawerMenu());

    }




    // feeds

    @Update
    public abstract void updateFeeds(List<RssFeed> selectedFeeds);

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    public abstract long insertRssFeed(RssFeed rssFeed);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insertRssFeeds(List<RssFeed> rssFeeds);

    @Query("SELECT * FROM rssfeed ORDER BY id")
    public abstract LiveData <List<RssFeed>> selectAllRssFeeds();

    @Query("SELECT * FROM rssfeed WHERE toBeShown = 1")
    public abstract LiveData <List<RssFeed>> selectSelectedRssFeeds();

    @Query("SELECT * FROM rssfeed WHERE toBeShown = 1")
    public abstract List<RssFeed> selectSelectedRssFeedsSynchronised();

    @Query("SELECT * FROM rssfeed")
    public abstract List<RssFeed> selectRssFeedsSync();

    @Query("SELECT * FROM rssfeed WHERE feedGroupId =:groupId")
    public abstract List<RssFeed> selectRssFeedsForGroupSync(int groupId);



    @Query ("select count(id) from rssfeed where rssFeedLink =:rssLink")
    public abstract long feedWithUrlCount(String rssLink);

    @Query("DELETE FROM rssfeed")
    public abstract void deleteAllFeeds();


}
