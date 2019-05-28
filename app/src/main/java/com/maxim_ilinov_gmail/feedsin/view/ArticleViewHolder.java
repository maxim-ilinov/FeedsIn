package com.maxim_ilinov_gmail.feedsin.view;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.maxim_ilinov_gmail.feedsin.BR;
import com.maxim_ilinov_gmail.feedsin.model.Article;


public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "ArticleViewHolder";

    private final ViewDataBinding binding;

    private Context context;


    private Article rssItem;

    public ArticleViewHolder(ViewDataBinding binding, Context context) {

        super(binding.getRoot());

        this.binding = binding;
        this.context = context;

        itemView.setOnClickListener(this);
    }

    public void bind(Article rssItem) {

        // Log.d(TAG, "in bind of ArticleViewHolder");

        binding.setVariable(BR.item, rssItem);

        //TODO remove later
        this.rssItem = rssItem;

    }

    @Override
    public void onClick(View v) {

    }

    public void clear(){}

}
