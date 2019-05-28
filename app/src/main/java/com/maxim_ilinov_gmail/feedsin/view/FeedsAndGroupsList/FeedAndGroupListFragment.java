package com.maxim_ilinov_gmail.feedsin.view.FeedsAndGroupsList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maxim_ilinov_gmail.feedsin.R;
import com.maxim_ilinov_gmail.feedsin.viewmodel.FeedsAndGroupsListViewModel;

import static androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;

public class FeedAndGroupListFragment extends Fragment {

    private FeedsAndGroupsListViewModel viewModel;
    private DrawerLayout mDrawer;

    private RecyclerView recyclerView;

    private FeedAndGroupListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feeds_and_groups_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);





        viewModel = ViewModelProviders.of(this).get(FeedsAndGroupsListViewModel.class);


        adapter = new FeedAndGroupListAdapter(getActivity(), viewModel);

        //adapter = new FeedAndGroupListAdapter(getContext(),)
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        recyclerView.setAdapter(adapter);


        viewModel.getRvItemsForFeedAndGroupsList().observe(this, rvItems ->
                {

                    adapter.submitList(rvItems);

                    //  rssItemsListSwipeRefreshLayout.setRefreshing(false);

                }
        );



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_groupsfeeds_items);
        setupToolbar();

    }

    private void setupToolbar() {
        mDrawer = (DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
        mDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        String title="FeedAndGroupListFragment";
        String subtitle="subtitle in FeedAndGroupListFragment";


        actionBar.setTitle(title);
        actionBar.setSubtitle(subtitle);
    }
}
