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

import com.maxim_ilinov_gmail.feedsin.model.Article;
import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.model.FeedForList;
import com.maxim_ilinov_gmail.feedsin.model.GroupEntity;
import com.maxim_ilinov_gmail.feedsin.model.GroupForDrawerMenu;
import com.maxim_ilinov_gmail.feedsin.model.GroupForList;
import com.maxim_ilinov_gmail.feedsin.model.GroupWithFeeds;

import java.util.List;

@Dao
public  abstract class RssDao {

    //RSS items

    @Insert(onConflict = OnConflictStrategy.IGNORE )
   public abstract long insertRssItem(Article rssItem);

    @Query("SELECT ri.* FROM FeedEntity rf, Article ri WHERE ri.rssFeedId = rf.id and rf.toBeShown = 1 order by pubDateNorm desc")
    public abstract LiveData <List<Article>> selectItemsForSelectedFeeds ();

    @Query("select count(id) from Article where guid=:itemGuid")
    public abstract long countRssItemWithGuid(String itemGuid);//TODO rewrite, guid is optional in rss item (see rfc)

    @Query("select count(id) from Article where title=:title and description = :desc")
    public abstract long countRssItemWithTitleAndDesc(String title, String desc);

    @Query("select count(id) from Article where hash=:hash")
    public abstract long countRssItemWithHash(int hash);

    @Query("DELETE FROM Article")
    public abstract void deleteAllRssItems();

    @Query("SELECT * from Article WHERE rssFeedId IN (:feedIds)")
    public abstract LiveData<List<Article>> selectItemsByFeedIds(int[] feedIds);

    @Query("SELECT ri.* FROM FeedEntity rf, Article ri, GroupEntity fg WHERE ri.rssFeedId = rf.id and fg.id = rf.feedGroupId and  fg.checked = 1 order by pubDateNorm desc")
    public abstract DataSource.Factory<Integer, Article> selectItemsForSelectedFeedsPl();



    //groups


    @Query("SELECT * FROM GroupEntity LIMIT :loadCount OFFSET :startPosition")
    public abstract List<GroupForList> selectGroupsForList(int startPosition, int loadCount);

    @Query("SELECT * FROM FeedEntity")
    public abstract List<FeedForList> selectFeedsForList();

    @Query("SELECT * FROM FeedEntity where feedGroupId =:groupId")
    public abstract List<FeedForList> selectFeedsForListByGroupId(long groupId);


    @Insert (onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insertFeedGroups(List<GroupEntity> groupEntities);



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insertFeedGroup(GroupEntity feedGroupEntity);

    @Query("UPDATE GroupEntity SET checked = 1 WHERE id =:feedGroupId")
    public abstract void setFeedGroupChecked(long feedGroupId);

    @Query("UPDATE GroupEntity SET checked = 0 WHERE id =:feedGroupId")
    public abstract void unsetFeedGroupChecked(long feedGroupId);

    @Query("select * from GroupEntity where checked=1")
    public abstract LiveData<List<GroupWithFeeds>> getCheckedFeedGroups();


    @Query("UPDATE FeedEntity SET toBeShown =1 WHERE feedGroupId =:groupId")
    public abstract void setFeedsToBeShownForGroup(int groupId);






    @Query("UPDATE FeedEntity SET toBeShown = 0 WHERE feedGroupId =:groupId")
    public abstract void unsetFeedsToBeShownForGroup(int groupId);


    @Update(onConflict= OnConflictStrategy.IGNORE)
    public abstract void updateFeed (FeedEntity rssFeedEntity);



    @Query("select id from GroupEntity where name=:groupName")
    public abstract long getGroupId(String groupName);

    @Query("select id from GroupEntity order BY id ASC LIMIT 1")
    public abstract long getFirstRssFeedGroupId();

    @Query("select count(id) from GroupEntity where id=:groupId")
    public abstract long countRssFeedGroupsWithId(int groupId);

    /*@Query("SELECT * FROM GroupEntity ORDER BY id")
    LiveData <List<GroupEntity>> selectAllRssFeedGroups();
    */

 @Query ("select count(id) from GroupEntity")
 public abstract int countGroups();

    @Query ("select count(id) from feedentity")
    public abstract int countFeeds();


 @Query ("select sum(rows) from (select count(id) as rows from GroupEntity union all select count(id) as rows from FeedEntity)")
 public abstract int countGroupsAndFeeds();

    @Transaction
    @Query("SELECT * from GroupEntity")
    public abstract LiveData<List<GroupWithFeeds>> selectGroupsWithAllFeeds();

    @Query("SELECT * from GroupEntity")
    public abstract LiveData<List<GroupEntity>> selectFeedGroups();


    @Query("SELECT id,name from GroupEntity")
    public abstract LiveData<List<GroupForDrawerMenu>> selectGroupsForDrawerMenu();

    public LiveData<List<GroupForDrawerMenu>> selectDistinctGroupsForDrawerMenu()
    {

     return Transformations.distinctUntilChanged(selectGroupsForDrawerMenu());

    }




    // feeds

    @Update
    public abstract void updateFeeds(List<FeedEntity> selectedFeedEntities);

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    public abstract long insertRssFeed(FeedEntity rssFeedEntity);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insertRssFeeds(List<FeedEntity> feedEntities);

    @Query("SELECT * FROM FeedEntity ORDER BY id")
    public abstract LiveData <List<FeedEntity>> selectAllRssFeeds();

    @Query("SELECT * FROM FeedEntity WHERE toBeShown = 1")
    public abstract LiveData <List<FeedEntity>> selectSelectedRssFeeds();

    @Query("SELECT * FROM FeedEntity WHERE toBeShown = 1")
    public abstract List<FeedEntity> selectSelectedRssFeedsSynchronised();

    @Query("SELECT * FROM FeedEntity")
    public abstract List<FeedEntity> selectRssFeedsSync();

    @Query("SELECT * FROM FeedEntity WHERE feedGroupId =:groupId")
    public abstract List<FeedEntity> selectRssFeedsForGroupSync(int groupId);



    @Query ("select count(id) from FeedEntity where rssFeedLink =:rssLink")
    public abstract long feedWithUrlCount(String rssLink);

    @Query("DELETE FROM FeedEntity")
    public abstract void deleteAllFeeds();


}
