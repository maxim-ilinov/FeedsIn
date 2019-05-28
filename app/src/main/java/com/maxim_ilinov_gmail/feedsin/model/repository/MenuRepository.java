package com.maxim_ilinov_gmail.feedsin.model.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.maxim_ilinov_gmail.feedsin.model.GroupWithFeeds;
import com.maxim_ilinov_gmail.feedsin.model.data.db.RssDao;
import com.maxim_ilinov_gmail.feedsin.model.data.db.RssRoomDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MenuRepository {
    private static final String TAG = "MenuRepository";

    private final RssRoomDatabase db;

    private final RssDao rssDao;


    private final Executor executor;


    public MenuRepository(Context context) {

        db = RssRoomDatabase.getInstance(context);
        this.rssDao = db.getRssDao();
        this.executor = Executors.newSingleThreadExecutor();

    }

    public LiveData<List<GroupWithFeeds>> getGroupsWithAllFeeds() {


        return rssDao.selectGroupsWithAllFeeds();
    }
}
