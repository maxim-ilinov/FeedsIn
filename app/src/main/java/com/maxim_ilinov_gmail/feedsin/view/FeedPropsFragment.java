package com.maxim_ilinov_gmail.feedsin.view;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.maxim_ilinov_gmail.feedsin.R;
import com.maxim_ilinov_gmail.feedsin.databinding.FragmentFeedPropsBinding;
import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.model.GroupEntity;
import com.maxim_ilinov_gmail.feedsin.viewmodel.FeedPropsViewModel;

import java.util.List;

import static androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedPropsFragment extends Fragment {

    private static final String TAG = "FeedPropsFragment";

    private DrawerLayout mDrawer;

    private FeedPropsViewModel viewModel;

    private TextView feedCustomTitle;

    public FeedPropsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG,"onActivityCreated");

        /*feedCustomTitle = getActivity().findViewById(R.id.feed_title_editText);

        feedCustomTitle.setText("sdfsdfsf");*/


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG,"onCreateView");




        viewModel = ViewModelProviders.of(getActivity()).get(FeedPropsViewModel.class);

        FragmentFeedPropsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed_props, container, false);

        binding.setViewmodel(viewModel);

        binding.setLifecycleOwner(this);


        return binding.getRoot();

        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_feed_props, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolbar();
        setHasOptionsMenu(true);



    }

    private void setupToolbar() {
        mDrawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        mDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        String title = "Feed props";
        String subtitle = "enter new or edit";

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
