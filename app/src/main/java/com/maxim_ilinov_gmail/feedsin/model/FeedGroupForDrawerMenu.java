package com.maxim_ilinov_gmail.feedsin.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FeedGroupForDrawerMenu {


    private int id;

    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeedGroupForDrawerMenu)) return false;
        FeedGroupForDrawerMenu that = (FeedGroupForDrawerMenu) o;
        return getId() == that.getId() &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        // return Objects.hash(getId(), getName());

        return 7 * (name != null ? name.hashCode() : 0) ;

    }
}
