package com.maxim_ilinov_gmail.feedsin.view;


import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.maxim_ilinov_gmail.feedsin.R;
import com.maxim_ilinov_gmail.feedsin.model.RssItem;

import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class RssItemsListAdapter extends RecyclerView.Adapter<RssItemViewHolder> {

    private static final String TAG = "RssItemListAdapter";


    private final LayoutInflater mInflater;
    private Context context;
    private List<RssItem> rssItems = Collections.emptyList(); // Cached copy of words
    RssItemsListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public RssItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new RssItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RssItemViewHolder holder, int position) {
        RssItem current = rssItems.get(position);
        holder.bind(current);
    }

    @Override
    public int getItemCount() {
        return rssItems.size();
    }

    void setRssItems(List<RssItem> rssItems) {
        this.rssItems = rssItems;
        notifyDataSetChanged();
    }

}


