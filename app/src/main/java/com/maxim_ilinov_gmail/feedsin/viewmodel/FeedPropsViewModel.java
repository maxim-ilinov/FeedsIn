package com.maxim_ilinov_gmail.feedsin.viewmodel;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.model.GroupEntity;
import com.maxim_ilinov_gmail.feedsin.model.repository.FeedAndGroupRepository;

import java.util.ArrayList;
import java.util.List;


public class FeedPropsViewModel extends AndroidViewModel {


    private static final String TAG = "FeedPropsViewModel";

    private final MutableLiveData<FeedEntity> currentFeed = new MutableLiveData<>();

    private FeedAndGroupRepository feedAndGroupRepository;

    private LiveData<Integer> currentFeedId = new MutableLiveData();

    private FeedEntity changedFeed;

    private GroupEntity curentFeedGroupEntity;

    private LiveData<List<GroupEntity>> listGroups;

    private LiveData<List<String>> listGroupNames;

    private int currentPosition;

    public FeedPropsViewModel(@NonNull Application application) {
        super(application);


        feedAndGroupRepository = FeedAndGroupRepository.getInstance(application);

        listGroupNames = Transformations.map(feedAndGroupRepository.getFeedGroupsLiveData(),
                new Function<List<GroupEntity>, List<String>>() {
                    @Override
                    public List<String> apply(List<GroupEntity> input) {

                        List<String> output = new ArrayList<>();

                        for (GroupEntity g : input) {
                            output.add(g.getName());
                        }

                        return output;
                    }
                });

        listGroups = Transformations.map(feedAndGroupRepository.getFeedGroupsLiveData(), new Function<List<GroupEntity>, List<GroupEntity>>() {
            @Override
            public List<GroupEntity> apply(List<GroupEntity> input) {


                return input;
            }
        });


    }



    public LiveData<FeedEntity> getCurrentFeed() {

        return currentFeed;
    }

    public void setCurrentFeed(FeedEntity item) {

        Log.d(TAG, "currentFeed value set to: " + item.getCustomTitle());

        currentFeed.setValue(item);
    }


    public int getCurrentPosition() {
        return currentPosition;
    }

    public LiveData<List<GroupEntity>> getListGroups() {
        return listGroups;
    }

    public LiveData<List<String>> getListGroupNames() {
        return listGroupNames;
    }
}