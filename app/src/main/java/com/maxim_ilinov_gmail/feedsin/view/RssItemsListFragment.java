package com.maxim_ilinov_gmail.feedsin.view;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxim_ilinov_gmail.feedsin.R;
import com.maxim_ilinov_gmail.feedsin.viewmodel.RssItemsListViewModel;
import com.maxim_ilinov_gmail.feedsin.model.RssFeed;
import com.maxim_ilinov_gmail.feedsin.model.RssFeedGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class RssItemsListFragment extends Fragment {
    private static final String TAG = "RssItemsListFragment";
    private RecyclerView recyclerView;
    private RssItemsListAdapter adapter;

    private RssItemsPagedListAdapter adapterPl;

    private SwipeRefreshLayout rssItemsListSwipeRefreshLayout;

    private  RssItemsListViewModel viewModel;




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(RssItemsListViewModel.class);

        rssItemsListSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        updateData();
                    }
                }
        );


        viewModel.getRssFeedGroups().observe(this, new Observer<List<RssFeedGroup>>() {
            @Override
            public void onChanged(List<RssFeedGroup> rssFeedGroups) {

                Log.d(TAG, "rssFeedGroup size: " + rssFeedGroups.size());

                for(RssFeedGroup rfg : rssFeedGroups)
                {
                    Log.d(TAG, "rssFeedGroup name: " + rfg.getName());
                }


            }
        });


        viewModel.getRssFeeds().observe(this, new Observer<List<RssFeed>>() {
            @Override
            public void onChanged(List<RssFeed> rssFeeds) {


                Log.d(TAG, "rssFeeds size: " + rssFeeds.size());

                for (RssFeed rf : rssFeeds) {
                    Log.d(TAG, "rssFeed: " + rf.toString());

//                    Log.d(TAG, "rssFeed link: " + rf.getRssFeedLink());
//                    Log.d(TAG, "rssFeed selected: " + rf.isSelected());
                }
            }
        });


        viewModel.getCurrentRssItemsListPl().observe(this, adapterPl::submitList);


       /* viewModel.getCurrentRssItemsList().observe(this, new Observer<List<RssItem>>() {
            @Override
            public void onChanged( @Nullable List<RssItem> rssItems) {


                adapter.setRssItems(rssItems);

                Log.d(TAG, "rssItems size: " + rssItems.size());

                for (RssItem ri : rssItems)
                {
                    Log.d(TAG,"Item title: " + ri.getTitle());
                    Log.d(TAG,"Item desc: " + ri.getDescription());


                    //rssDao.insertRssItem(ri);

                }


            }
        });*/



    }
    private void updateData() {



        viewModel.updateData();

        // TODO implement a refreshment


        rssItemsListSwipeRefreshLayout.setRefreshing(false); // Disables the refresh icon
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_rss_item_list, container, false);



        recyclerView = v.findViewById(R.id.rv_rss_items);



        rssItemsListSwipeRefreshLayout = v.findViewById(R.id.rssItemsListSwipe);


        adapter = new RssItemsListAdapter(getActivity());

        adapterPl = new RssItemsPagedListAdapter();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

      //  recyclerView.setAdapter(adapter);
        recyclerView.setAdapter(adapterPl);





        return v;

    }
}

