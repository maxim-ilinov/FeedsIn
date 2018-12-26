package com.maxim_ilinov_gmail.feedsin.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;
import java.util.List;

/*
https://cyber.harvard.edu/rss/rss.html

Required channel elements:

Element	Description	            Example
title	The name of the channel. It's how people refer to your service. If you have an HTML website that contains the same information as your RSS file, the title of your channel should be the same as the title of your website. 	GoUpstate.com News Headlines
link	The URL to the HTML website corresponding to the channel.	http://www.goupstate.com/
description       	Phrase or sentence describing the channel.	The latest news from GoUpstate.com, a Spartanburg Herald-Journal Web site.
*/




import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@NamespaceList({
        @Namespace(reference = "http://www.w3.org/2005/Atom", prefix = "atom")
})
@Entity
@Root(name="channel", strict=false)
public class RssFeed {



    @PrimaryKey(autoGenerate = true)
    private int id;

    private String customTitle;

    private boolean isSelected;

    private int rssFeedGroupId;

    private String rssFeedLink;

    private String imageUrl;

    // Tricky part in Simple XML because the link is named twice (thanks https://gist.github.com/macsystems/01d7e80554efd344b1f9)
    @ElementList(entry = "link", inline = true, required = false)
    @Ignore
    private List<Link> links;

    @Path("channel")
    @Element(name="title")
    private String title;

    @Path("channel")
    @Element(name="description",required = false)
    private String description;

    @ElementList(entry="item", inline=true, type = RssItem.class)
    @Path("channel")
    @Ignore
    private List<RssItem> rssItemList;



    // empty constructor necessary for simplexml
    public RssFeed() {
    }


    public RssFeed(String rssFeedLink, boolean isSelected) {
        this.rssFeedLink = rssFeedLink;

        this.isSelected= isSelected;

    }

    public RssFeed(String title, String rssFeedLink, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getCustomTitle() {
        return customTitle;
    }

    public void setCustomTitle(String customTitle) {
        this.customTitle = customTitle;
    }

    public int getRssFeedGroupId() {
        return rssFeedGroupId;
    }

    public void setRssFeedGroupId(int rssFeedGroupId) {
        this.rssFeedGroupId = rssFeedGroupId;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<RssItem> getRssItemList() {
        return rssItemList;
    }

    public void setRssItemList(List<RssItem> rssItemList) {
        this.rssItemList = rssItemList;
    }

    public String getRssFeedLink() {
        return rssFeedLink;
    }

    public void setRssFeedLink(String rssFeedLink) {
        this.rssFeedLink = rssFeedLink;
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

    public void setTitle(String title) {
        this.title = title;
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

    @NonNull
    @Override
    public String toString() {
        return
                "id = " + this.getId() + " " +
                "title = " + this.getTitle() + " " +
                "rssFeedLink = " + this.getRssFeedLink() + " " +
                "selected = " + this.isSelected() + " " +
                "group id = " + this.getRssFeedGroupId();

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
