package com.maxim_ilinov_gmail.feedsin.view;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.maxim_ilinov_gmail.feedsin.R;
import com.maxim_ilinov_gmail.feedsin.databinding.FragmentFeedPropsBinding;
import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.model.GroupEntity;
import com.maxim_ilinov_gmail.feedsin.viewmodel.FeedPropsViewModel;

import java.util.List;

import static androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedPropsFragment extends Fragment {

    private static final String TAG = "FeedPropsFragment";

    private DrawerLayout mDrawer;

    private FeedPropsViewModel viewModel;

    private TextView feedCustomTitle;

    private Button bt;

    public FeedPropsFragment() {
        // Required empty public constructor
    }

    @BindingAdapter( {"listOfGroups"})
    public static void setListOfGroups(Spinner spinner, LiveData<List<GroupEntity>> listGroups) {

        Log.d(TAG, "inside setListOfGroups BindingAdapter");


        List<GroupEntity> listGroupValues = listGroups.getValue();





        if (listGroupValues == null) {
            Log.d(TAG, "listGroupValues is null");
            return;
        }




        ArrayAdapter<GroupEntity> adapter = new FeedPropsFragment.GroupNameAdapter(spinner.getContext(),
                android.R.layout.simple_spinner_dropdown_item, listGroupValues);

        spinner.setAdapter(adapter);



    }
    @BindingAdapter(value = {"selectedGroup", "selectedGroupAttrChanged"}, requireAll = false)
    public static void setSelectedGroup(Spinner spinner,
                                        MutableLiveData<Long> feedGroupId,
                                        InverseBindingListener listener) {

        Log.d(TAG, "inside setSelectedGroup bindingAdapter");


        Long groupIdLong = feedGroupId.getValue();



        if (groupIdLong == null) {
            Log.d(TAG, "groupIdLong is null");
            return;
        }



        /*if (((GroupEntity) spinner.getSelectedItem()).getId() == groupIdLong) {
            Log.d(TAG, "spinner.getSelectedItem()).getId() == selectedGroup !!! Canceling binding...");
            return ;
        } ;*/

        setCurrentSelection(spinner, groupIdLong);

        setSpinnerListener(spinner, listener);


    }



    private static boolean setCurrentSelection(Spinner spinner, @NonNull Long selectedGroup) {

        Log.d(TAG, "setCurrentSelection started");

        if ( spinner.getSelectedItem() == null)
        {
            return false;
        }


        Log.d(TAG, "selectedGroup id = " + selectedGroup);

        Log.d(TAG, "((GroupEntity) spinner.getSelectedItem()).getId() = " + ((GroupEntity) spinner.getSelectedItem()).getId());


        if (((GroupEntity) spinner.getSelectedItem()).getId() == selectedGroup) {
            Log.d(TAG, "spinner.getSelectedItem()).getId() == selectedGroup !!!");
            return false;
        } else {
            Log.d(TAG, "spinner.getSelectedItem()).getId() != selectedGroup !!!");
        }


        if (((GroupEntity) spinner.getSelectedItem()).getId() != selectedGroup) {

            for (int index = 0; index < spinner.getAdapter().getCount(); index++) {

                Log.d(TAG, "spinner item = " + ((GroupEntity) spinner.getItemAtPosition(index)).getName());


                if (((GroupEntity) spinner.getItemAtPosition(index)).getId() == selectedGroup) {

                    Log.d(TAG, "matched spinner item = " + ((GroupEntity) spinner.getItemAtPosition(index)).getName());

                    spinner.setSelection(index);

                    return true;
                }
            }
        }
        return false;

    }

    private static void setSpinnerListener(Spinner spinner, InverseBindingListener listener) {

        Log.d(TAG, "setSpinnerListener");
      /*  if (spinner.getOnItemSelectedListener() == listener) {
            return;
        }*/

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG, "in onItemSelected of setSpinnerListener, position = " + position + " , id = " + id);

                Log.d(TAG, "((GroupEntity)spinner.getSelectedItem()).getId() = " + ((GroupEntity) spinner.getSelectedItem()).getId());

                listener.onChange();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

//                listener.onChange();

            }
        });

    }

    @InverseBindingAdapter(attribute = "selectedGroup", event = "selectedGroupAttrChanged")
    public static Long getSelectedGroupId(Spinner spinner) {

        Log.d(TAG, "inside getSelectedGroupId InverseBindingAdapter");


        Log.d(TAG, "(Long)spinner.getSelectedItem().getId(): " + ((GroupEntity) spinner.getSelectedItem()).getId());


        return (long) ((GroupEntity) spinner.getSelectedItem()).getId();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Log.d(TAG, "onCreateView");


        viewModel = ViewModelProviders.of(getActivity()).get(FeedPropsViewModel.class);

        FragmentFeedPropsBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_feed_props, container, false);

        binding.setViewmodel(viewModel);

        binding.setLifecycleOwner(getViewLifecycleOwner());

        binding.setHandler(new HandlerViewFocusChange());

      /*  bt = binding.buttonChangeTitle;

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "CurrentLinkValue = " + viewModel.getCurrentFeed_RssLink().getValue());

                //viewModel.getCurrentFeedLDMutable().setValue(new FeedEntity("custom title", "custom link"));
            }
        });*/




        viewModel.getCurrentFeedLDMutable().observe(getViewLifecycleOwner(), new Observer<FeedEntity>() {
            @Override
            public void onChanged(FeedEntity feedEntity) {
                Log.d(TAG, "observeCurrentFeedMutable toString: " + feedEntity.toString());
            }
        });

        viewModel.getCurrentFeed_RssLink().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "CurrentFeed_RssLink changed to: " + s);
            }
        });


        return binding.getRoot();

        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_feed_props, container, false);

    }


 /*@BindingAdapter({"selection"})
    public static void setSelection(Spinner spinner, int selection) {
        spinner.setSelection(selection);
    }
*/
   /* @BindingAdapter(value = {"listGroupNames", "selectedGroup", "selectedGroupAttrChanged"}, requireAll = false)
    public static void setListGroupNames(Spinner spinner, LiveData<List<String>> listGroupNames, GroupEntity selectedGroup, InverseBindingListener listener) {


        List<String> groupNames = listGroupNames.getValue();

        if (groupNames == null) return;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(spinner.getContext(), android.R.layout.simple_spinner_item, groupNames);

        spinner.setAdapter(adapter);

        setCurrentSelection(spinner, selectedGroup);

    }*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolbar();
        setHasOptionsMenu(true);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated");

        /*feedCustomTitle = getActivity().findViewById(R.id.feed_title_editText);

        feedCustomTitle.setText("sdfsdfsf");*/


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {


        inflater.inflate(R.menu.feed_props_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);

        // menu.clear();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Log.d(TAG, item.toString());

        NavController navController = Navigation.findNavController((Activity)getActivity(), R.id.nav_host_fragment);

        switch (item.getItemId()) {


            case R.id.save_feed:


                Toast.makeText(getContext(),"Saving values...",Toast.LENGTH_SHORT).show();

                viewModel.updateFeedWithCurrentValues();

                navController.navigate(R.id.action_feedPropsFragment_to_organizeFeedsFragment);

                return true;



            default:
                return super.onOptionsItemSelected(item);


        }


    }

    private void setupToolbar() {
        mDrawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        mDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        String title = "Feed props";
        String subtitle = "enter new or edit";

        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        actionBar.setTitle(title);
        actionBar.setSubtitle(subtitle);
    }

    static class GroupNameAdapter extends ArrayAdapter<GroupEntity> {


        List<GroupEntity> groupList;

        GroupNameAdapter(@NonNull Context context, int resource, @NonNull List<GroupEntity> groupList) {
            super(context, resource, groupList);

            this.groupList = groupList;
        }

        @Override
        public int getCount() {
            return groupList.size();
        }

        @Nullable
        @Override
        public GroupEntity getItem(int position) {
            return groupList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return (long) position;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            TextView textView = (TextView) super.getView(position, convertView, parent);

            textView.setText(groupList.get(position).getName());

            return textView;

        }


        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            TextView textView = (TextView) super.getView(position, convertView, parent);

            textView.setText(groupList.get(position).getName());

            return textView;

        }
    }
}
