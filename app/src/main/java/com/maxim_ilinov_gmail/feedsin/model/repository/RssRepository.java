package com.maxim_ilinov_gmail.feedsin.model.repository;


import android.content.Context;
import android.util.Log;

import com.maxim_ilinov_gmail.feedsin.model.data.db.RssRoomDatabase;
import com.maxim_ilinov_gmail.feedsin.model.data.db.RssDao;
import com.maxim_ilinov_gmail.feedsin.model.RssFeed;
import com.maxim_ilinov_gmail.feedsin.model.RssFeedGroup;
import com.maxim_ilinov_gmail.feedsin.model.RssItem;
import com.maxim_ilinov_gmail.feedsin.model.data.webservices.RssWebservice;
import com.maxim_ilinov_gmail.feedsin.model.data.webservices.RssWebserviceClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class RssRepository {

    private static final String TAG = "RssRepository";




    private final RssWebservice rssWebservice;
    private final RssDao rssDao;


    private final Executor executor;

    private final RssRoomDatabase db;

    private int defaultRssFeedGroupId;


    public RssRepository(Context context) {

       db = RssRoomDatabase.getInstance(context);


        this.rssDao = db.getRssDao();

       /* this.languageDao = db.getLanguageDao();
        this.countryLanguageJoinDao = db.getCountryLanguageJoinDao();
        this.currencyDao = db.getCurrencyDao();
        this.countryCurrencyJoinDao = db.getCountryCurrencyJoinDao();
        this.timezoneDao = db.getTimezoneDao();
        this.countryTimezoneJoinDao = db.getCountryTimezoneJoinDao();
*/



       this.executor =  Executors.newSingleThreadExecutor();

       rssWebservice =   RssWebserviceClient.getInstance().getRssWebservice();

//TODO: remove debug and make tests
        addNewFeed ("http://www.ixbt.com/export/articles.rss", true);
        addNewFeed ("https://habr.com/rss/hub/apps_design/all/?hl=ru&fl=ru", true);
      //  addNewFeed ("https://habr.com/rss/best/?hl=ru&fl=ru", true); //top

        addNewFeed ("https://habr.com/rss/all/all/?hl=ru&fl=ru", true);
      //  addNewFeed ("https://bash.im/rss/", true);


       // addNewFeed ("https://www.nytimes.com/svc/collections/v1/publish/https://www.nytimes.com/section/world/rss.xml", true);
      // addNewFeed ("https://news.yandex.ru/politics.rss", true);

        //atom
      // addNewFeed ("https://www.reddit.com/r/worldnews/.rss", true);

        //addNewFeed ("https://plugins.geany.org/install.html", true);


            loadRssData();

    }

    public void loadRssData() {

        executor.execute(() -> {

            //for test purposes
           // rssDao.deleteAllRssItems();
           // rssDao.deleteAllFeeds();

           List<RssFeed> rssFeeds = rssDao.selectSelectedRssFeedsSync();

           for (RssFeed rf : rssFeeds)
           {
               loadRssItemsFromWeb(rf.getId(), rf.getRssFeedLink());
           }

        });

    }

    public void addNewFeed(String rssUrl,  boolean selected)
    {
        executor.execute(() -> {

            if (rssDao.feedWithUrlCount(rssUrl) == 0) {

                rssDao.insertRssFeed(new RssFeed(rssUrl, selected));

            }
     });

    }


    public void addFeedToGroup(int feedId, int groupId)
    {
        int useGroupId;

        if (rssDao.countRssFeedGroupsWithId(groupId)>0 )
        {
            useGroupId = groupId;
        }
        else
        {
            useGroupId = 0;
        }
    }
    public LiveData<List<RssFeed>> getRssFeeds(){

        return rssDao.selectAllRssFeeds();
    }

    public LiveData<List<RssFeed>> getSelectedRssFeeds(){

        return rssDao.selectSelectedRssFeeds();
    }

    public LiveData<PagedList<RssItem>> getItemsForSelectedFeedsPl()
    {
        return   new LivePagedListBuilder<>(
                rssDao.selectItemsForSelectedFeedsPl(), /* page size */ 20).build();

    }

    public LiveData<List<RssItem>> getItemsForSelectedFeeds() {



        return   rssDao.selectItemsForSelectedFeeds();


       /* if (rssFeeds != null)
        {*/
            //check availability of connection

            //loadRssItemsFromWeb(rssFeeds.get(0));


           /* int[] rssFeedIds = new int[rssFeeds.size()];

            for (int i = 0; i<rssFeeds.size(); i++){

                rssFeedIds[i]= rssFeeds.get(i).getId();

            }

            return rssDao.selectItemsByFeedIds(rssFeedIds);*/
       /* }
        return null;
*/

    }

    public LiveData<List<RssFeedGroup>> getRssFeedGroups(){
      return   rssDao.selectAllRssFeedGroups();
    }

    private Date parseStringToDate(String strDate) {

        String determinedDateFormat = determineDateFormat(strDate);

        if (determinedDateFormat!=null) {

            Log.d(TAG, "determinedDateFormat: " + determinedDateFormat);

            SimpleDateFormat sdf = new SimpleDateFormat(determinedDateFormat, Locale.ENGLISH);

            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));  // IMP !!!

            try {



                return sdf.parse(strDate);


            } catch (Exception e) {
                Log.d(TAG, "Parsing error: " + e.getMessage());
                return null;
            }
        }
        return null;
    }
    private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
        put("^\\d{8}$", "yyyyMMdd");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy"); //Aug 24, 1990
        put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
        put("^\\d{12}$", "yyyyMMddHHmm");
        put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
        put("^\\d{14}$", "yyyyMMddHHmmss");
        put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
        put("^[a-z]{3,},\\s\\d{1,2}\\s[a-z]{3,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}\\s[a-z]{3,}$", "EEE, dd MMM yyyy HH:mm:ss Z");
        put("^\\d{1,2}\\s[a-z]{3,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}\\s\\+\\d{4,}$", "dd MMM yyyy HH:mm:ss Z");
        put("^[a-z]{3,},\\s\\d{1,2}\\s[a-z]{3,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}\\s\\+\\d{4,}$", "EEE, dd MMM yyyy HH:mm:ss Z");

    }};



    public String determineDateFormat(String dateString) {

        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {
                return DATE_FORMAT_REGEXPS.get(regexp);
            }
        }
        return null;
    }



    private void loadRssItemsFromWeb(int feedId, String rssLink) {



        executor.execute(() -> {

            Call<RssFeed> rssFeedCall = rssWebservice.getItems(rssLink);

            rssFeedCall.enqueue(new Callback<RssFeed>() {
                @Override
                public void onResponse(Call<RssFeed> call, final Response<RssFeed> response) {

                    executor.execute(()-> {
                            if (response.isSuccessful()) {
                            //TODO check type of response

                                Log.d(TAG,"Response raw: " + response.raw());

                                Log.d(TAG,"Response message: " + response.toString());

                                RssFeed rssFeed = response.body();

                                Log.d(TAG, "Current feed: " + rssFeed.toString());
                                if (rssFeed.getRssItemList()!=null ) {


                                    Log.d(TAG, "Items in list: " + rssFeed.getRssItemList().size());

                                    for (RssItem ri : rssFeed.getRssItemList()) {

                                        String strDate = ri.getPubDate();

                                        Log.d(TAG, "Item pubdate: " + strDate);

                                        Date pubDateNorm = parseStringToDate(strDate);

                                        ri.setPubDateNorm(pubDateNorm);

                                        if (pubDateNorm!=null)
                                        {

                                        }
                                        else
                                        {

                                        }
                                        Log.d(TAG, "Item pubDateNorm: " + pubDateNorm);
                                        Log.d(TAG, "Item title: " + ri.getTitle());
                                        Log.d(TAG, "Item desc: " + ri.getDescription());

                                        ri.setRssFeedId(feedId);

                                        if (rssDao.countRssItemWithHash(ri.hashCode()) == 0) {

                                            ri.setHash(ri.hashCode());
                                            rssDao.insertRssItem(ri);
                                        }


                                    }
                                }


                            } else {
                                Log.d(TAG,"response code " + response.code());
                                //todo use this to inform user
                            }




                    });


                }

                @Override
                public void onFailure(Call<RssFeed> call, Throwable t) {
                    Log.d(TAG,"failure " + t);
                }

            });


        });


    }



}
