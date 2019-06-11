package com.maxim_ilinov_gmail.feedsin.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.maxim_ilinov_gmail.feedsin.model.GroupForList;
import com.maxim_ilinov_gmail.feedsin.model.RvItem;
import com.maxim_ilinov_gmail.feedsin.model.repository.FeedsAndGroupsRepository;

public class FeedAndGroupListViewModel extends AndroidViewModel {


    private static final String TAG = "FeedAndGroupListViewModel";

   // private final LiveData<PagedList<GroupForList>> groups;

    private FeedsAndGroupsRepository feedsAndGroupsRepository;


    public FeedAndGroupListViewModel(Application application) {
        super(application);

        feedsAndGroupsRepository = new FeedsAndGroupsRepository(application);



    }

    public LiveData<PagedList<RvItem>> getRvItemsForFeedAndGroupsList()
    {
       return  feedsAndGroupsRepository.getRvItemsForFeedAndGroupList();
    }
}
