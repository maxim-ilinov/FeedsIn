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

    private final MutableLiveData<FeedEntity> currentFeedMutable = new MutableLiveData<>();

    private final MutableLiveData<GroupEntity> currentGroupMutable = new MutableLiveData<>();

  // private final LiveData<FeedEntity> currentFeed;

    private final LiveData<GroupEntity> currentGroup;

    private LiveData<List<GroupEntity>> listGroups;

    private FeedAndGroupRepository feedAndGroupRepository;

    private LiveData<Integer> currentFeedId = new MutableLiveData();
    private FeedEntity changedFeed;
    private GroupEntity curentFeedGroupEntity;

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

                Log.d(TAG, "inside List<GroupEntity> apply(List<GroupEntity> input), value of input: " + input.toString());

                return input;
            }
        });

        if (currentFeedMutable.getValue() != null) {
            Log.d(TAG, "currentFeed value is not null and value.toString = " + (currentFeedMutable.getValue()).toString());
        }
        else
        {
            Log.d(TAG, "currentFeed value is null!");
        }

     /*   currentFeed = Transformations.map(currentFeedMutable, new Function<FeedEntity, FeedEntity>() {
            @Override
            public FeedEntity apply(FeedEntity input) {

                Log.d(TAG, "inside currentFeed = Transformations.map(currentFeedMutable");

                return input;
            }
        });*/


        currentGroup = Transformations.switchMap(currentFeedMutable, new Function<FeedEntity, LiveData<GroupEntity>>() {

            @Override
                    public LiveData<GroupEntity> apply(FeedEntity input) {

                        Log.d(TAG, "inside currentGroup LiveData<GroupEntity> apply(FeedEntity input), value of input: " + input.toString());

                       // ??? currentGroupMutable.postValue(currentGroup.getValue());

                        return feedAndGroupRepository.getGroupById((int) input.getFeedGroupId());


                    }
                });





    }


    public LiveData<GroupEntity> getCurrentGroupMutable() {
        return currentGroupMutable;
    }

    public LiveData<GroupEntity> getCurrentGroup ()
    {
        return currentGroup;
    }
    public void setCurrentGroupMutable(GroupEntity value) {
         currentGroupMutable.postValue(value);
    }

    public LiveData<FeedEntity> getCurrentFeedMutable() {

        return currentFeedMutable;
    }

    public void setCurrentFeedMutable(FeedEntity item) {

        Log.d(TAG, "currentFeed value set to: " + item.getCustomTitle());

        currentFeedMutable.postValue(item);
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