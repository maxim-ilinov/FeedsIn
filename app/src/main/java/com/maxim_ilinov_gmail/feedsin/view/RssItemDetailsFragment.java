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


    public static RssItemDetailsFragment newInstance() {

        Bundle args = new Bundle();

        RssItemDetailsFragment fragment = new RssItemDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public View onCreateView1(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_rss_item_details, container);



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



        FragmentRssItemDetailsBinding binding = DataBindingUtil.setContentView(getActivity(),
                R.layout.fragment_rss_item_details);

        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(getActivity());
    }
}
