package com.maxim_ilinov_gmail.feedsin.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.maxim_ilinov_gmail.feedsin.R;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    private DrawerLayout mDrawer;

    private Toolbar toolbar;

    private NavigationView nvDrawer;

    private Menu drawerMenu;

    private Menu drawerFeedsMenu;

    private ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        nvDrawer = findViewById(R.id.nav_view);

        drawerMenu = nvDrawer.getMenu();

       // drawerFeedsMenu = drawerMenu.addSubMenu("Feeds");



        for (int j =  1; j<= 3; j++) {

            //MenuItem groupItem = drawerMenu.add("Group"+j);
            Menu groupMenu = drawerMenu.addSubMenu("Group"+j);




            for (int i = 1; i <= 3; i++) {
                MenuItem  menuItem= groupMenu.add("Feed" + i);
                menuItem.setIcon(R.drawable.ic_description_black_24dp);

            }

        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);



        ActionBar actionbar = getSupportActionBar();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
         NavigationUI.setupActionBarWithNavController(this, navController,mDrawer);

        //NavigationUI.setupWithNavController(toolbar, navController);//setupWithNavController(this, navController);



      //  actionbar.setDisplayHomeAsUpEnabled(true);

       // actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

      //  NavigationUI.setup


    }

    /*@Override

    public boolean onOptionsItemSelected(MenuItem item) {

        // The action bar home/up action should open or close the drawer.

        switch (item.getItemId()) {

            case android.R.id.home:

           //     mDrawer.openDrawer(GravityCompat.START);

                return true;

        }



        return super.onOptionsItemSelected(item);

    }*/
    @Override
    public boolean onSupportNavigateUp() {

       // Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.rssItemsListFragment);

        //navController.navigate(R.id.action_rssItemDetailsFragment_to_rssItemsListFragment);


     // NavController nc =  Navigation.findNavController(this, R.id.nav_host_fragment);

    //  return  navigateUp();


        return  NavigationUI.navigateUp(navController, mDrawer);




    }

    @Override
    public void onBackPressed() {

        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }*/

}