package com.maxim_ilinov_gmail.feedsin.ui;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.maxim_ilinov_gmail.feedsin.R;
import com.maxim_ilinov_gmail.feedsin.db.entities.RssItem;

import java.util.Collections;
import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


public class RssItemsListAdapter extends RecyclerView.Adapter<RssItemsListAdapter.RssItemViewHolder> {

    private static final String TAG= "RssItemListAdapter";

    private Context context;

    class RssItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView rssItemPubdateTextView;
        private final TextView rssItemTitleTextView;
        private final TextView rssItemDescTextView;


        private RssItem rssItem;

        private RssItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);


            rssItemPubdateTextView = itemView.findViewById(R.id.tv_pubdate);
            rssItemTitleTextView = itemView.findViewById(R.id.tv_title);
            rssItemDescTextView =  itemView.findViewById(R.id.tv_desc);




        }

        public void bind(RssItem rssItem)
        {
            this.rssItem = rssItem;
            this.rssItemPubdateTextView.setText(rssItem.getPubDate());
            this.rssItemTitleTextView.setText(rssItem.getTitle());
            this.rssItemDescTextView.setText(rssItem.getDescription());

        }


        @Override
        public void onClick(View v) {


            Toast.makeText(v.getContext(),"Selected item: " + rssItem.getTitle(), Toast.LENGTH_SHORT).show();

            NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment);


            Bundle bundle = new Bundle();
           // bundle.putString("countryAlpha3Code",  rssItem.alpha3Code);


          //  navController.navigate(R.id.action_countriesListFragment_to_countryDetailsFragment, bundle );

            //Log.d(TAG,"Some text");

        }
    }

    private final LayoutInflater mInflater;
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

    void setRssItems(List<RssItem> rssItems) {
        this.rssItems = rssItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return rssItems.size();
    }
}


