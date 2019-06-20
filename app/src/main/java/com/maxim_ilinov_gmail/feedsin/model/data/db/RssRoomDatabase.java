package com.maxim_ilinov_gmail.feedsin.model.data.db;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.maxim_ilinov_gmail.feedsin.model.Article;
import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.model.Group;

import java.util.concurrent.Executors;


@Database(entities = {
        FeedEntity.class,
        Article.class,
        Group.class

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
                                    long[] groupIds;
                                    int[] feedIds;

                                    groupIds= getInstance(context).getGroupDao().insertFeedGroups(Group.populateData());
                                    getInstance(context).getFeedDao().insertRssFeeds(FeedEntity.populateData(groupIds));

                                    //getInstance(context).getRssDao().updateFeed();

                                });
                    }
                })
                .build();



    }



    public abstract ArticleDao getArticleDao();
    public abstract FeedDao getFeedDao();
    public abstract GroupDao getGroupDao();


}
