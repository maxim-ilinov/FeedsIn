package com.maxim_ilinov_gmail.feedsin.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
https://cyber.harvard.edu/rss/rss.html

Required channel elements:

Element	Description	            Example
title	The name of the channel. It's how people refer to your service. If you have an HTML website that contains the same information as your RSS file, the title of your channel should be the same as the title of your website. 	GoUpstate.com News Headlines
link	The URL to the HTML website corresponding to the channel.	http://www.goupstate.com/
description       	Phrase or sentence describing the channel.	The latest news from GoUpstate.com, a Spartanburg Herald-Journal Web site.
*/

@NamespaceList({
        @Namespace(reference = "http://www.w3.org/2005/Atom", prefix = "atom")
})
@Entity
@Root(name = "channel", strict = false)
public class FeedEntity implements RvItem {


    @PrimaryKey(autoGenerate = true)
    private int id;

    private String customTitle;

    private boolean toBeShown;

    private long feedGroupId;

    private String rssFeedLink;

    private String imageUrl;


    // Tricky part in Simple XML because the link is named twice (thanks https://gist.github.com/macsystems/01d7e80554efd344b1f9)
    @ElementList(entry = "link", inline = true, required = false)
    @Ignore
    private List<Link> links;

    @Path("channel")
    @Element(name = "title")
    private String title;

    @Path("channel")
    @Element(name = "description", required = false)
    private String description;

    @ElementList(entry = "item", inline = true, type = Article.class)
    @Path("channel")
    @Ignore
    private List<Article> articleList;

    // empty constructor necessary for simplexml
    public FeedEntity() {
    }

    @Ignore
    public FeedEntity(String customTitle, String feedLink) {
        this.customTitle = customTitle;
        this.rssFeedLink = feedLink;
    }

    @Ignore
    public FeedEntity(String customTitle, String feedLink, long groupId) {
        this.customTitle = customTitle;
        this.rssFeedLink = feedLink;
        this.setFeedGroupId(groupId);
    }

    @Ignore
    public FeedEntity(String feedLink) {

        this.rssFeedLink = feedLink;


    }

    @Ignore
    public FeedEntity(String title, String feedLink, String description, String imageUrl) {
        this.title = title;
        this.rssFeedLink = feedLink;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public static List<FeedEntity> populateData(long[] groupIds) {

        Random rand = new Random();

        List<FeedEntity> feedEntities = new ArrayList<>();

        int groupsCount = groupIds.length;

        // long  i = groupIds[groupsCount];


        feedEntities.add(new FeedEntity("ixbt", "http://www.ixbt.com/export/articles.rss", groupIds[rand.nextInt(groupsCount)]));
        feedEntities.add(new FeedEntity("habr 1", "https://habr.com/rss/hub/apps_design/all/?hl=ru&fl=ru", groupIds[rand.nextInt(groupsCount)]));
        feedEntities.add(new FeedEntity("habr 2", "https://habr.com/rss/all/all/?hl=ru&fl=ru", groupIds[rand.nextInt(groupsCount)]));
        feedEntities.add(new FeedEntity("bash", "https://bash.im/rss/", groupIds[rand.nextInt(groupsCount)]));

        feedEntities.add(new FeedEntity("yandex", "https://news.yandex.ru/politics.rss", groupIds[rand.nextInt(groupsCount)]));

        return feedEntities;


        //  addFeed ("https://bash.im/rss/", true);
        //  addFeed ("https://habr.com/rss/best/?hl=ru&fl=ru", true); //top
        // addFeed ("https://www.nytimes.com/svc/collections/v1/publish/https://www.nytimes.com/section/world/rss.xml", true);
        // addFeed ("https://news.yandex.ru/politics.rss", true);

        //atom
        // addFeed ("https://www.reddit.com/r/worldnews/.rss", true);
        //addFeed ("https://plugins.geany.org/install.html", true);
    }

    public String getCustomTitle() {
        return customTitle;
    }

    public void setCustomTitle(String customTitle) {
        this.customTitle = customTitle;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int hashCode() {

        return 7 * (rssFeedLink != null ? rssFeedLink.hashCode() : 0);

        //return Objects.hash(getId(), getFeedGroupId(), getTitle(), getCustomTitle(), getDescription(), getRssFeedLink());
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FeedEntity)) {
            return false;
        }

        FeedEntity other = (FeedEntity) obj;

        return this.rssFeedLink.equals(other.rssFeedLink);

    }

    @NonNull
    @Override
    public String toString() {
        return
                "id = " + this.getId() + " " +
                        "title = " + this.getTitle() + " " +
                        "rssFeedLink = " + this.getRssFeedLink() + " " +
                        "selected = " + this.isToBeShown() + " " +
                        "group id = " + this.getFeedGroupId();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getRssFeedLink() {
        return rssFeedLink;
    }

    public boolean isToBeShown() {
        return toBeShown;
    }

    public void setToBeShown(boolean isSelected) {
        this.toBeShown = isSelected;
    }

    public long getFeedGroupId() {
        return feedGroupId;
    }

    public void setFeedGroupId(long feedGroupId) {
        this.feedGroupId = feedGroupId;
    }

    public void setRssFeedLink(String rssFeedLink) {
        this.rssFeedLink = rssFeedLink;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class Link {
        @Attribute(required = false)
        public String href;

        @Attribute(required = false)
        public String rel;

        @Attribute(name = "type", required = false)
        public String contentType;

        @Text(required = false)
        public String link;

        public Link() {
        }
    }

}
