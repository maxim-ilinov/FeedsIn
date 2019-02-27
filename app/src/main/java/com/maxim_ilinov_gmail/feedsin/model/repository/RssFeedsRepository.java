package com.maxim_ilinov_gmail.feedsin.model.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.maxim_ilinov_gmail.feedsin.model.RssFeed;
import com.maxim_ilinov_gmail.feedsin.model.RssFeedGroup;
import com.maxim_ilinov_gmail.feedsin.model.data.db.RssDao;
import com.maxim_ilinov_gmail.feedsin.model.data.db.RssRoomDatabase;
import com.maxim_ilinov_gmail.feedsin.model.data.webservices.RssWebservice;
import com.maxim_ilinov_gmail.feedsin.model.data.webservices.RssWebserviceClient;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RssFeedsRepository {
    private static final String TAG = "RssFeedsRepository";


    private final RssWebservice rssWebservice;
    private final RssDao rssDao;


    private final Executor executor;

    private final RssRoomDatabase db;

    private int defaultRssFeedGroupId;


    public RssFeedsRepository(Context context) {

        db = RssRoomDatabase.getInstance(context);


        this.rssDao = db.getRssDao();

       /* this.languageDao = db.getLanguageDao();
        this.countryLanguageJoinDao = db.getCountryLanguageJoinDao();
        this.currencyDao = db.getCurrencyDao();
        this.countryCurrencyJoinDao = db.getCountryCurrencyJoinDao();
        this.timezoneDao = db.getTimezoneDao();
        this.countryTimezoneJoinDao = db.getCountryTimezoneJoinDao();
*/
        this.executor = Executors.newSingleThreadExecutor();

        rssWebservice = RssWebserviceClient.getInstance().getRssWebservice();

        //TODO: remove debug and make tests
        addNewFeed("http://www.ixbt.com/export/articles.rss", true);
        addNewFeed("https://habr.com/rss/hub/apps_design/all/?hl=ru&fl=ru", true);
        //  addNewFeed ("https://habr.com/rss/best/?hl=ru&fl=ru", true); //top

        addNewFeed("https://habr.com/rss/all/all/?hl=ru&fl=ru", true);
        //  addNewFeed ("https://bash.im/rss/", true);


        // addNewFeed ("https://www.nytimes.com/svc/collections/v1/publish/https://www.nytimes.com/section/world/rss.xml", true);
        // addNewFeed ("https://news.yandex.ru/politics.rss", true);

        //atom
        // addNewFeed ("https://www.reddit.com/r/worldnews/.rss", true);

        //addNewFeed ("https://plugins.geany.org/install.html", true);
    }


    public void addNewFeed(String rssUrl, boolean selected) {
        executor.execute(() -> {

            if (rssDao.feedWithUrlCount(rssUrl) == 0) {

                rssDao.insertRssFeed(new RssFeed(rssUrl, selected));

            }
        });

    }

    public void addFeedToGroup(int feedId, int groupId) {
        int useGroupId;

        if (rssDao.countRssFeedGroupsWithId(groupId) > 0) {
            useGroupId = groupId;
        } else {
            useGroupId = 0;
        }
    }

    public LiveData<List<RssFeed>> getRssFeeds() {

        return rssDao.selectAllRssFeeds();
    }

    public LiveData<List<RssFeed>> getSelectedRssFeeds() {

        return rssDao.selectSelectedRssFeeds();
    }

    public LiveData<List<RssFeedGroup>> getRssFeedGroups() {
        return rssDao.selectAllRssFeedGroups();
    }

}
