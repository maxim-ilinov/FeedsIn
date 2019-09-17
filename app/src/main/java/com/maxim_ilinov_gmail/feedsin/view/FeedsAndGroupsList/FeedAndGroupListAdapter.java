package com.maxim_ilinov_gmail.feedsin.view.FeedsAndGroupsList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.maxim_ilinov_gmail.feedsin.R;
import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.model.GroupEntity;
import com.maxim_ilinov_gmail.feedsin.model.RvItem;
import com.maxim_ilinov_gmail.feedsin.viewmodel.FeedPropsViewModel;
import com.maxim_ilinov_gmail.feedsin.viewmodel.GroupPropsViewModel;

public class FeedAndGroupListAdapter extends PagedListAdapter<RvItem, RecyclerView.ViewHolder> {

    private static final String TAG = "FeedAndGroupListAdapter";

    private ViewModel viewModel;




    private static DiffUtil.ItemCallback<RvItem> DIFF_CALLBACK =

            new DiffUtil.ItemCallback<RvItem>() {

                private  final String TAG = "FeedAndGroupListAdapter.DiffUtil.ItemCallback";
                // Item details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(@NonNull RvItem oldItem, @NonNull RvItem newItem) {

                   /* Log.d(TAG, "areItemsTheSame");
                    Log.d(TAG, "oldItem desc= " + oldItem.getDescription());
                    Log.d(TAG, "newItem desc= " + newItem.getDescription());*/


                    if (oldItem instanceof GroupEntity)
                    {

                        return ((GroupEntity)oldItem).getId() == ((GroupEntity)newItem).getId();
                    }

                    if (oldItem instanceof FeedEntity)
                    {

                        return ((FeedEntity)oldItem).getId() == ((FeedEntity)newItem).getId();
                    }

                    //todo fix this
                    return false;

                }

                @Override
                public boolean areContentsTheSame(@NonNull RvItem oldItem,
                                                  @NonNull RvItem newItem) {
                   /* Log.d(TAG, "areContentsTheSame");
                    Log.d(TAG, "oldItem desc= " + oldItem.getDescription());
                    Log.d(TAG, "newItem desc= " + newItem.getDescription());*/

                   if (oldItem instanceof GroupEntity)
                   {
                       return ((GroupEntity)oldItem).equals((GroupEntity)newItem);
                   }

                    if (oldItem instanceof FeedEntity)
                    {
                        return ((FeedEntity)oldItem).equals((FeedEntity)newItem);
                    }
                    //todo fix this

                    return false;
                   //1\throw new
                }
            };

    private Context context;

    private FeedPropsViewModel feedPropsViewModel;

    private GroupPropsViewModel groupPropsViewmodel;


    protected FeedAndGroupListAdapter(Context context, ViewModel viewModel, FeedPropsViewModel feedPropsViewModel, GroupPropsViewModel groupPropsViewmodel) {

            super(DIFF_CALLBACK);

            // Log.d(TAG, "ArticleListAdapter init");

            this.viewModel = viewModel;

        this.feedPropsViewModel = feedPropsViewModel;

        this.groupPropsViewmodel = groupPropsViewmodel;

            this.context = context;
    }

    @Override
    public int getItemViewType(int position) {

        if (getItem(position) instanceof GroupEntity)
        {
            return 0;
        }
        else
            return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      //  Log.d(TAG, "in onCreateViewHolder");

       // View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if (viewType ==0) {
            ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_group, parent, false);

            return new GroupViewHolder(binding, context);
        } else
        {
            ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_feed, parent, false);

            return new FeedViewHolder(binding, context);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,
                                 int position) {

       // Log.d(TAG, "in onBindViewHolder");



        RvItem rvItem = getItem(position);


        if (rvItem != null) {

            switch (getItemViewType(position)) {
                case 0:
                    GroupViewHolder groupViewHolder = (GroupViewHolder) holder;

                    groupViewHolder.itemView.setOnClickListener(v -> {

                        groupPropsViewmodel.select((GroupEntity)rvItem);

                Toast.makeText(v.getContext(), "Selected item: " + ((GroupEntity)rvItem).getName(), Toast.LENGTH_SHORT).show();

                NavController navController = Navigation.findNavController((Activity) v.getContext(), R.id.nav_host_fragment);
             navController.navigate(R.id.action_organizeFeedsFragment_to_feedgroupPropsFragment);


                    });

                    Log.d(TAG, "Item's of type GroupEntity name: " + ((GroupEntity)rvItem).getName());


                    groupViewHolder.bind((GroupEntity) rvItem);

                    break;

                case 1:
                    FeedViewHolder feedViewHolder = (FeedViewHolder) holder;

                    feedViewHolder.itemView.setOnClickListener(v -> {
                    feedPropsViewModel.setCurrentFeedMutable((FeedEntity) rvItem);

                        Toast.makeText(v.getContext(), "Selected item: " + ((FeedEntity)rvItem).getCustomTitle(), Toast.LENGTH_SHORT).show();

                        NavController navController = Navigation.findNavController((Activity) v.getContext(), R.id.nav_host_fragment);
                        navController.navigate(R.id.action_organizeFeedsFragment_to_feedPropsFragment);



                    });
                    Log.d(TAG, "Item's of type FeedForList name: " + ((FeedEntity) rvItem).getCustomTitle());

                    feedViewHolder.bind((FeedEntity) rvItem);

                    break;
            }





        } else {
            // Null defines a placeholder item - PagedListAdapter automatically
            // invalidates this row when the actual object is loaded from the
            // database.

            if (holder instanceof FeedViewHolder)
            {
                ((FeedViewHolder)holder).clear();
            }
            if (holder instanceof GroupViewHolder)
            {
                ((GroupViewHolder)holder).clear();
            }


            //holder.clear();
        }
    }

}



