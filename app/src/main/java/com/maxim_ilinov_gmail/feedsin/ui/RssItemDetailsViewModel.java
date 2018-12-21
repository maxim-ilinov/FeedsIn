package com.maxim_ilinov_gmail.feedsin.ui;

import android.util.Log;

import com.maxim_ilinov_gmail.feedsin.db.entities.RssFeed;
import com.maxim_ilinov_gmail.feedsin.db.entities.RssItem;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class RssItemDetailsViewModel extends ViewModel {


    public MutableLiveData<RssItem> rssItemLiveData = new MutableLiveData<>();
    public MutableLiveData<RssFeed> rssFeedLiveData= new MutableLiveData<>();
    public MutableLiveData<String> itemDescLiveData = new MutableLiveData<>();



    public RssItemDetailsViewModel() {


//        RssItem rssItem =  new RssItem("some guid");
//        rssItem.setTitle("some title");
//        RssFeed rssFeed = new RssFeed();
//        rssFeed.setTitle("feed title");
//
//        itemDescLiveData.postValue("some item desc #1");
//
//        this.rssItemLiveData.postValue(rssItem);
//        this.rssFeedLiveData.postValue(rssFeed);

    }


    public void readMoreClicked() {
        Log.d("RssItemDetailsViewModel", "readMoreClicked ");

       // RssItem rssItem =  new RssItem("some guid2");
      //  rssItem.setTitle("some title2");

     //   itemDescLiveData.setValue("some item desc #2");

//        this.rssItemLiveData.postValue(rssItem);



    }
}