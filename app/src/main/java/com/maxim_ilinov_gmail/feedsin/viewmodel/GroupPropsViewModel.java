package com.maxim_ilinov_gmail.feedsin.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.model.GroupEntity;
import com.maxim_ilinov_gmail.feedsin.model.repository.FeedAndGroupRepository;

import java.util.List;


public class GroupPropsViewModel extends AndroidViewModel {


    private static final String TAG = "GroupPropsViewModel";

    private final MutableLiveData<GroupEntity> currentGroup = new MutableLiveData<>();

    private FeedAndGroupRepository feedAndGroupRepository;

    private LiveData<Integer> currentGroupId = new MutableLiveData();

    private FeedEntity changedGroup;

    private LiveData<List<GroupEntity>> listGroups;

    private LiveData<List<String>> listGroupNames;

    private int currentPosition;

    public GroupPropsViewModel(@NonNull Application application) {
        super(application);


        feedAndGroupRepository = FeedAndGroupRepository.getInstance(application);



    }

    public void select(GroupEntity item) {

        if (currentGroup != null) {
            currentGroup.setValue(item);
        }

    }

    public LiveData<GroupEntity> getSelected() {

        return currentGroup;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }
}