package com.maxim_ilinov_gmail.feedsin.data.webservices;

import com.maxim_ilinov_gmail.feedsin.model.RssFeed;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RssWebservice {
        @GET
        Call<RssFeed> getItems(@Url String url);
}
