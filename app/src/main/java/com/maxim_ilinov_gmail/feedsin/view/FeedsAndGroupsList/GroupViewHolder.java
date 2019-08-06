package com.maxim_ilinov_gmail.feedsin.view.FeedsAndGroupsList;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;


import com.maxim_ilinov_gmail.feedsin.BR;
import com.maxim_ilinov_gmail.feedsin.model.GroupEntity;


public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "GroupViewHolder";

    private final ViewDataBinding binding;

    private Context context;


    private GroupEntity groupEntity;

    public GroupViewHolder(ViewDataBinding binding, Context context) {

        super(binding.getRoot());

        this.binding = binding;
        this.context = context;

        itemView.setOnClickListener(this);
    }

    public void bind(GroupEntity groupEntity) {

        // Log.d(TAG, "in bind of ArticleViewHolder");

        binding.setVariable(BR.itemGroupEntity, groupEntity);


        this.groupEntity = groupEntity;

    }

    @Override
    public void onClick(View v) {

    }

    public void clear(){}

}
