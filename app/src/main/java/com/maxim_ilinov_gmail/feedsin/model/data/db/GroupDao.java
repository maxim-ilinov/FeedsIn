package com.maxim_ilinov_gmail.feedsin.model.data.db;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.model.GroupEntity;
import com.maxim_ilinov_gmail.feedsin.model.GroupForDrawerMenu;
import com.maxim_ilinov_gmail.feedsin.model.GroupForList;
import com.maxim_ilinov_gmail.feedsin.model.GroupWithFeeds;

import java.util.List;

@Dao
public abstract class GroupDao {

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void updateGroup(GroupEntity rssGroupEntity);


    @Query("SELECT * FROM GroupEntity LIMIT :loadCount OFFSET :startPosition")
    public abstract List<GroupEntity> selectGroupsForPaging(int startPosition, int loadCount);

    @Query("select * from GroupEntity where checked=1")
    public abstract LiveData<List<GroupWithFeeds>> getCheckedFeedGroups();

    @Transaction
    @Query("SELECT * from GroupEntity")
    public abstract LiveData<List<GroupWithFeeds>> selectGroupsWithAllFeeds();

    @Query("SELECT * from GroupEntity")
    public abstract LiveData<List<GroupEntity>> selectFeedGroupsLiveData();

    @Query("SELECT * from GroupEntity")
    public abstract List<GroupEntity> selectFeedGroups();


    @Query("SELECT id,name from GroupEntity")
    public abstract LiveData<List<GroupForDrawerMenu>> selectGroupsForDrawerMenu();


    public LiveData<List<GroupForDrawerMenu>> selectDistinctGroupsForDrawerMenu() {

        return Transformations.distinctUntilChanged(selectGroupsForDrawerMenu());

    }


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insertFeedGroups(List<GroupEntity> groupEntities);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insertFeedGroup(GroupEntity feedGroupEntity);

    @Query("UPDATE GroupEntity SET checked = 1 WHERE id =:feedGroupId")
    public abstract void setFeedGroupChecked(long feedGroupId);

    @Query("UPDATE GroupEntity SET checked = 0 WHERE id =:feedGroupId")
    public abstract void unsetFeedGroupChecked(long feedGroupId);



    @Query("select id from GroupEntity where name=:groupName")
    public abstract long getGroupId(String groupName);

    @Query("select id from GroupEntity order BY id ASC LIMIT 1")
    public abstract long getFirstRssFeedGroupId();

    @Query("select count(id) from GroupEntity where id=:groupId")
    public abstract long countRssFeedGroupsWithId(int groupId);

    @Query("select count(id) from GroupEntity")
    public abstract int countGroups();

    @Query("select sum(rows) from (select count(id) as rows from GroupEntity union all select count(id) as rows from FeedEntity)")
    public abstract int countGroupsAndFeeds();




    @Query("SELECT * from GroupEntity WHERE id =:groupId")
    public abstract LiveData<GroupEntity> getGroupById(int groupId);
}
