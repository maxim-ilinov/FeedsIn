package com.maxim_ilinov_gmail.feedsin.view;

import com.maxim_ilinov_gmail.feedsin.R;
import com.maxim_ilinov_gmail.feedsin.databinding.FragmentRssItemDetailsBinding;
import com.maxim_ilinov_gmail.feedsin.model.FeedGroupWithFeeds;
import com.maxim_ilinov_gmail.feedsin.model.RssFeed;
import com.maxim_ilinov_gmail.feedsin.viewmodel.RssItemDetailsViewModel;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class RssItemDetailsFragment extends Fragment {

   private RssItemDetailsViewModel viewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        String title="Detailed info";
        String subtitle="some subtitle";



        actionBar.setTitle(title);
        actionBar.setSubtitle(subtitle);




      //  ((AppCompatActivity) getActivity()).getDrawerToggleDelegate()

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* viewModel.getSelected().observe(this, {
            item ->
                // Update the UI.
        });*/







        //ab.setTitle("some details here");

        //ab.setSubtitle("some more details here");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         viewModel = ViewModelProviders.of(getActivity()).get(RssItemDetailsViewModel.class);

        FragmentRssItemDetailsBinding binding = DataBindingUtil.inflate (inflater, R.layout.fragment_rss_item_details, container,false);

        binding.setViewmodel(viewModel);

        binding.setLifecycleOwner(getActivity());




        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
