package com.maxim_ilinov_gmail.feedsin.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.maxim_ilinov_gmail.feedsin.R;
import com.maxim_ilinov_gmail.feedsin.model.RssItem;

import java.text.DateFormat;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class RssItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "RssItemViewHolder";


    private static int DESC_LEN = 120;

    private final TextView rssItemPubdateTextView;

    private final TextView rssItemTitleTextView;

    private final TextView rssItemDescTextView;

    private RssItem rssItem;




    public RssItemViewHolder(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        rssItemPubdateTextView = itemView.findViewById(R.id.tv_pubdate);
        rssItemTitleTextView = itemView.findViewById(R.id.tv_title);

        rssItemDescTextView = itemView.findViewById(R.id.tv_desc);
    }

    public void bind(RssItem rssItem) {

        Log.d(TAG, "in bind of RssItemViewHolder");

        this.rssItem = rssItem;

        //SimpleDateFormat sdf= new SimpleDateFormat("dd.MM.yy hh:mm", Locale.ge);

        if (rssItem.getPubDateNorm() != null) {

            String myFormattedDate =
                    DateFormat.getDateTimeInstance().format(rssItem.getPubDateNorm());

            this.rssItemPubdateTextView.setText(myFormattedDate);
        }

        this.rssItemTitleTextView.setText(rssItem.getTitle());

        String desc = Html.fromHtml(rssItem.getDescription(), null, null).toString().trim();

        if (desc.length() > DESC_LEN) {

            desc = desc.substring(0, DESC_LEN).trim() + "...";
        }

        this.rssItemDescTextView.setText(desc);
        //   this.rssItemDescWebView.loadData(rssItem.getDescription(),"text/html", "UTF-8");

    }

    @Override
    public void onClick(View v) {

        Toast.makeText(v.getContext(), "Selected item: " + rssItem.getTitle(), Toast.LENGTH_SHORT).show();

        NavController navController = Navigation.findNavController((Activity) v.getContext(), R.id.nav_host_fragment);

        Bundle bundle = new Bundle();
        // bundle.putString("countryAlpha3Code",  rssItem.alpha3Code);

        navController.navigate(R.id.action_rssItemsListFragment_to_rssItemDetailsFragment, bundle);

        //Log.d(TAG,"Some text");

    }

    public void clear() {

        this.rssItemPubdateTextView.setText("*************");

    }

}
