package com.maxim_ilinov_gmail.feedsin.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.model.Group;
import com.maxim_ilinov_gmail.feedsin.model.GroupForDrawerMenu;
import com.maxim_ilinov_gmail.feedsin.model.repository.FeedsAndGroupsRepository;


import java.util.List;


public class MainActivityViewModel extends AndroidViewModel {


    //   private MenuRepository menuRepository;

    private FeedsAndGroupsRepository feedsAndGroupsRepository;

    // private LiveData<List<GroupWithFeeds>> menuGroups;
    //  private LiveData<List<FeedEntity>> rssFeeds;
    //   private LiveData<List<FeedEntity>> selectedRssFeeds;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);


        feedsAndGroupsRepository = new FeedsAndGroupsRepository(application);

        //  menuRepository = new MenuRepository(application);


    }

    public LiveData<List<GroupForDrawerMenu>> getMenuGroups() {
        return feedsAndGroupsRepository.selectGroupsForDrawerMenu();
    }

    public LiveData<List<FeedEntity>> getFeeds() {
        return feedsAndGroupsRepository.getRssFeeds();
    }

    public void addGroup(String groupName) {
        feedsAndGroupsRepository.addGroup(groupName);
    }

    public void addFeed(String feedName, String feedUrl) {

        feedsAndGroupsRepository.addFeed(feedName, feedUrl);
    }


    public void addFeedToGroup(FeedEntity rssFeedEntity, int groupId) {
        feedsAndGroupsRepository.addFeedToGroup(rssFeedEntity, groupId);
    }

    public void selectFeeds(List<FeedEntity> feedEntities) {


        feedsAndGroupsRepository.setFeedsSelected(feedEntities);
    }

    public void setFeedGroupChecked(long feedGroupId) {

        feedsAndGroupsRepository.setFeedGroupChecked(feedGroupId);
    }

    public void unsetFeedGroupChecked(long feedGroupId) {

        feedsAndGroupsRepository.unsetFeedGroupChecked(feedGroupId);

    }

    public void setFeedsToBeShownForGroup(Group fg) {

        feedsAndGroupsRepository.setFeedsToBeShownForGroup(fg);
    }

    public void unsetFeedsToBeShownForGroup(Group fg) {

        feedsAndGroupsRepository.unsetFeedsToBeShownForGroup(fg);
    }


}
