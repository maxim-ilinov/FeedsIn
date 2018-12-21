package com.maxim_ilinov_gmail.feedsin.webservices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jaxb.JaxbConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RssWebserviceClient {
    private static String BASE_URL = "https://bash.im/rss/";

    private static RssWebserviceClient instance = null;



    // Keep your services here, build them in buildRetrofit method later
    private RssWebservice rssWebservice;

    public static RssWebserviceClient getInstance() {
        if (instance == null) {
            instance = new RssWebserviceClient();
        }

        return instance;
    }

    // Build retrofit once when creating a single instance
    private RssWebserviceClient() {
        // Implement a method to build your retrofit
        buildRetrofit();
    }

    private void buildRetrofit() {
        Retrofit retrofit =
        new Retrofit.Builder().baseUrl(BASE_URL)
//                .addConverterFactory(JaxbConverterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
                .build();
        // Build your services once
        this.rssWebservice = retrofit.create(RssWebservice.class);

    }

    public RssWebservice getRssWebservice() {
        return this.rssWebservice;
    }


}
