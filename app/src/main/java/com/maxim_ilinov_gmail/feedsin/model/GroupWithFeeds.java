package com.maxim_ilinov_gmail.feedsin.model;

import androidx.room.Relation;

import java.util.List;

public class GroupWithFeeds {

    private int id;
    private String name;

    @Relation(parentColumn = "id", entityColumn = "feedGroupId")
    private List<FeedEntity> feedEntities;

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

    public List<FeedEntity> getFeedEntities() {
        return feedEntities;
    }

    public void setFeedEntities(List<FeedEntity> feedEntities) {
        this.feedEntities = feedEntities;
    }
}
