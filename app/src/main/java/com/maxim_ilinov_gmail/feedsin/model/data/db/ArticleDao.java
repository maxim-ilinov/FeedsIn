package com.maxim_ilinov_gmail.feedsin.model.data.db;


import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.maxim_ilinov_gmail.feedsin.model.Article;

import java.util.List;

@Dao
public abstract class ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insertRssItem(Article rssItem);

    @Query("SELECT ri.* FROM FeedEntity rf, Article ri WHERE ri.rssFeedId = rf.id and rf.toBeShown = 1 order by pubDateNorm desc")
    public abstract LiveData<List<Article>> selectItemsForSelectedFeeds();

    @Query("select count(id) from Article where guid=:itemGuid")
    public abstract long countArticlesWithGuid(String itemGuid);//TODO rewrite, guid is optional in rss item (see rfc)

    @Query("select count(id) from Article where title=:title and description = :desc")
    public abstract long countArticlesWithTitleAndDesc(String title, String desc);

    @Query("select count(id) from Article where hash=:hash")
    public abstract long countArticlesWithHash(int hash);

    @Query("DELETE FROM Article")
    public abstract void deleteAllArticles();

    @Query("SELECT * from Article WHERE rssFeedId IN (:feedIds)")
    public abstract LiveData<List<Article>> selectArticlesByFeedIds(int[] feedIds);

    @Query("SELECT ri.* FROM FeedEntity rf, Article ri, GroupEntity fg WHERE ri.rssFeedId = rf.id and fg.id = rf.feedGroupId and  fg.checked = 1 order by pubDateNorm desc")
    public abstract DataSource.Factory<Integer, Article> selectArticlesForSelectedFeedsPl();


}
