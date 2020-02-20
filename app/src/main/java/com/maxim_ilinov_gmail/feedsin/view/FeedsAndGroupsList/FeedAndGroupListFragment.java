package com.maxim_ilinov_gmail.feedsin.view.FeedsAndGroupsList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maxim_ilinov_gmail.feedsin.R;
import com.maxim_ilinov_gmail.feedsin.viewmodel.FeedAndGroupListViewModel;
import com.maxim_ilinov_gmail.feedsin.viewmodel.FeedPropsViewModel;
import com.maxim_ilinov_gmail.feedsin.viewmodel.GroupPropsViewModel;

import static android.view.ViewGroup.getChildMeasureSpec;
import static androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;

public class FeedAndGroupListFragment extends Fragment {
    private static final String TAG = "FeedAndGroupListFrag";
    private FeedAndGroupListViewModel viewModel;
    private DrawerLayout mDrawer;
    private RecyclerView recyclerView;
    private FeedAndGroupListAdapter adapter;
    private FeedPropsViewModel feedPropsViewModel;
    private GroupPropsViewModel groupPropsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feeds_and_groups_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_groupsfeeds_items);
        setupToolbar();
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getActivity().invalidateOptionsMenu();
        viewModel = new ViewModelProvider(getActivity()).get(FeedAndGroupListViewModel.class);
        feedPropsViewModel = new ViewModelProvider(getActivity()).get(FeedPropsViewModel.class);
        groupPropsViewModel = new ViewModelProvider(getActivity()).get(GroupPropsViewModel.class);
        adapter = new FeedAndGroupListAdapter(getActivity(), viewModel, feedPropsViewModel, groupPropsViewModel);
        //adapter = new FeedAndGroupListAdapter(getContext(),)
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        viewModel.getRvItemsForFeedAndGroupsList().observe(getViewLifecycleOwner(), rvItems ->
                {
                    adapter.submitList(rvItems);
                    //  rssItemsListSwipeRefreshLayout.setRefreshing(false);
                }
        );
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.feed_and_group_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        // menu.clear();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //top menu
        Log.d(TAG, item.toString());
        NavController navController = Navigation.findNavController((Activity) getActivity(), R.id.nav_host_fragment);
        switch (item.getItemId()) {
            case R.id.search_for_content:
                navController.navigate(R.id.action_organizeFeedsFragment_to_seacrhContentFragment);
                return true;
            case R.id.create_feed:


                navController.navigate(R.id.action_organizeFeedsFragment_to_feedPropsFragment);

                return true;
            case R.id.create_group:
                navController.navigate(R.id.action_organizeFeedsFragment_to_feedgroupPropsFragment);
                return true;
            case R.id.import_opml:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupToolbar() {
        mDrawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        mDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        //todo replace hardcoded
        String title = "Manage content";
        String subtitle = "add, remove, edit";
        actionBar.setTitle(title);
        actionBar.setSubtitle(subtitle);
    }
}
