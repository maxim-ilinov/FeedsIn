package com.maxim_ilinov_gmail.feedsin.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.model.Group;
import com.maxim_ilinov_gmail.feedsin.model.GroupForDrawerMenu;
import com.maxim_ilinov_gmail.feedsin.model.repository.ArticleRepository;
import com.maxim_ilinov_gmail.feedsin.model.repository.FeedAndGroupRepository;


import java.util.List;


public class MainActivityViewModel extends AndroidViewModel {


    //   private MenuRepository menuRepository;

    private FeedAndGroupRepository feedAndGroupRepository;

    // private LiveData<List<GroupWithFeeds>> menuGroups;
    //  private LiveData<List<FeedEntity>> rssFeeds;
    //   private LiveData<List<FeedEntity>> selectedRssFeeds;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);


        feedAndGroupRepository = FeedAndGroupRepository.getInstance(application);

        //  menuRepository = new MenuRepository(application);


    }

    public LiveData<List<GroupForDrawerMenu>> getMenuGroups() {
        return feedAndGroupRepository.selectGroupsForDrawerMenu();
    }

    public LiveData<List<FeedEntity>> getFeeds() {
        return feedAndGroupRepository.getRssFeeds();
    }

    public void addGroup(String groupName) {
        feedAndGroupRepository.addGroup(groupName);
    }

    public void addFeed(String feedName, String feedUrl) {

        feedAndGroupRepository.addFeed(feedName, feedUrl);
    }


    public void addFeedToGroup(FeedEntity rssFeedEntity, int groupId) {
        feedAndGroupRepository.addFeedToGroup(rssFeedEntity, groupId);
    }

    public void selectFeeds(List<FeedEntity> feedEntities) {


        feedAndGroupRepository.setFeedsSelected(feedEntities);
    }

    public void setFeedGroupChecked(long feedGroupId) {

        feedAndGroupRepository.setFeedGroupChecked(feedGroupId);
    }

    public void unsetFeedGroupChecked(long feedGroupId) {

        feedAndGroupRepository.unsetFeedGroupChecked(feedGroupId);

    }

    public void setFeedsToBeShownForGroup(Group fg) {

        feedAndGroupRepository.setFeedsToBeShownForGroup(fg);
    }

    public void unsetFeedsToBeShownForGroup(Group fg) {

        feedAndGroupRepository.unsetFeedsToBeShownForGroup(fg);
    }


}
