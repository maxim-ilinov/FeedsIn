package com.maxim_ilinov_gmail.feedsin;

import android.app.Application;

import com.maxim_ilinov_gmail.feedsin.db.entities.RssFeed;
import com.maxim_ilinov_gmail.feedsin.db.entities.RssFeedGroup;
import com.maxim_ilinov_gmail.feedsin.db.entities.RssItem;
import com.maxim_ilinov_gmail.feedsin.repositories.RssRepository;

import java.util.List;
import java.util.TreeSet;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class RssItemsListViewModel extends AndroidViewModel {


    private static final String TAG = "RssItemsListViewModel";
    private RssRepository rssRepository;




    private LiveData<List<RssFeedGroup>> rssFeedGroups; // groups with feeds in them

    private LiveData<List<RssFeed>> rssFeeds;

    private LiveData<List<RssFeed>> selectedRssFeeds;

    private LiveData<List<RssItem>> currentRssItemsList = new MutableLiveData<>();

    public LiveData<List<RssItem>> getCurrentRssItemsList() {

            return currentRssItemsList;

    }

    public void setCurrentRssItemsList(LiveData<List<RssItem>> currentRssItemsList) {
        this.currentRssItemsList = currentRssItemsList;
    }

    public LiveData<List<RssFeed>> getRssFeeds() {

       return rssFeeds;
       //return rssRepository.getRssFeeds();

    }

    public void setRssFeeds(LiveData<List<RssFeed>> rssFeeds) {
        this.rssFeeds = rssFeeds;
    }

    public LiveData<List<RssFeed>> getSelectedRssFeeds() {
        return selectedRssFeeds;
    }




    public RssItemsListViewModel(Application application) {
        super(application);

        rssRepository = new RssRepository(application);

        rssFeedGroups = rssRepository.getRssFeedGroups();

        rssFeeds = rssRepository.getRssFeeds();

        selectedRssFeeds =  rssRepository.getSelectedRssFeeds();

       currentRssItemsList = rssRepository.getItemsForSelectedFeeds();

    }

    public LiveData<List<RssFeedGroup>>  getRssFeedGroups()
    {
        return rssFeedGroups;
    }


    public LiveData<List<RssItem>> getRssItemList(List<RssFeed> rssFeeds)
    {
        return currentRssItemsList;
    }


}