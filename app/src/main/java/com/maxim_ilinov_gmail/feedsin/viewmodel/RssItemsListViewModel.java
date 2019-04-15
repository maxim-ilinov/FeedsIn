package com.maxim_ilinov_gmail.feedsin.viewmodel;

import android.app.Application;

import com.maxim_ilinov_gmail.feedsin.model.FeedGroupWithFeeds;
import com.maxim_ilinov_gmail.feedsin.model.RssFeed;
import com.maxim_ilinov_gmail.feedsin.model.RssItem;
import com.maxim_ilinov_gmail.feedsin.model.repository.RssItemsRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;


public class RssItemsListViewModel extends AndroidViewModel {


    private static final String TAG = "RssItemsListViewModel";

    private final LiveData<PagedList<RssItem>> currentRssItemsListPl;

    private RssItemsRepository rssItemsRepository;


    private MutableLiveData<Integer> selectedPosition;

    private MutableLiveData<RssItem> selectedRssItem;



    private LiveData<List<RssItem>> currentRssItemsList = new MutableLiveData<>();

    public RssItemsListViewModel(Application application) {
        super(application);

        rssItemsRepository = new RssItemsRepository(application);



        currentRssItemsList = rssItemsRepository.getItemsForSelectedFeeds();

        currentRssItemsListPl = rssItemsRepository.getItemsForSelectedFeedsPl();


    }

    public LiveData<List<RssItem>> getCurrentRssItemsList() {

        return currentRssItemsList;

    }


    public LiveData<PagedList<RssItem>> getCurrentRssItemsListPl() {

        return rssItemsRepository.getItemsForSelectedFeedsPl();

    }


    public void updateData() {
        rssItemsRepository.loadRssData();
    }


    public LiveData<List<RssItem>> getRssItemList(List<RssFeed> rssFeeds) {
        return currentRssItemsList;
    }


    public LiveData<List<FeedGroupWithFeeds>> getCheckedFeedGroups() {

        return rssItemsRepository.getCheckedFeedGroups();

    }
}