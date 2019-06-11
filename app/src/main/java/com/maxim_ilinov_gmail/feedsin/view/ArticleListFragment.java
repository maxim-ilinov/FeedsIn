package com.maxim_ilinov_gmail.feedsin.view;

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
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.maxim_ilinov_gmail.feedsin.R;
import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.model.GroupWithFeeds;
import com.maxim_ilinov_gmail.feedsin.viewmodel.ArticleDetailsViewModel;
import com.maxim_ilinov_gmail.feedsin.viewmodel.ArticleListViewModel;

import static androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED;

public class ArticleListFragment extends Fragment {

    private static final String TAG = "ArticleListFragment";

    private RecyclerView recyclerView;

    private ArticleListAdapter adapterPl;

    private SwipeRefreshLayout rssItemsListSwipeRefreshLayout;

    private ArticleListViewModel viewModel;

    private ArticleDetailsViewModel articleDetailsViewModelmodel;

    private DrawerLayout mDrawer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_article_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_rss_items);

        rssItemsListSwipeRefreshLayout = view.findViewById(R.id.rssItemsListSwipe);

        rssItemsListSwipeRefreshLayout.setOnRefreshListener(
                () -> {

                    updateData();

                }
        );

        setHasOptionsMenu(true);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupToolbar();


        viewModel = ViewModelProviders.of(getActivity()).get(ArticleListViewModel.class);

        articleDetailsViewModelmodel = ViewModelProviders.of(getActivity()).get(ArticleDetailsViewModel.class);



        adapterPl = new ArticleListAdapter(getActivity(), articleDetailsViewModelmodel);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //  recyclerView.setAdapter(adapter);
        recyclerView.setAdapter(adapterPl);

        viewModel.getCurrentRssItemsListPl().observe(this, rssItems ->
                {

                    adapterPl.submitList(rssItems);

                    //  rssItemsListSwipeRefreshLayout.setRefreshing(false);

                }
        );

        viewModel.getCheckedFeedGroups().observe(this, feedGroups ->
        {

            Log.d(TAG, "getCheckedFeedGroups().observed called back...");

            if (feedGroups != null) {

                Log.d(TAG, "FeedGroups() size is: " + feedGroups.size());

                ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

                String title = "";
                String subtitle = "";

                for (GroupWithFeeds fgwf : feedGroups) {
                    Log.d(TAG, "fgwf.getName() = " + fgwf.getName());


                    title = title + fgwf.getName() + ", "; //todo make more elegant

                    for (FeedEntity rf : fgwf.getFeedEntities()) {
                        subtitle = subtitle + rf.getCustomTitle() + ", ";//todo make more elegant
                    }

                }


                Log.d(TAG, "title  = " + title);

                Log.d(TAG, "subtitle  = " + subtitle);

                actionBar.setTitle(title);
                actionBar.setSubtitle(subtitle);

            } else {
                Log.d(TAG, "getCheckedFeedGroups().observed but feedGroup id null...");
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {


        inflater.inflate(R.menu.article_list_menu, menu);


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Log.d(TAG, item.toString());

        switch (item.getItemId()) {
            case R.id.refresh:
                updateData();

                return true;

            default:
                return super.onOptionsItemSelected(item);


        }


    }

    public void onClickN(View v) {

        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        navController.navigate(R.id.action_to_details, null);
    }

    private void updateData() {

        // rssItemsListSwipeRefreshLayout.setRefreshing(true);
        viewModel.updateData();

        // TODO implement a visual refreshment
        rssItemsListSwipeRefreshLayout.setRefreshing(false); // Disables the refresh icon
    }

    private void setupToolbar() {
        mDrawer = getActivity().findViewById(R.id.drawer_layout);

        // mDrawer.setEnabled(true);

        mDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED);


        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("FeedGroupName subtitle");
    }
}

