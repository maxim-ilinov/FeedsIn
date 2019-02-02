package com.maxim_ilinov_gmail.feedsin.viewmodel;


import com.maxim_ilinov_gmail.feedsin.model.RssItem;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class RssItemDetailsViewModel extends ViewModel {

    private final MutableLiveData<RssItem> selected = new MutableLiveData<RssItem>();

    public void select(RssItem item) {

        if (selected!=null){
            selected.setValue(item);
        }

    }

    public LiveData<RssItem> getSelected() {
        return selected;
    }

}