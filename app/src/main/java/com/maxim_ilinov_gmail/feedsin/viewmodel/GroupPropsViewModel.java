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


    private FeedAndGroupRepository feedAndGroupRepository;



    private final MutableLiveData<GroupEntity> currentGroupLDMutable = new MutableLiveData<>();

    public GroupPropsViewModel(@NonNull Application application) {
        super(application);


        feedAndGroupRepository = FeedAndGroupRepository.getInstance(application);


    }

    public void select(GroupEntity item) {

        if (currentGroupLDMutable != null) {
            currentGroupLDMutable.setValue(item);
        }

    }

    public LiveData<GroupEntity> getSelected() {

        return currentGroupLDMutable;
    }


}