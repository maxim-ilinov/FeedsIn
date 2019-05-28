package com.maxim_ilinov_gmail.feedsin.model;

import android.text.Html;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.text.DateFormat;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/*
 All elements of an item are optional, however at least one of title or description must be present.
*/

@Entity
@Root(name = "item", strict = false)
public class Article {

    @Ignore
    private final static int DESC_LEN = 120;

    @PrimaryKey(autoGenerate = true)
    private int id;

    @Element(name = "guid", required = false)
    private String guid;

    private int rssFeedId;

    @Element(name = "title", required = false)
    private String title;

    @Element(name = "guid", required = false)
    @Path("item")
    private String link;

    @Element(name = "description", required = false)
    private String description;

    @Element(name = "pubDate", required = false)
    private String pubDate;

    private int hash;

    private Date pubDateNorm;

    public int getHash() {

        return hash;
    }

    public void setHash(int hash) {

        this.hash = hash;
    }

    private String imageUrl;

    @Override
    public int hashCode() {

        return 7 * (title != null ? title.hashCode() : 0) + 11 * (description != null ? description.hashCode() : 0);

    }

    @Override
    public boolean equals(@Nullable Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Article)) {
            return false;
        }

        Article other = (Article) obj;

        return this.title.equals(other.title) && this.description.equals(other.description);

    }

    public Article() { //empty constructor is necessary for simplexml
    }

    @Ignore
    public Article(String pubDate,
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

    public Date getPubDateNorm() {

        return pubDateNorm;
    }

    public void setPubDateNorm(Date pubDateNorm) {

        this.pubDateNorm = pubDateNorm;
    }

    public String getFormattedDate()
    {


        //SimpleDateFormat sdf= new SimpleDateFormat("dd.MM.yy hh:mm", Locale.ge);

        if (pubDateNorm != null) {

            return DateFormat.getDateTimeInstance().format(pubDateNorm);

        }


        return null;


    }
    public String getClearedDesc()
    {
        String desc = Html.fromHtml(description, null, null).toString().trim();

        return desc;
    }

    public String getShortDesc()
    {


        String desc = getClearedDesc();

        if (desc.length() > DESC_LEN) {

            desc = desc.substring(0, DESC_LEN).trim() + "...";
        }

        return desc;

    }

}
