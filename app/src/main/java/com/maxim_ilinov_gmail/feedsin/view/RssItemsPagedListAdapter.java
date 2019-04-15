package com.maxim_ilinov_gmail.feedsin.view;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.maxim_ilinov_gmail.feedsin.R;
import com.maxim_ilinov_gmail.feedsin.model.RssItem;
import com.maxim_ilinov_gmail.feedsin.viewmodel.RssItemDetailsViewModel;

public class RssItemsPagedListAdapter extends PagedListAdapter<RssItem, RssItemViewHolder> {

    private static final String TAG = "RssItemsPagedListAdpt";

    private RssItemDetailsViewModel rssItemDetailsViewModel;

    private static DiffUtil.ItemCallback<RssItem> DIFF_CALLBACK =

            new DiffUtil.ItemCallback<RssItem>() {

                private  final String TAG = "RssItemsPagedListAdpt.";
                // Item details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(RssItem oldRssItem, RssItem newRssItem) {

                   /* Log.d(TAG, "areItemsTheSame");
                    Log.d(TAG, "oldRssItem desc= " + oldRssItem.getDescription());
                    Log.d(TAG, "newRssItem desc= " + newRssItem.getDescription());*/
                    return oldRssItem.getId() == newRssItem.getId();

                }

                @Override
                public boolean areContentsTheSame(RssItem oldRssItem,
                                                  RssItem newRssItem) {
                   /* Log.d(TAG, "areContentsTheSame");
                    Log.d(TAG, "oldRssItem desc= " + oldRssItem.getDescription());
                    Log.d(TAG, "newRssItem desc= " + newRssItem.getDescription());*/
                    return oldRssItem.equals(newRssItem);

                }
            };

    private Context context;

    protected RssItemsPagedListAdapter(Context context, RssItemDetailsViewModel viewModel) {

        super(DIFF_CALLBACK);

       // Log.d(TAG, "RssItemsPagedListAdapter init");

        rssItemDetailsViewModel =viewModel;
         this.context = context;
    }

    @NonNull
    @Override
    public RssItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      //  Log.d(TAG, "in onCreateViewHolder");

       // View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.recyclerview_item, parent, false);

        return new RssItemViewHolder(binding,context);

    }

    @Override
    public void onBindViewHolder(@NonNull RssItemViewHolder holder,
                                 int position) {

       // Log.d(TAG, "in onBindViewHolder");



        RssItem rssItem = getItem(position);
        if (rssItem != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rssItemDetailsViewModel.select(rssItem);

                    //Toast.makeText(v.getContext(), "Selected item: " + rssItem.getTitle(), Toast.LENGTH_SHORT).show();

                     NavController navController = Navigation.findNavController((Activity) v.getContext(), R.id.nav_host_fragment);
                     navController.navigate(R.id.action_to_details);


                }
            });
            holder.bind(rssItem);
        } else {
            // Null defines a placeholder item - PagedListAdapter automatically
            // invalidates this row when the actual object is loaded from the
            // database.
            holder.clear();
        }
    }

}



