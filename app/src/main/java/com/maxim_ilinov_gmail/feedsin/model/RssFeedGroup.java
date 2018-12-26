package com.maxim_ilinov_gmail.feedsin.model;

import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class RssFeedGroup {
    @Ignore
    private static final String DEF_GROUP_NAME = "Ungrouped";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<RssFeed> getRssFeeds() {
        return rssFeeds;
    }

    public void setRssFeeds(List<RssFeed> rssFeeds) {
        this.rssFeeds = rssFeeds;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    @Ignore
    private List<RssFeed> rssFeeds;

    public RssFeedGroup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static RssFeedGroup populateData() {

        return new RssFeedGroup(DEF_GROUP_NAME);

    }

}
