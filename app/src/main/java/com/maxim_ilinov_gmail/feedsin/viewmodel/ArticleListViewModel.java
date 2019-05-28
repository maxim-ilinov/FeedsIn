package com.maxim_ilinov_gmail.feedsin.viewmodel;

import android.app.Application;

import com.maxim_ilinov_gmail.feedsin.model.Article;
import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.model.GroupWithFeeds;
import com.maxim_ilinov_gmail.feedsin.model.repository.ArticleRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;


public class ArticleListViewModel extends AndroidViewModel {


    private static final String TAG = "ArticleListViewModel";


    private ArticleRepository rssItemsRepository;

    //  private final LiveData<PagedList<Article>> currentRssItemsListPl;


    /*private MutableLiveData<Integer> selectedPosition;

    private MutableLiveData<Article> selectedRssItem;*/



    private LiveData<List<Article>> currentRssItemsList = new MutableLiveData<>();

    public ArticleListViewModel(Application application) {
        super(application);

        rssItemsRepository = new ArticleRepository(application);



       // currentRssItemsList = rssItemsRepository.getItemsForSelectedFeeds();

        //currentRssItemsListPl = rssItemsRepository.getItemsForSelectedFeedsPl();


    }

    public LiveData<List<Article>> getCurrentRssItemsList() {

        return currentRssItemsList;

    }


    public LiveData<PagedList<Article>> getCurrentRssItemsListPl() {

        return rssItemsRepository.getItemsForSelectedFeedsPl();

    }


    public void updateData() {
        rssItemsRepository.loadRssData();
    }


    public LiveData<List<Article>> getRssItemList(List<FeedEntity> feedEntities) {
        return currentRssItemsList;
    }


    public LiveData<List<GroupWithFeeds>> getCheckedFeedGroups() {

        return rssItemsRepository.getCheckedFeedGroups();

    }
}