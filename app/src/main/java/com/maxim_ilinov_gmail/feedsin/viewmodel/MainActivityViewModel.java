package com.maxim_ilinov_gmail.feedsin.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.maxim_ilinov_gmail.feedsin.model.FeedGroup;
import com.maxim_ilinov_gmail.feedsin.model.FeedGroupForDrawerMenu;
import com.maxim_ilinov_gmail.feedsin.model.RssFeed;
import com.maxim_ilinov_gmail.feedsin.model.repository.FeedsGroupsRepository;


import java.util.List;


public class MainActivityViewModel extends AndroidViewModel {


    //   private MenuRepository menuRepository;

    private FeedsGroupsRepository feedsGroupsRepository;

    // private LiveData<List<FeedGroupWithFeeds>> menuGroups;
    //  private LiveData<List<RssFeed>> rssFeeds;
    //   private LiveData<List<RssFeed>> selectedRssFeeds;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);


        feedsGroupsRepository = new FeedsGroupsRepository(application);

        //  menuRepository = new MenuRepository(application);


    }

    public LiveData<List<FeedGroupForDrawerMenu>> getMenuGroups() {
        return feedsGroupsRepository.selectGroupsForDrawerMenu();
    }

    public LiveData<List<RssFeed>> getFeeds() {
        return feedsGroupsRepository.getRssFeeds();
    }

    public void addGroup(String groupName) {
        feedsGroupsRepository.addGroup(groupName);
    }

    public void addFeed(String feedName, String feedUrl) {

        feedsGroupsRepository.addFeed(feedName, feedUrl);
    }


    public void addFeedToGroup(RssFeed rssFeed, int groupId) {
        feedsGroupsRepository.addFeedToGroup(rssFeed, groupId);
    }

    public void selectFeeds(List<RssFeed> feeds) {


        feedsGroupsRepository.setFeedsSelected(feeds);
    }

    public void setFeedGroupChecked(long feedGroupId) {

        feedsGroupsRepository.setFeedGroupChecked(feedGroupId);
    }

    public void unsetFeedGroupChecked(long feedGroupId) {

        feedsGroupsRepository.unsetFeedGroupChecked(feedGroupId);

    }

    public void setFeedsToBeShownForGroup(FeedGroup fg) {

        feedsGroupsRepository.setFeedsToBeShownForGroup(fg);
    }

    public void unsetFeedsToBeShownForGroup(FeedGroup fg) {

        feedsGroupsRepository.unsetFeedsToBeShownForGroup(fg);
    }


}
