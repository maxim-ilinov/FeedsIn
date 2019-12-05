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

import java.util.List;


public class FeedPropsViewModel extends AndroidViewModel {


    private static final String TAG = "FeedPropsViewModel";

    private FeedAndGroupRepository feedAndGroupRepository;
    private LiveData<List<GroupEntity>> listGroups;

    private final MutableLiveData<FeedEntity> currentFeedLDMutable = new MutableLiveData<>();
    private final MutableLiveData<GroupEntity> currentGroupLDMutable = new MutableLiveData<>();

    //databinding code generation cannot set values to object properties.. :( so we use these ones

    private final MutableLiveData<String> currentFeed_CustomTitle = new MutableLiveData<>();
    private final MutableLiveData<String> currentFeed_RssLink = new MutableLiveData<>();
    private final MutableLiveData<Long> currentFeed_GroupId = new MutableLiveData<>();

    public FeedPropsViewModel(@NonNull Application application) {
        super(application);


        feedAndGroupRepository = FeedAndGroupRepository.getInstance(application);



        listGroups = Transformations.map(feedAndGroupRepository.getFeedGroupsLiveData(), new Function<List<GroupEntity>, List<GroupEntity>>() {
            @Override
            public List<GroupEntity> apply(List<GroupEntity> input) {

                Log.d(TAG, "inside List<GroupEntity> apply(List<GroupEntity> input), value of input: " + input.toString());

                return input;
            }
        });

        if (currentFeedLDMutable.getValue() != null) {
            Log.d(TAG, "currentFeedLDMutable value is not null and value.toString = " + (currentFeedLDMutable.getValue()).toString());
        } else {
            Log.d(TAG, "currentFeedLDMutable value is null!");
        }






        /*currentFeedLDMutable.observeForever(new Observer<FeedEntity>() {
            @Override
            public void onChanged(FeedEntity feedEntity) {
                Log.d(TAG, "observeforeverCurrentFeedMutable toString: " + feedEntity.toString());

                setCurrentFeed_RssLink(feedEntity.getRssFeedLink());
            }
        });*/


    }


    public LiveData<GroupEntity> getCurrentGroupLDMutable() {
        return currentGroupLDMutable;
    }


    public void setCurrentGroupLDMutable(GroupEntity value) {
        currentGroupLDMutable.postValue(value);
    }

    public MutableLiveData<FeedEntity> getCurrentFeedLDMutable() {

        return currentFeedLDMutable;
    }

    public void setCurrentFeedLDMutable(@NonNull FeedEntity item) {

        Log.d(TAG, "currentFeedLDMutable value set to: " + item.getCustomTitle());

        currentFeedLDMutable.setValue(item);

        currentFeed_CustomTitle.setValue(item.getCustomTitle());
        currentFeed_RssLink.setValue(item.getRssFeedLink());
        currentFeed_GroupId.setValue((Long) item.getFeedGroupId());


    }


    public MutableLiveData<String> getCurrentFeed_RssLink() {
        return currentFeed_RssLink;
    }

    public void setCurrentFeed_RssLink(String value)
    {
        currentFeed_RssLink.setValue(value);
    }

    public LiveData<List<GroupEntity>> getListGroups() {
        return listGroups;
    }

    public MutableLiveData<String> getCurrentFeed_CustomTitle() {
        return currentFeed_CustomTitle;
    }

    public MutableLiveData<Long> getCurrentFeed_GroupId() {
        return currentFeed_GroupId;
    }


    public void setCurrentFeed_CustomTitle(String value) {
        currentFeed_CustomTitle.setValue(value);
    }

    public void  setCurrentFeed_GroupId(Long value) {

        if (currentFeed_GroupId.getValue() != value) {
            currentFeed_GroupId.setValue(value);
        }
    }

    public void updateFeedWithCurrentValues() {

       FeedEntity fe = currentFeedLDMutable.getValue();



       fe.setCustomTitle(currentFeed_CustomTitle.getValue());

       fe.setRssFeedLink(currentFeed_RssLink.getValue());

       fe.setFeedGroupId(currentFeed_GroupId.getValue());




        Log.d(TAG, "**** going to save changes to current fedd: " + fe.toString());


        feedAndGroupRepository.updateFeedWithAllValues(fe);






    }
}