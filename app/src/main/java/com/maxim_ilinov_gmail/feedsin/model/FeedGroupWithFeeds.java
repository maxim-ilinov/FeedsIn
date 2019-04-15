package com.maxim_ilinov_gmail.feedsin.model;

import androidx.room.Relation;

import java.util.List;

public class FeedGroupWithFeeds {

    private int id;
    private String name;

    @Relation(parentColumn = "id", entityColumn = "feedGroupId")
    private List<RssFeed> rssFeeds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RssFeed> getRssFeeds() {
        return rssFeeds;
    }

    public void setRssFeeds(List<RssFeed> rssFeeds) {
        this.rssFeeds = rssFeeds;
    }
}
