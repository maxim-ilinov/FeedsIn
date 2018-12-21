package com.maxim_ilinov_gmail.feedsin.webservices;

import com.maxim_ilinov_gmail.feedsin.db.entities.RssFeed;



import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RssWebservice {
        @GET
        Call<RssFeed> getItems(@Url String url);
}
