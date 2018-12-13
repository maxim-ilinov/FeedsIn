package com.maxim_ilinov_gmail.feedsin;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;


public class RssItemDetailsViewModel extends ViewModel {


    public MutableLiveData <RssItem> rssItemLiveData = new MutableLiveData<>();
    public MutableLiveData <RssFeed> rssFeedLiveData= new MutableLiveData<>();

    public MutableLiveData<String> itemDescLiveData = new MutableLiveData<String>() {
    };

    public MutableLiveData<String> getItemDescLiveData() {
        return itemDescLiveData;
    }

    public void setItemDescLiveData(MutableLiveData<String> itemDescLiveData) {
        this.itemDescLiveData = itemDescLiveData;
    }

    public RssItemDetailsViewModel() {


        RssItem rssItem =  new RssItem("some guid");
        rssItem.setTitle("some title");
        RssFeed rssFeed = new RssFeed();
        rssFeed.setTitle("feed title");

        itemDescLiveData.postValue("some item desc #1");

        this.rssItemLiveData.postValue(rssItem);
        this.rssFeedLiveData.postValue(rssFeed);

    }


    public void readMoreClicked() {
        Log.d("RssItemDetailsViewModel", "readMoreClicked ");

        RssItem rssItem =  new RssItem("some guid2");
        rssItem.setTitle("some title2");

        itemDescLiveData.postValue("some item desc #2");

        this.rssItemLiveData.postValue(rssItem);



    }
}