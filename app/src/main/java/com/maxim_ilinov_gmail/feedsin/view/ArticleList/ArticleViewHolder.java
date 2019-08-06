package com.maxim_ilinov_gmail.feedsin.view.ArticleList;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.maxim_ilinov_gmail.feedsin.BR;
import com.maxim_ilinov_gmail.feedsin.model.Article;


public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "ArticleViewHolder";

    private final ViewDataBinding binding;


    public ArticleViewHolder(ViewDataBinding binding, Context context) {

        super(binding.getRoot());

        this.binding = binding;

        //itemView.setOnClickListener(this);
    }

    public void bind(Article article) {

        // Log.d(TAG, "in bind of ArticleViewHolder");

        binding.setVariable(BR.itemArticle, article);

        //TODO remove later

    }

    @Override
    public void onClick(View v) {

    }

    public void clear(){}

}
