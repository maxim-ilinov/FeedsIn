package com.maxim_ilinov_gmail.feedsin.viewmodel;

import android.app.Application;

import com.maxim_ilinov_gmail.feedsin.model.RssFeed;
import com.maxim_ilinov_gmail.feedsin.model.RssFeedGroup;
import com.maxim_ilinov_gmail.feedsin.model.RssItem;
import com.maxim_ilinov_gmail.feedsin.model.repository.RssRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;


public class RssItemsListViewModel extends AndroidViewModel {


    private static final String TAG = "RssItemsListViewModel";

    private final LiveData<PagedList<RssItem>> currentRssItemsListPl;

    private RssRepository rssRepository;
    private LiveData<List<RssFeedGroup>> rssFeedGroups;
    private LiveData<List<RssFeed>> rssFeeds;

    private LiveData<List<RssFeed>> selectedRssFeeds;

    private LiveData<List<RssItem>> currentRssItemsList = new MutableLiveData<>();

    public RssItemsListViewModel(Application application) {
        super(application);

        rssRepository = new RssRepository(application);

        rssFeedGroups = rssRepository.getRssFeedGroups();

        rssFeeds = rssRepository.getRssFeeds();

        selectedRssFeeds = rssRepository.getSelectedRssFeeds();

        currentRssItemsList = rssRepository.getItemsForSelectedFeeds();

        currentRssItemsListPl = rssRepository.getItemsForSelectedFeedsPl();


    }

    public LiveData<List<RssItem>> getCurrentRssItemsList() {

        return currentRssItemsList;

    }


    public LiveData<PagedList<RssItem>> getCurrentRssItemsListPl() {

        return currentRssItemsListPl;

    }

    public LiveData<List<RssFeed>> getRssFeeds() {

        return rssFeeds;


    }


    public LiveData<List<RssFeed>> getSelectedRssFeeds() {
        return selectedRssFeeds;
    }

    public LiveData<List<RssFeedGroup>> getRssFeedGroups() {
        return rssFeedGroups;
    }

    public void updateData() {
        rssRepository.loadRssData();
    }


    public LiveData<List<RssItem>> getRssItemList(List<RssFeed> rssFeeds) {
        return currentRssItemsList;
    }


}