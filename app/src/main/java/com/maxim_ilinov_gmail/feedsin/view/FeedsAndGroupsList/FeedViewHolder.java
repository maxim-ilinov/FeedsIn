package com.maxim_ilinov_gmail.feedsin.view.FeedsAndGroupsList;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.maxim_ilinov_gmail.feedsin.model.Article;
import com.maxim_ilinov_gmail.feedsin.model.FeedForList;


public class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "FeedViewHolder";

    private final ViewDataBinding binding;

    private Context context;


    private FeedForList feedForList;

    public FeedViewHolder(ViewDataBinding binding, Context context) {

        super(binding.getRoot());

        this.binding = binding;
        this.context = context;

        itemView.setOnClickListener(this);
    }

    public void bind(FeedForList feedForList) {

        // Log.d(TAG, "in bind of ArticleViewHolder");

        binding.setVariable(com.maxim_ilinov_gmail.feedsin.BR.item, feedForList);

        //TODO remove later
        this.feedForList = feedForList;

    }

    @Override
    public void onClick(View v) {

    }

    public void clear(){}

}
