package com.maxim_ilinov_gmail.feedsin.view;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxim_ilinov_gmail.feedsin.R;
import com.maxim_ilinov_gmail.feedsin.viewmodel.OrganizeFeedsViewModel;

public class OrganizeFeedsFragment extends Fragment {

    private OrganizeFeedsViewModel mViewModel;

    public static OrganizeFeedsFragment newInstance() {
        return new OrganizeFeedsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.organize_feeds_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OrganizeFeedsViewModel.class);
        // TODO: Use the ViewModel
    }

}
