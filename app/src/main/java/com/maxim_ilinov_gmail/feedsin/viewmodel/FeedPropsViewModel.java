package com.maxim_ilinov_gmail.feedsin.viewmodel;


import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.maxim_ilinov_gmail.feedsin.model.FeedEntity;
import com.maxim_ilinov_gmail.feedsin.model.GroupEntity;
import com.maxim_ilinov_gmail.feedsin.model.repository.FeedAndGroupRepository;

import java.util.ArrayList;
import java.util.List;


public class FeedPropsViewModel extends AndroidViewModel {


    private static final String TAG = "FeedPropsViewModel";

    private final MutableLiveData<FeedEntity> currentFeed = new MutableLiveData<>();
    private final LiveData<GroupEntity> currentGroup;
    private FeedAndGroupRepository feedAndGroupRepository;
    private LiveData<Integer> currentFeedId = new MutableLiveData();
    private FeedEntity changedFeed;
    private GroupEntity curentFeedGroupEntity;
    private LiveData<List<GroupEntity>> listGroups;
    private LiveData<List<String>> listGroupNames;
    private int currentPosition;

    public FeedPropsViewModel(@NonNull Application application) {
        super(application);


        feedAndGroupRepository = FeedAndGroupRepository.getInstance(application);

        listGroupNames = Transformations.map(feedAndGroupRepository.getFeedGroupsLiveData(),
                new Function<List<GroupEntity>, List<String>>() {
                    @Override
                    public List<String> apply(List<GroupEntity> input) {

                        List<String> output = new ArrayList<>();

                        for (GroupEntity g : input) {
                            output.add(g.getName());
                        }

                        return output;
                    }
                });

        listGroups = Transformations.map(feedAndGroupRepository.getFeedGroupsLiveData(), new Function<List<GroupEntity>, List<GroupEntity>>() {
            @Override
            public List<GroupEntity> apply(List<GroupEntity> input) {


                return input;
            }
        });



        currentGroup = Transformations.switchMap(currentFeed, new Function<FeedEntity, LiveData<GroupEntity>>() {


            @Override
            public LiveData<GroupEntity> apply(FeedEntity input) {
                return feedAndGroupRepository.getGroupById((int)input.getFeedGroupId());
            }
        });


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

    private static boolean setCurrentSelection(Spinner spinner, GroupEntity selectedGroup) {

        Log.d(TAG, "selectedGroup = " + selectedGroup.getName());

        for (int index = 0; index < spinner.getAdapter().getCount(); index++) {

            Log.d(TAG, "spinner item = " + ((GroupEntity)spinner.getItemAtPosition(index)).getName());


            if (((GroupEntity)spinner.getItemAtPosition(index)).getName().equals(selectedGroup.getName())) {

                Log.d(TAG, "matched spinner item = " + ((GroupEntity)spinner.getItemAtPosition(index)).getName());

                spinner.setSelection(index);

                return true;
            }
        }

        return false;
    }

    @BindingAdapter(value = {"listOfGroups", "selectedGroup", "selectedGroupAttrChanged"}, requireAll = false)
    public static void setListOfGroups(Spinner spinner, LiveData<List<GroupEntity>> listGroups, LiveData<GroupEntity> selectedGroup, InverseBindingListener listener) {

        List<GroupEntity> listGroupValues = listGroups.getValue();

        if (listGroupValues == null) return;

        ArrayAdapter<GroupEntity> adapter = new GroupNameAdapter(spinner.getContext(), android.R.layout.simple_spinner_item, listGroupValues);

        spinner.setAdapter(adapter);

        setCurrentSelection(spinner, selectedGroup.getValue());

        setSpinnerListener(spinner, listener);
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


    public LiveData<GroupEntity> getCurrentGroup() {
        return currentGroup;
    }



    public LiveData<FeedEntity> getCurrentFeed() {

        return currentFeed;
    }

    public void setCurrentFeed(FeedEntity item) {

        Log.d(TAG, "currentFeed value set to: " + item.getCustomTitle());

        currentFeed.postValue(item);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public LiveData<List<GroupEntity>> getListGroups() {
        return listGroups;
    }

    public LiveData<List<String>> getListGroupNames() {
        return listGroupNames;
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