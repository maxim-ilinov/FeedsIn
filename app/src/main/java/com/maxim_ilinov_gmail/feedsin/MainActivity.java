package com.maxim_ilinov_gmail.feedsin;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        //return RssItemsListFragment.newInstance();
        return RssItemDetailsFragment.newInstance();
    }


}
