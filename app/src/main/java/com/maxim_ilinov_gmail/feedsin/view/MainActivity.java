package com.maxim_ilinov_gmail.feedsin.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.maxim_ilinov_gmail.feedsin.R;

public class MainActivity extends AppCompatActivity {

    private NavController navController;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController);

    }

    @Override
    public boolean onSupportNavigateUp() {

       // Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.rssItemsListFragment);

        //navController.navigate(R.id.action_rssItemDetailsFragment_to_rssItemsListFragment);

/*
      NavController nc =  Navigation.findNavController(this, R.id.nav_host_fragment);

      return  nc.navigateUp();
*/

        return  navController.navigateUp();




    }

}
