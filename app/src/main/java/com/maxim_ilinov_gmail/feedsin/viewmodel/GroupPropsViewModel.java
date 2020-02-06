package com.maxim_ilinov_gmail.feedsin.viewmodel;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import androidx.lifecycle.MutableLiveData;


import com.maxim_ilinov_gmail.feedsin.model.GroupEntity;
import com.maxim_ilinov_gmail.feedsin.model.repository.FeedAndGroupRepository;




public class GroupPropsViewModel extends AndroidViewModel {


    private static final String TAG = "GroupPropsViewModel";


    private FeedAndGroupRepository feedAndGroupRepository;



    private final MutableLiveData<GroupEntity> currentGroupLDMutable = new MutableLiveData<>();

    //databinding code generation cannot create code to set values of class properties.. :( so we use these ones
    private final MutableLiveData<String> currentGroup_Name = new MutableLiveData<>();

    public GroupPropsViewModel(@NonNull Application application) {
        super(application);


        feedAndGroupRepository = FeedAndGroupRepository.getInstance(application);


    }



    public void select(GroupEntity item) {

        if (currentGroupLDMutable != null) {
            currentGroupLDMutable.setValue(item);

            Log.d(TAG, "currentGroupLDMutable value set to: " + item.getName());



            setCurrentGroup_Name(currentGroupLDMutable.getValue().getName());
        }

    }

    public MutableLiveData<GroupEntity> getSelected() {

        return currentGroupLDMutable;
    }

    public MutableLiveData<String> getCurrentGroup_Name() {
        return currentGroup_Name;
    }

    public void setCurrentGroup_Name(String name){

        currentGroup_Name.setValue(name);

    }



}