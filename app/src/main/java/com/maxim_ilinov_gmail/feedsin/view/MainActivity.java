package com.maxim_ilinov_gmail.feedsin.view;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.maxim_ilinov_gmail.feedsin.R;
import com.maxim_ilinov_gmail.feedsin.model.GroupForDrawerMenu;
import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.viewmodel.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private NavController navController;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private Menu drawerMenu;
    private List<GroupForDrawerMenu> localFeedGroups;

    private List<FeedEntity> localFeedEntities;

    private ActionBarDrawerToggle drawerToggle;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        nvDrawer = findViewById(R.id.nav_view);




        nvDrawer.setNavigationItemSelectedListener(this);

        drawerMenu = nvDrawer.getMenu();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mDrawer);
/*

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                                                          @Override
                                                          public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                                                              //toolbar.setTitle("navController title");
                                                              //toolbar.setSubtitle("navController subtitle");

                                                              if(destination.getId() == R.id.rssItemDetailsFragment ||
                                                                      destination.getId() == R.id.organizeFeedsFragment ||
                                                                      destination.getId() == R.id.settingsFragment ) {

                                                                //  mDrawer.setEnabled(false);
                                                                 // mDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED);
                                                              }
                                                              else
                                                              {
                                                                //  mDrawer.setEnabled(true);
                                                                // mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                                                              }


                                                          }
                                                      }
        );*/

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        viewModel.getMenuGroups().observe(this, feedGroupForDrawerMenus -> {

            localFeedGroups = feedGroupForDrawerMenus;

            buildMenu();
        });




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

        return NavigationUI.navigateUp(navController, mDrawer);


    }

    private void buildMenu() {

        //TODO remove hardcoded text
        drawerMenu.clear();

        nvDrawer.inflateMenu(R.menu.drawer_view);

        Log.d(TAG, "Building menu...");
        Log.d(TAG, "feedGroups size: " + localFeedGroups.size());

        // drawerMenu.setGroupCheckable(R.id.drawer_menu_main,true,true);

        Menu feedsMenu = drawerMenu.addSubMenu(R.id.drawer_menu_main, 2, 0, "Feeds");


        MenuItem mi = feedsMenu.add(R.id.drawer_menu_main, 99, 0, "All");
        mi.setIcon(R.drawable.ic_view_module_black_24dp);

        mi.setActionView(R.layout.menu_counter);

        setMenuCounter(mi.getItemId(), 155);

        for (GroupForDrawerMenu rfg : localFeedGroups) {
            Log.d(TAG, "added rssFeedGroup name: " + rfg.getName());

            //  Log.d(TAG, "rssFeedGroup.FeedListSize: " + rfg.getFeedEntities().size());

            feedsMenu.add(R.id.drawer_menu_main, rfg.getId(), 0, rfg.getName()).setIcon(R.drawable.ic_description_black_24dp);

        }

        drawerMenu.setGroupCheckable(R.id.drawer_menu_main, true, true);
        feedsMenu.setGroupCheckable(R.id.drawer_menu_main, true, true);

    }

    private void setMenuCounter(@IdRes int itemId, int count) {
        TextView view = (TextView) nvDrawer.getMenu().findItem(itemId).getActionView();
        view.setText(count > 0 ? String.valueOf(count) : null);
    }

    @Override
    public void onBackPressed() {

        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        // for (nvDrawer.getMenu().getItem())

        Log.d(TAG, "Selected MenuItem Name: " + menuItem.getTitle());
        Log.d(TAG, "Selected MenuItem ID: " + menuItem.getItemId());

        // List <FeedEntity> selectedFeeds
        int itemId = menuItem.getItemId();


        switch (itemId)

        {
            case 99:
                Log.d(TAG, "Checked all");
                for (GroupForDrawerMenu fg : localFeedGroups) {


                    Log.d(TAG, "Set feed group as checked with id : " + fg.getId());

                    viewModel.setFeedGroupChecked(fg.getId());
                }
                break;

            case R.id.drawer_settings:
                navController.navigate(R.id.action_rssItemsListFragment_to_settingsFragment);
                break;

            case R.id.drawer_organize_feeds:
                navController.navigate(R.id.action_rssItemsListFragment_to_organizeFeedsFragment);

                break;

            case R.id.drawer_about:
                navController.navigate(R.id.action_rssItemsListFragment_to_aboutFragment);

                break;




            default:
                for (GroupForDrawerMenu fg : localFeedGroups) {
                    viewModel.unsetFeedGroupChecked(fg.getId());
                }

                viewModel.setFeedGroupChecked(itemId);

        }





        menuItem.setChecked(true);

        mDrawer.closeDrawers();
        return true;
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }*/


}



