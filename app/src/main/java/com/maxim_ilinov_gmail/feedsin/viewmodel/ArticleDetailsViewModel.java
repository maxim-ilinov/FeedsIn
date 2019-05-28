package com.maxim_ilinov_gmail.feedsin.viewmodel;


import com.maxim_ilinov_gmail.feedsin.model.Article;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class ArticleDetailsViewModel extends ViewModel {

    private final MutableLiveData<Article> selected = new MutableLiveData<Article>();

    public void select(Article item) {

        if (selected!=null){
            selected.setValue(item);
        }

    }

    public LiveData<Article> getSelected() {
        return selected;
    }

}