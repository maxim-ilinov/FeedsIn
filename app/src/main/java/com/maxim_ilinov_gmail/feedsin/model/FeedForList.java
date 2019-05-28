package com.maxim_ilinov_gmail.feedsin.model;


import androidx.annotation.Nullable;

import java.util.Objects;

public class FeedForList implements RvItem {

    private int id;

    private long feedGroupId;

    private String title;

    private String customTitle;

    private String description;

    private String rssFeedLink;

    public void setId(int id) {
        this.id = id;
    }

    public void setFeedGroupId(long feedGroupId) {
        this.feedGroupId = feedGroupId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCustomTitle(String customTitle) {
        this.customTitle = customTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRssFeedLink(String rssFeedLink) {
        this.rssFeedLink = rssFeedLink;
    }

    public String getCustomTitle() {
        return customTitle;
    }

    public int getId() {
        return id;
    }

    public long getFeedGroupId() {
        return feedGroupId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getRssFeedLink() {
        return rssFeedLink;
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FeedForList)) {
            return false;
        }

        FeedForList other = (FeedForList) obj;

        return this.rssFeedLink.equals(other.rssFeedLink);

    }


    @Override
    public int hashCode() {

        return 7 * (rssFeedLink != null ? rssFeedLink.hashCode() : 0);

        //return Objects.hash(getId(), getFeedGroupId(), getTitle(), getCustomTitle(), getDescription(), getRssFeedLink());
    }
}
