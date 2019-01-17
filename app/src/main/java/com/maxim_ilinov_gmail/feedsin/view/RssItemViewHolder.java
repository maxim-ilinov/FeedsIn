package com.maxim_ilinov_gmail.feedsin.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.maxim_ilinov_gmail.feedsin.BR;
import com.maxim_ilinov_gmail.feedsin.R;
import com.maxim_ilinov_gmail.feedsin.model.RssItem;

public class RssItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "RssItemViewHolder";

    private Context context;


   /* private final TextView rssItemPubdateTextView;

    private final TextView rssItemTitleTextView;

    private final TextView rssItemDescTextView;
*/
    private RssItem rssItem;

    private final ViewDataBinding binding;


    public RssItemViewHolder(ViewDataBinding binding, Context context) {

        super(binding.getRoot());
        this.binding = binding;
        this.context = context;

        itemView.setOnClickListener(this);
    }

    public void bind(RssItem rssItem) {

        Log.d(TAG, "in bind of RssItemViewHolder");

        binding.setVariable(BR.item, rssItem);

       //TODO remove later
        this.rssItem = rssItem;


    }

    @Override
    public void onClick(View v) {

        Toast.makeText(v.getContext(), "Selected item: " + rssItem.getTitle(), Toast.LENGTH_SHORT).show();

       // Toast.makeText(context, "Selected item: " + rssItem.getTitle(), Toast.LENGTH_SHORT).show();


       NavController navController = Navigation.findNavController((Activity) v.getContext(), R.id.nav_host_fragment);


       // NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment);

        Bundle bundle = new Bundle();
        // bundle.putString("countryAlpha3Code",  rssItem.alpha3Code);

        navController.navigate(R.id.action_to_details);

        //Log.d(TAG,"Some text");

    }

    public void clear() {

//        this.rssItemPubdateTextView.setText("*************");

    }

}
