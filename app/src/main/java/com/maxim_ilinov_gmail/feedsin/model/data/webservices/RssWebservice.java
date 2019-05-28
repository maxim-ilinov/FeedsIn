package com.maxim_ilinov_gmail.feedsin.model.data.webservices;

import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RssWebservice {
        @GET
        Call<FeedEntity> getItems(@Url String url);
}
