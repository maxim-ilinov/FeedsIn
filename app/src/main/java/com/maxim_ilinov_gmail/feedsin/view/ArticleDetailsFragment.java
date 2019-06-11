package com.maxim_ilinov_gmail.feedsin.view;

import com.maxim_ilinov_gmail.feedsin.R;

import com.maxim_ilinov_gmail.feedsin.databinding.FragmentArticleDetailsBinding;
import com.maxim_ilinov_gmail.feedsin.model.Article;
import com.maxim_ilinov_gmail.feedsin.viewmodel.ArticleDetailsViewModel;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;


public class ArticleDetailsFragment extends Fragment {

   private ArticleDetailsViewModel viewModel;

    private DrawerLayout mDrawer;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupToolbar();


        //  ((AppCompatActivity) getActivity()).getDrawerToggleDelegate()

    }

    private void setupToolbar() {
        mDrawer = (DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
        mDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        /*String title="Detailed info";
        String subtitle="some subtitle";

        actionBar.setTitle(title);
        actionBar.setSubtitle(subtitle);*/

        viewModel.getSelected().observe(this, new Observer<Article>() {
            @Override
            public void onChanged(Article article) {


                actionBar.setTitle(article.getTitle());
                actionBar.setSubtitle(article.getPubDate());
            }
        });


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

         viewModel = ViewModelProviders.of(getActivity()).get(ArticleDetailsViewModel.class);

        FragmentArticleDetailsBinding binding = DataBindingUtil.inflate (inflater, R.layout.fragment_article_details, container,false);

        binding.setViewmodel(viewModel);

        binding.setLifecycleOwner(getActivity());




        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
