package com.maxim_ilinov_gmail.feedsin.repository;

import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.viewmodel.ArticleListViewModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProviders;

public class ArticleRepositoryTest {

    @Test
    public void getItemsByFeeds() {


        ArticleListViewModel viewModel = ViewModelProviders.of(getActivity()).get(ArticleListViewModel.class);

        List<FeedEntity> feedEntityList = new ArrayList<FeedEntity>(1) ;

        FeedEntity feedEntity = new FeedEntity("Bash.im","https://bash.im/", "Цитатник Рунета", "");

        feedEntityList.add(feedEntity);

        viewModel.getRssItemList(feedEntityList);


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