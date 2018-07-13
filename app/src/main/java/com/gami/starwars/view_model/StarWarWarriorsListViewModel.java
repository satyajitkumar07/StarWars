package com.gami.starwars.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.gami.starwars.model.StarWarWarriorsModel;

import java.util.ArrayList;
import java.util.List;

public class StarWarWarriorsListViewModel extends AndroidViewModel {

    private MutableLiveData<List<StarWarWarriorsModel>> starWarWarriorsDataList;
    private List<StarWarWarriorsModel> starWarWarriorsList;

    public StarWarWarriorsListViewModel(@NonNull Application application) {
        super(application);
        starWarWarriorsDataList=new MutableLiveData<>();
        starWarWarriorsList=new ArrayList<>();
    }

    public void addStarWarWarriorsModel(StarWarWarriorsModel starWarWarriorsModel){
        starWarWarriorsList.add(starWarWarriorsModel);
        starWarWarriorsDataList.setValue(starWarWarriorsList);
    }

    public MutableLiveData<List<StarWarWarriorsModel>> getStarWarWarriorsDataList() {
        return starWarWarriorsDataList;
    }
}
