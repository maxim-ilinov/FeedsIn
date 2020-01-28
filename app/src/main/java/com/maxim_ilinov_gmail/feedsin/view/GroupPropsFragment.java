package com.maxim_ilinov_gmail.feedsin.view;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.maxim_ilinov_gmail.feedsin.R;

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

        viewModel = ViewModelProviders.of(getActivity()).get(GroupPropsViewModel.class);

        return inflater.inflate(R.layout.fragment_group_props, container, false);
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

        String title="GroupEntity props";
        String subtitle="create or edit";


        actionBar.setTitle(title);
        actionBar.setSubtitle(subtitle);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Log.d(TAG, item.toString());

        NavController navController = Navigation.findNavController((Activity)getActivity(), R.id.nav_host_fragment);

        switch (item.getItemId()) {


            case R.id.save_feed:


                Toast.makeText(getContext(),"Saving values...",Toast.LENGTH_SHORT).show();

                viewModel.updateFeedWithCurrentValues();

                navController.navigate(R.id.action_feedPropsFragment_to_organizeFeedsFragment);

                return true;



            default:
                return super.onOptionsItemSelected(item);


        }


    }
}
