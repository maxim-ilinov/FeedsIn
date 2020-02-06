package com.maxim_ilinov_gmail.feedsin.view;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.maxim_ilinov_gmail.feedsin.R;

import com.maxim_ilinov_gmail.feedsin.databinding.FragmentFeedPropsBinding;
import com.maxim_ilinov_gmail.feedsin.databinding.FragmentGroupPropsBinding;
import com.maxim_ilinov_gmail.feedsin.viewmodel.FeedPropsViewModel;
import com.maxim_ilinov_gmail.feedsin.viewmodel.GroupPropsViewModel;

import static androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupPropsFragment extends Fragment {

    private static final String TAG = "GroupPropsFragment";

    private DrawerLayout mDrawer;

    private GroupPropsViewModel viewModel;

    public GroupPropsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        viewModel = new ViewModelProvider(getActivity()).get(GroupPropsViewModel.class);


       FragmentGroupPropsBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_group_props, container, false);

        binding.setViewmodel(viewModel);

        binding.setLifecycleOwner(getViewLifecycleOwner());

        binding.setHandler(new HandlerViewFocusChange());


        return binding.getRoot();
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

        String title="Group properties";
        String subtitle="create new or edit existing";

        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        actionBar.setTitle(title);
        actionBar.setSubtitle(subtitle);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {


        inflater.inflate(R.menu.group_props_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);

        // menu.clear();


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Log.d(TAG, item.toString());

        NavController navController = Navigation.findNavController((Activity)getActivity(), R.id.nav_host_fragment);

        switch (item.getItemId()) {


            case R.id.save_group:


                Toast.makeText(getContext(),"Saving values...",Toast.LENGTH_SHORT).show();

               // viewModel.updateFeedWithCurrentValues();

                navController.navigate(R.id.action_feedgroupPropsFragment_pop);

                return true;



            default:
                return super.onOptionsItemSelected(item);


        }


    }
}
