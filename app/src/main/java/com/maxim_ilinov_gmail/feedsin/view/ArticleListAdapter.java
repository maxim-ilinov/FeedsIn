package com.maxim_ilinov_gmail.feedsin.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.maxim_ilinov_gmail.feedsin.R;
import com.maxim_ilinov_gmail.feedsin.model.Article;
import com.maxim_ilinov_gmail.feedsin.viewmodel.ArticleDetailsViewModel;

public class ArticleListAdapter extends PagedListAdapter<Article, ArticleViewHolder> {

    private static final String TAG = "RssItemsPagedListAdpt";
    private static DiffUtil.ItemCallback<Article> DIFF_CALLBACK =

            new DiffUtil.ItemCallback<Article>() {

                private  final String TAG = "RssItemsPagedListAdpt";
                // Item details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(Article oldArticle, Article newArticle) {

                   /* Log.d(TAG, "areItemsTheSame");
                    Log.d(TAG, "oldArticle desc= " + oldArticle.getDescription());
                    Log.d(TAG, "newArticle desc= " + newArticle.getDescription());*/
                    return oldArticle.getId() == newArticle.getId();

                }

                @Override
                public boolean areContentsTheSame(Article oldArticle,
                                                  Article newArticle) {
                   /* Log.d(TAG, "areContentsTheSame");
                    Log.d(TAG, "oldArticle desc= " + oldArticle.getDescription());
                    Log.d(TAG, "newArticle desc= " + newArticle.getDescription());*/
                    return oldArticle.equals(newArticle);

                }
            };
    private ArticleDetailsViewModel rssItemDetailsViewModel;
    private Context context;

    protected ArticleListAdapter(Context context, ArticleDetailsViewModel viewModel) {

        super(DIFF_CALLBACK);

       // Log.d(TAG, "ArticleListAdapter init");

        rssItemDetailsViewModel =viewModel;
         this.context = context;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      //  Log.d(TAG, "in onCreateViewHolder");

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_article, parent, false);

        return new ArticleViewHolder(binding,context);

    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder,
                                 int position) {

       // Log.d(TAG, "in onBindViewHolder");



        Article rssItem = getItem(position);
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



