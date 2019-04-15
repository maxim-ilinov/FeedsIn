package com.maxim_ilinov_gmail.feedsin.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;



@Entity(indices = {@Index(value = {"name"}, unique = true)})

public class FeedGroup {
    @Ignore
    private static final String DEF_GROUP_NAME = "Ungrouped";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

 /*  public List<RssFeed> getRssFeeds() {
        return rssFeeds;
    }

    public void setRssFeeds(List<RssFeed> rssFeeds) {
        this.rssFeeds = rssFeeds;
    }
*/
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private Boolean checked;

    @Ignore
    private List<RssFeed> rssFeeds;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public FeedGroup(String name) {
        this.name = name;
    }

    public List<RssFeed> getRssFeeds() {
        return rssFeeds;
    }

    public void setRssFeeds(List<RssFeed> rssFeeds) {
        this.rssFeeds = rssFeeds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<FeedGroup> populateData() {

        List<FeedGroup> feedGroups = new ArrayList();

        feedGroups.add(new FeedGroup("Politics"));
        feedGroups.add(new FeedGroup("Movies"));
        feedGroups.add(new FeedGroup("Sports"));


        return feedGroups;

    }

}
