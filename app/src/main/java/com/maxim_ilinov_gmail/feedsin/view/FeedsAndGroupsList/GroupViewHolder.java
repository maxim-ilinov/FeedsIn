package com.maxim_ilinov_gmail.feedsin.view.FeedsAndGroupsList;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;


import com.maxim_ilinov_gmail.feedsin.model.GroupForList;
import com.maxim_ilinov_gmail.feedsin.model.RvItem;


public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "GroupViewHolder";

    private final ViewDataBinding binding;

    private Context context;


    private GroupForList groupForList;

    public GroupViewHolder(ViewDataBinding binding, Context context) {

        super(binding.getRoot());

        this.binding = binding;
        this.context = context;

        itemView.setOnClickListener(this);
    }

    public void bind(GroupForList groupForList) {

        // Log.d(TAG, "in bind of ArticleViewHolder");

        binding.setVariable(com.maxim_ilinov_gmail.feedsin.BR.item, groupForList);


        this.groupForList = groupForList;

    }

    @Override
    public void onClick(View v) {

    }

    public void clear(){}

}
