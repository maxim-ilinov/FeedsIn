package com.maxim_ilinov_gmail.feedsin.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;


@Entity(indices = {@Index(value = {"name"}, unique = true)})

public class GroupEntity implements RvItem {
    @Ignore
    private static final String DEF_GROUP_NAME = "Ungrouped";


    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private Boolean checked;

    @Ignore
    private List<FeedEntity> feedEntities;


    public GroupEntity(String name) {
        this.name = name;
    }

    public static List<GroupEntity> populateData() {

        List<GroupEntity> groupEntities = new ArrayList();

        groupEntities.add(new GroupEntity("Politics"));
        groupEntities.add(new GroupEntity("Movies"));
        groupEntities.add(new GroupEntity("Sports"));


        return groupEntities;

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


    @Override
    public int hashCode() {

        return 7 * (name != null ? name.hashCode() : 0);

    }

    @Override
    public boolean equals(@Nullable Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }
        if (!(obj instanceof GroupEntity)) {
            return false;
        }

        GroupEntity other = (GroupEntity) obj;

        return this.name.equals(other.name);

    }
}
