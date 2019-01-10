package com.maxim_ilinov_gmail.feedsin.model.data.db;


import android.content.Context;


import com.maxim_ilinov_gmail.feedsin.model.RssFeed;
import com.maxim_ilinov_gmail.feedsin.model.RssFeedGroup;
import com.maxim_ilinov_gmail.feedsin.model.RssItem;

import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {
        RssFeed.class,
        RssItem.class,
        RssFeedGroup.class
        /*Country.class,
        CountryCurrencyJoin.class,
        CountryLanguageJoin.class,
        CountryTimezoneJoin.class,
        Currency.class,
        Language.class,
        Timezone.class,*/
        }, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class RssRoomDatabase extends RoomDatabase {


    private static final String DB_NAME = "RssDatabase.db";
    private static volatile RssRoomDatabase instance;

   public static synchronized RssRoomDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static RssRoomDatabase create(final Context context) {


        return Room.databaseBuilder(context,
                RssRoomDatabase.class,
                DB_NAME)
                .addCallback(new Callback() {

                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(() ->
                                {
                                 getInstance(context).getRssDao().createDefaultRssGroup(RssFeedGroup.populateData());

                                });
                    }
                })
                .build();



    }


    public abstract RssDao getRssDao();
/*
    public abstract CountryCurrencyJoinDao getCountryCurrencyJoinDao();

    public abstract CountryLanguageJoinDao getCountryLanguageJoinDao();

    public abstract CountryTimezoneJoinDao getCountryTimezoneJoinDao();

    public abstract CurrencyDao getCurrencyDao();

    public abstract LanguageDao getLanguageDao();

    public abstract TimezoneDao getTimezoneDao();*/

}
