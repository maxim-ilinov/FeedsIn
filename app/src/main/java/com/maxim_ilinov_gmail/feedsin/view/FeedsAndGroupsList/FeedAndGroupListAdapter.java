package com.maxim_ilinov_gmail.feedsin.view.FeedsAndGroupsList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

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
import com.maxim_ilinov_gmail.feedsin.model.Article;
import com.maxim_ilinov_gmail.feedsin.model.FeedForList;
import com.maxim_ilinov_gmail.feedsin.model.GroupForList;
import com.maxim_ilinov_gmail.feedsin.model.RvItem;
import com.maxim_ilinov_gmail.feedsin.view.ArticleViewHolder;
import com.maxim_ilinov_gmail.feedsin.viewmodel.ArticleDetailsViewModel;

public class FeedAndGroupListAdapter extends PagedListAdapter<RvItem, RecyclerView.ViewHolder> {

    private static final String TAG = "FeedAndGroupListAdapter";

   // private ViewModel viewModel;

    private static DiffUtil.ItemCallback<RvItem> DIFF_CALLBACK =

            new DiffUtil.ItemCallback<RvItem>() {

                private  final String TAG = "FeedAndGroupListAdapter.DiffUtil.ItemCallback";
                // Item details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(RvItem oldItem, RvItem newItem) {

                   /* Log.d(TAG, "areItemsTheSame");
                    Log.d(TAG, "oldItem desc= " + oldItem.getDescription());
                    Log.d(TAG, "newItem desc= " + newItem.getDescription());*/


                    if (oldItem instanceof GroupForList)
                    {

                        return ((GroupForList)oldItem).getId() == ((GroupForList)newItem).getId();
                    }

                    if (oldItem instanceof FeedForList)
                    {

                        return ((FeedForList)oldItem).getId() == ((FeedForList)newItem).getId();
                    }

                    //todo fix this
                    return false;

                }

                @Override
                public boolean areContentsTheSame(RvItem oldItem,
                                                  RvItem newItem) {
                   /* Log.d(TAG, "areContentsTheSame");
                    Log.d(TAG, "oldItem desc= " + oldItem.getDescription());
                    Log.d(TAG, "newItem desc= " + newItem.getDescription());*/

                   if (oldItem instanceof GroupForList)
                   {
                       return ((GroupForList)oldItem).equals((GroupForList)newItem);
                   }

                    if (oldItem instanceof FeedForList)
                    {
                        return ((FeedForList)oldItem).equals((FeedForList)newItem);
                    }
                    //todo fix this

                    return false;
                   //1\throw new
                }
            };

    private Context context;

    protected FeedAndGroupListAdapter(Context context, ViewModel viewModel) {

            super(DIFF_CALLBACK);

            // Log.d(TAG, "ArticleListAdapter init");

          //  this.viewModel = viewModel;
            this.context = context;
    }

    @Override
    public int getItemViewType(int position) {

        if (getItem(position) instanceof GroupForList)
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
//                rssItemDetailsViewModel.select(rssItem);
//
//                //Toast.makeText(v.getContext(), "Selected item: " + rssItem.getTitle(), Toast.LENGTH_SHORT).show();
//
//                 NavController navController = Navigation.findNavController((Activity) v.getContext(), R.id.nav_host_fragment);
//                 navController.navigate(R.id.action_to_details);


                    });

                    Log.d(TAG, "Item's of type GroupForList name: " + ((GroupForList) rvItem).getName());

                    groupViewHolder.bind((GroupForList) rvItem);

                    break;

                case 1:
                    FeedViewHolder feedViewHolder = (FeedViewHolder) holder;

                    feedViewHolder.itemView.setOnClickListener(v -> {
//                rssItemDetailsViewModel.select(rssItem);
//
//                //Toast.makeText(v.getContext(), "Selected item: " + rssItem.getTitle(), Toast.LENGTH_SHORT).show();
//
//                 NavController navController = Navigation.findNavController((Activity) v.getContext(), R.id.nav_host_fragment);
//                 navController.navigate(R.id.action_to_details);


                    });
                    Log.d(TAG, "Item's of type FeedForList name: " + ((FeedForList) rvItem).getCustomTitle());

                    feedViewHolder.bind((FeedForList) rvItem);

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



