package com.maxim_ilinov_gmail.feedsin.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;


@Entity(indices = {@Index(value = {"name"}, unique = true)})

public class Group {
    @Ignore
    private static final String DEF_GROUP_NAME = "Ungrouped";


    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private Boolean checked;

    @Ignore
    private List<FeedEntity> feedEntities;


    public Group(String name) {
        this.name = name;
    }

    public static List<Group> populateData() {

        List<Group> groups = new ArrayList();

        groups.add(new Group("Politics"));
        groups.add(new Group("Movies"));
        groups.add(new Group("Sports"));


        return groups;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public List<FeedEntity> getFeedEntities() {
        return feedEntities;
    }

    public void setFeedEntities(List<FeedEntity> feedEntities) {
        this.feedEntities = feedEntities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
