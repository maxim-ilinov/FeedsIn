package com.maxim_ilinov_gmail.feedsin;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxim_ilinov_gmail.feedsin.databinding.RssItemDetailsFragmentBinding;


public class RssItemDetailsFragment extends Fragment {


    public static RssItemDetailsFragment newInstance() {

        Bundle args = new Bundle();

        RssItemDetailsFragment fragment = new RssItemDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public View onCreateView1(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.rss_item_details_fragment, container);



        return v;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RssItemDetailsViewModel viewModel = ViewModelProviders.of(getActivity()).get( RssItemDetailsViewModel.class);



        RssItemDetailsFragmentBinding binding = DataBindingUtil.setContentView(getActivity(),
                R.layout.rss_item_details_fragment);

        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(getActivity());
    }
}
