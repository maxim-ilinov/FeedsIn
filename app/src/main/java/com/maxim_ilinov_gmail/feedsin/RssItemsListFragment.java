package com.maxim_ilinov_gmail.feedsin;


import android.os.Bundle;
import android.support.v4.app.Fragment;


public class RssItemsListFragment extends Fragment {
    public static RssItemsListFragment newInstance() {

        Bundle args = new Bundle();

        RssItemsListFragment fragment = new RssItemsListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
