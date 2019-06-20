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
import com.maxim_ilinov_gmail.feedsin.model.Group;
import com.maxim_ilinov_gmail.feedsin.model.GroupForDrawerMenu;
import com.maxim_ilinov_gmail.feedsin.model.GroupForList;
import com.maxim_ilinov_gmail.feedsin.model.GroupWithFeeds;

import java.util.List;

@Dao
public abstract class GroupDao {


    @Query("SELECT * FROM `group` LIMIT :loadCount OFFSET :startPosition")
    public abstract List<GroupForList> selectGroupsForList(int startPosition, int loadCount);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insertFeedGroups(List<Group> groups);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insertFeedGroup(Group feedGroup);

    @Query("UPDATE `Group` SET checked = 1 WHERE id =:feedGroupId")
    public abstract void setFeedGroupChecked(long feedGroupId);

    @Query("UPDATE `Group` SET checked = 0 WHERE id =:feedGroupId")
    public abstract void unsetFeedGroupChecked(long feedGroupId);

    @Query("select * from `Group` where checked=1")
    public abstract LiveData<List<GroupWithFeeds>> getCheckedFeedGroups();

    @Query("select id from `Group` where name=:groupName")
    public abstract long getGroupId(String groupName);

    @Query("select id from `Group` order BY id ASC LIMIT 1")
    public abstract long getFirstRssFeedGroupId();

    @Query("select count(id) from `Group` where id=:groupId")
    public abstract long countRssFeedGroupsWithId(int groupId);

    @Query("select count(id) from `group`")
    public abstract int countGroups();

    @Query("select sum(rows) from (select count(id) as rows from `group` union all select count(id) as rows from FeedEntity)")
    public abstract int countGroupsAndFeeds();

    @Transaction
    @Query("SELECT * from `Group`")
    public abstract LiveData<List<GroupWithFeeds>> selectGroupsWithAllFeeds();

    @Query("SELECT * from `Group`")
    public abstract LiveData<List<Group>> selectFeedGroups();

    public LiveData<List<GroupForDrawerMenu>> selectDistinctGroupsForDrawerMenu() {

        return Transformations.distinctUntilChanged(selectGroupsForDrawerMenu());

    }

    @Query("SELECT id,name from `Group`")
    public abstract LiveData<List<GroupForDrawerMenu>> selectGroupsForDrawerMenu();




}
