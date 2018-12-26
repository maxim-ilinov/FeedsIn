package com.maxim_ilinov_gmail.feedsin.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxim_ilinov_gmail.feedsin.R;
import com.maxim_ilinov_gmail.feedsin.model.RssItem;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

public class RssItemsPagedListAdapter extends PagedListAdapter<RssItem, RssItemViewHolder> {

    private Context context;

        private static DiffUtil.ItemCallback<RssItem> DIFF_CALLBACK =
                new DiffUtil.ItemCallback<RssItem>() {
                    // Item details may have changed if reloaded from the database,
                    // but ID is fixed.
                    @Override
                    public boolean areItemsTheSame(RssItem oldRssItem, RssItem newRssItem) {
                        return oldRssItem.getId() == newRssItem.getId();
                    }

                    @Override
                    public boolean areContentsTheSame(RssItem oldRssItem,
                                                      RssItem newRssItem) {
                        return oldRssItem.equals(newRssItem);
                    }
                };


    protected RssItemsPagedListAdapter() {
            super(DIFF_CALLBACK);
           // this.context = context;
        }

    @NonNull
    @Override
    public RssItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);

        return new RssItemViewHolder(itemView);

    }

    @Override
        public void onBindViewHolder(@NonNull RssItemViewHolder holder,
                                     int position) {
            RssItem rssItem = getItem(position);
            if (rssItem != null) {
                holder.bind(rssItem);
            } else {
                // Null defines a placeholder item - PagedListAdapter automatically
                // invalidates this row when the actual object is loaded from the
                // database.
                holder.clear();
            }
        }
    }



