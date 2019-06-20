package com.maxim_ilinov_gmail.feedsin.viewmodel;


import android.app.Application;
import android.app.ListActivity;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.model.Group;
import com.maxim_ilinov_gmail.feedsin.model.repository.FeedAndGroupRepository;

import java.util.List;


public class FeedPropsViewModel extends AndroidViewModel {


    private static final String TAG = "FeedPropsViewModel";

    private FeedAndGroupRepository feedAndGroupRepository;

    public FeedPropsViewModel(Application application) {
        super(application);

        feedAndGroupRepository = FeedAndGroupRepository.getInstance(application);


    }
    public LiveData<List<Group>> getGroups()
    {
        return feedAndGroupRepository.getFeedGroups();
    };


    /*public LiveData<FeedEntity> getFeedById(int id)
    {

      return  feedAndGroupRepository.getFeedById(id);

    }
*/
}