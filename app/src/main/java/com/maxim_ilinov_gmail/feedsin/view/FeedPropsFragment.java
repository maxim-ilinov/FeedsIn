package com.maxim_ilinov_gmail.feedsin.view;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

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

    @BindingAdapter(value = {"listOfGroups", "selectedGroup", "selectedGroupAttrChanged"}, requireAll = false)
    public static void setListOfGroups(Spinner spinner, LiveData<List<GroupEntity>> listGroups,
                                       Long feedGroupId, InverseBindingListener listener) {

        List<GroupEntity> listGroupValues = listGroups.getValue();

        Log.d(TAG, "inside @BindingAdapter(value = {\"listOfGroups\", \"selectedGroup\", \"selectedGroupAttrChanged\"}");

        if (listGroupValues == null)
        {
            Log.d(TAG, "listGroupValues is null");
            return;
        }


        ArrayAdapter<GroupEntity> adapter = new FeedPropsFragment.GroupNameAdapter(spinner.getContext(),
                android.R.layout.simple_spinner_dropdown_item, listGroupValues);

        spinner.setAdapter(adapter);



        if (feedGroupId ==null) {
            Log.d(TAG, "selectedGroupValue is null");

            return;
        }

        setCurrentSelection(spinner, feedGroupId);

        setSpinnerListener(spinner, listener);



    }



    private static boolean setCurrentSelection(Spinner spinner, @NonNull Long selectedGroup) {

        Log.d(TAG, "selectedGroup id = " + selectedGroup);

        for (int index = 0; index < spinner.getAdapter().getCount(); index++) {

            Log.d(TAG, "spinner item = " + ((GroupEntity) spinner.getItemAtPosition(index)).getName());


            if (((GroupEntity) spinner.getItemAtPosition(index)).getId() == selectedGroup) {

                Log.d(TAG, "matched spinner item = " + ((GroupEntity) spinner.getItemAtPosition(index)).getName());

                spinner.setSelection(index);

                return true;
            }
        }

        return false;
    }

    private static void setSpinnerListener(Spinner spinner, InverseBindingListener listener) {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                listener.onChange();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                listener.onChange();

            }
        });

    }

    @InverseBindingAdapter(attribute = "selectedGroup", event = "selectedGroupAttrChanged")
    public static Long getSelectedGroup(Spinner spinner) {

        Log.d(TAG, "(Long)spinner.getSelectedItem(): " + ((GroupEntity)spinner.getSelectedItem()).getName());


        return (long)((GroupEntity) spinner.getSelectedItem()).getId();

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


        bt = binding.buttonChangeTitle;

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.getCurrentFeedMutable().setValue(new FeedEntity("custom title", "custom link"));
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
