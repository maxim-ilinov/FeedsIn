package com.maxim_ilinov_gmail.feedsin;


import android.content.Context;


import com.maxim_ilinov_gmail.feedsin.model.data.db.RssDao;
import com.maxim_ilinov_gmail.feedsin.model.data.db.RssRoomDatabase;
import com.maxim_ilinov_gmail.feedsin.model.RssItem;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;




public class DaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private RssRoomDatabase db;

    private RssDao rssItemDao;




    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, RssRoomDatabase.class)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build();
        rssItemDao = db.getRssDao();

    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void rssDaoInsertAndGetItem() throws Exception {
        RssItem rssItem = new RssItem(
                new Date(), "title",
                "http://link",
                "desc",
                "http://imageurl",
                    "quid",
                    1);

        rssItemDao.insertRssItem(rssItem);
        List<RssItem> rssItemList = LiveDataTestUtil.getValue(rssItemDao.selectAllItems());
        Assert.assertEquals(rssItemList.get(0).getDescription(), rssItem.getDescription());
    }

//
//    @Test
//    public void countryDaoGetAllCountries() throws Exception {
//        Country country = new Country("AAA","Aaaaa", "http://aaaaa");
//        rssItemDao.insert(country);
//        Country country2 = new Country("BBB","Baaaa", "http://bbbbb");
//        rssItemDao.insert(country2);
//        List<Country> allCountries = LiveDataTestUtil.getValue(rssItemDao.getAllCountries());
//        Assert.assertEquals(allCountries.get(0).name, country.name);
//        Assert.assertEquals(allCountries.get(1).name, country2.name);
//    }
//
//    @Test
//    public void countryDaoDeleteAll() throws Exception {
//        Country country = new Country("AAA","Aaaaa", "http://aaaaa");
//        rssItemDao.insert(country);
//        Country country2 = new Country("BBB","Baaaa", "http://bbbbb");
//        rssItemDao.insert(country2);
//        rssItemDao.deleteAll();
//        List<Country> allWords = LiveDataTestUtil.getValue(rssItemDao.getAllCountries());
//        Assert.assertTrue(allWords.isEmpty());
//    }
//
//    @Test
//    public void languageDaoInsertAndGet() throws Exception
//    {
//        Language language = new Language("LA","lang1");
//
//
//
//
//    }




}