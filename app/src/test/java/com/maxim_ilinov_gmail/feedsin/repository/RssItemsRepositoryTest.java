package com.maxim_ilinov_gmail.feedsin.repository;

import com.maxim_ilinov_gmail.feedsin.viewmodel.RssItemsListViewModel;
import com.maxim_ilinov_gmail.feedsin.model.RssFeed;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProviders;

public class RssItemsRepositoryTest {

    @Test
    public void getItemsByFeeds() {


        RssItemsListViewModel viewModel = ViewModelProviders.of(getActivity()).get(RssItemsListViewModel.class);

        List<RssFeed> feedList = new ArrayList<RssFeed>(1) ;

        RssFeed feed = new RssFeed("Bash.im","https://bash.im/", "Цитатник Рунета", "");

        feedList.add(feed);

        viewModel.getRssItemList(feedList);


    }

    @Test
    public void testApiResponse() {
        Rss mockedApiInterface = Mockito.mock(ApiInterface.class);
        Call<UserNotifications> mockedCall = Mockito.mock(Call.class);

        Mockito.when(mockedApiInterface.getNotifications()).thenReturn(mockedCall);

        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Callback<UserNotifications> callback = invocation.getArgumentAt(0, Callback.class);

                callback.onResponse(mockedCall, Response.success(new UserNotifications()));
                // or callback.onResponse(mockedCall, Response.error(404. ...);
                // or callback.onFailure(mockedCall, new IOException());

                return null;
            }
        }).when(mockedCall).enqueue(any(Callback.class));

        // inject mocked ApiInterface to your presenter
        // and then mock view and verify calls (and eventually use ArgumentCaptor to access call parameters)
    }
}