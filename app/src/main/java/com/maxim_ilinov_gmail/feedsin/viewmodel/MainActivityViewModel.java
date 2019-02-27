package com.maxim_ilinov_gmail.feedsin.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.maxim_ilinov_gmail.feedsin.model.RssFeed;
import com.maxim_ilinov_gmail.feedsin.model.RssFeedGroup;
import com.maxim_ilinov_gmail.feedsin.model.repository.RssFeedsRepository;


import java.util.List;


public class MainActivityViewModel extends AndroidViewModel {


    private RssFeedsRepository rssFeedsRepository;

    private LiveData<List<RssFeedGroup>> rssFeedGroups;
    private LiveData<List<RssFeed>> rssFeeds;

    private LiveData<List<RssFeed>> selectedRssFeeds;



    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        rssFeedsRepository = new RssFeedsRepository(application);

        rssFeedGroups = rssFeedsRepository.getRssFeedGroups();

        rssFeeds = rssFeedsRepository.getRssFeeds();

        selectedRssFeeds = rssFeedsRepository.getSelectedRssFeeds();
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

}
