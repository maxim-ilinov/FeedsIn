package com.maxim_ilinov_gmail.feedsin.view;

import com.maxim_ilinov_gmail.feedsin.R;
import com.maxim_ilinov_gmail.feedsin.databinding.FragmentRssItemDetailsBinding;
import com.maxim_ilinov_gmail.feedsin.viewmodel.RssItemDetailsViewModel;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class RssItemDetailsFragment extends Fragment {

    RssItemDetailsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* viewModel.getSelected().observe(this, {
            item ->
                // Update the UI.
        });*/

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

}
