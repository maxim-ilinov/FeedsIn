package com.maxim_ilinov_gmail.feedsin.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.maxim_ilinov_gmail.feedsin.model.Article;
import com.maxim_ilinov_gmail.feedsin.model.GroupWithFeeds;
import com.maxim_ilinov_gmail.feedsin.model.repository.ArticleRepository;
import com.maxim_ilinov_gmail.feedsin.model.repository.FeedAndGroupRepository;

import java.util.List;


public class ArticleListViewModel extends AndroidViewModel {


    private static final String TAG = "ArticleListViewModel";


    private ArticleRepository articleRepository;

    private FeedAndGroupRepository feedAndGroupRepository;

    //  private final LiveData<PagedList<Article>> currentRssItemsListPl;


    /*private MutableLiveData<Integer> selectedPosition;

    private MutableLiveData<Article> selectedRssItem;*/


    // private LiveData<List<Article>> currentArticleList = new MutableLiveData<>();

    public ArticleListViewModel(Application application) {
        super(application);

        articleRepository = ArticleRepository.getInstance(application);

        feedAndGroupRepository = FeedAndGroupRepository.getInstance(application);
        // currentArticleList = articleRepository.getItemsForSelectedFeeds();

        //currentRssItemsListPl = articleRepository.getItemsForSelectedFeedsPl();


    }



    public LiveData<PagedList<Article>> getCurrentRssItemsListPl() {

        return articleRepository.getItemsForSelectedFeedsPl();

    }


    public void updateData() {
        articleRepository.loadRssData();
    }

    public LiveData<List<GroupWithFeeds>> getCheckedFeedGroups() {

        return feedAndGroupRepository.getCheckedFeedGroups();

    }
}