package com.maxim_ilinov_gmail.feedsin.db.entities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/*
 All elements of an item are optional, however at least one of title or description must be present.
*/

@Entity
@Root(name = "item", strict = false)
public class RssItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @Element(name = "guid",required = false)
    private String guid;

    private int rssFeedId;

    @Element(name = "title",required = false)
    private String title;



    @Element (name = "guid",required = false)
    @Path("item")
    private String link;

    @Element(name = "description",required = false)
    private String description;

    @Element(name = "pubDate",required = false)
    private String pubDate;

    private String imageUrl;




    public RssItem() {
    }

    public RssItem( String pubDate,
                    String title,
                    String link,
                    String description,
                    String guid) {
        this.guid = guid;
        this.pubDate = pubDate;
        this.title = title;
        this.link = link;
        this.description = description;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRssFeedId() {
        return rssFeedId;
    }

    public void setRssFeedId(int rssFeedId) {
        this.rssFeedId = rssFeedId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
