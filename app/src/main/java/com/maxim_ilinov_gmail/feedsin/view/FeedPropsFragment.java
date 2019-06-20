package com.maxim_ilinov_gmail.feedsin.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxim_ilinov_gmail.feedsin.R;

import static androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedPropsFragment extends Fragment {

    private DrawerLayout mDrawer;

    public FeedPropsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed_props, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolbar();
        setHasOptionsMenu(true);
    }

    private void setupToolbar() {
        mDrawer = (DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
        mDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        String title="Feed props";
        String subtitle="enter new or edit";

        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        actionBar.setTitle(title);
        actionBar.setSubtitle(subtitle);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {



        inflater.inflate(R.menu.feed_props_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);

        // menu.clear();


    }
}
