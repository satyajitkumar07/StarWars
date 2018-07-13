package com.gami.starwars.view.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gami.starwars.R;
import com.gami.starwars.listener.ActionListenerInterface;
import com.gami.starwars.model.StarWarWarriorsModel;
import com.gami.starwars.network.ApiInterface;
import com.gami.starwars.network.NetworkManager;
import com.gami.starwars.network.RequestManager;
import com.gami.starwars.view.activity.MainActivity;
import com.gami.starwars.view.adapter.WarriorsListScreenAdapter;
import com.gami.starwars.view_model.StarWarWarriorsListViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/*
* showing the list of names which is fetched from server
* */

public class WarriorsListScreen extends BaseMaterialFragment implements NetworkManager,ActionListenerInterface {
    private ApiInterface apiInterface;
    private int ID_LIST=1;
    private StarWarWarriorsListViewModel starWarWarriorsListViewModel;
    private WarriorsListScreenAdapter warriorsListScreenAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<StarWarWarriorsModel> starWarWarriorsModelsList;
    private int count;
    private int totalReq=35;
    // bind view
    @BindView(R.id.list_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.try_again_btn)
    TextView tryAgainBtn;
    @BindView(R.id.loading_txt)
    TextView loading;

    public static WarriorsListScreen newInstance() {
        WarriorsListScreen fragment = new WarriorsListScreen();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            if(getArguments()!=null){
                int layoutId=getArguments().getInt("layout_id");
                setLayoutId(layoutId);
            }
        }catch (Exception ex){}
        apiInterface=((MainActivity)getActivity()).getApiInterface();
    }

    @Override
    protected void setScreen(View view) {
        ButterKnife.bind(this, view);
        //get view model object by ViewModelProviders
        starWarWarriorsListViewModel =
                ViewModelProviders.of(this).get(StarWarWarriorsListViewModel.class);
        //request for api calling
        callWarriorApi();
        updateList();
    }

    @Override
    public void onResume() {
        super.onResume();
        setScreenName("Warrior List");
        setHasOptionsMenu(true);
        showBackArrow(false);
    }

    private void updateList(){
        if(starWarWarriorsListViewModel!=null){
            starWarWarriorsListViewModel.getStarWarWarriorsDataList().observe(this, new Observer<List<StarWarWarriorsModel>>() {
                @Override
                public void onChanged(@Nullable List<StarWarWarriorsModel> starWarWarriorsModels) {
                    starWarWarriorsModelsList=starWarWarriorsModels;
                    callAdapter(starWarWarriorsModels);
                    if(count==totalReq && starWarWarriorsModels.size()>0){
                        loading.setVisibility(View.GONE);
                    }
                }
            });

        }
    }

    private void callAdapter(List<StarWarWarriorsModel> starWarWarriorsList){
        if(warriorsListScreenAdapter==null){
            // use a linear layout manager
            layoutManager=new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(layoutManager);
            // define an adapter
            warriorsListScreenAdapter=new WarriorsListScreenAdapter();
            //set instance of ActionListenerInterface for loose coupling
            warriorsListScreenAdapter.setListenerInterfaceInstance(this);
            //set data list of adapter
            warriorsListScreenAdapter.setStarWarWarriorsList(starWarWarriorsList);
            //set adapter
            mRecyclerView.setAdapter(warriorsListScreenAdapter);
        }else{
            //updating data list of adapter
            warriorsListScreenAdapter.setStarWarWarriorsList(starWarWarriorsList);
            //notify adapter after data list change
            warriorsListScreenAdapter.notifyDataSetChanged();
        }
    }

    /*
    * click event of tryAgainBtn view
    * */
    @OnClick({R.id.try_again_btn})
    public void onButtonClickTryAgain(View view) {
        count=0;
        tryAgainBtn.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        //request for api calling
        callWarriorApi();
    }

    /*
    * generate request to server for data fetching
    * */
    private void callWarriorApi(){
        for(int k=1;k<=totalReq;k++){
            Call<StarWarWarriorsModel> call=apiInterface.warriorDetailsWebService(k+"/");
            new RequestManager<StarWarWarriorsModel>().requestCall(ID_LIST,this,getActivity(),call);
        }
    }

    /*
     * called if server response is success
     * */
    @Override
    public void onReceiveResponse(int identity, Response response) {
        if(identity==ID_LIST){
            count++;
            StarWarWarriorsModel starWarWarriorsModel=(StarWarWarriorsModel)response.body();
            starWarWarriorsListViewModel.addStarWarWarriorsModel(starWarWarriorsModel);
        }
    }

    /*
     * called if server error
     * */
    @Override
    public void onErrorResponse(int identity) {
        count++;
        if(count==totalReq && (starWarWarriorsModelsList==null || starWarWarriorsModelsList.size()<1)){
            showColoredToast(getString(R.string.facing_problem),Color.RED);
            tryAgainBtn.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }
    }

    /*
     * called if server error
     * */
    @Override
    public void onFailureReceive(int identity, Throwable throwable) {
        count++;
        if(count==totalReq && (starWarWarriorsModelsList==null || starWarWarriorsModelsList.size()<1)){
            showColoredToast(getString(R.string.facing_problem),Color.RED);
            tryAgainBtn.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }
    }

    /*
     * called if any row of list will be pressed
     * @index :  tapped index of row in adapter list
     * */
    @Override
    public void getActionListener(int index) {
        if(starWarWarriorsModelsList!=null && starWarWarriorsModelsList.size()>0){
            String name=starWarWarriorsModelsList.get(index).getName();
            String height=starWarWarriorsModelsList.get(index).getHeight();
            String mass=starWarWarriorsModelsList.get(index).getMass();
            String createdOn=starWarWarriorsModelsList.get(index).getCreated();
            Bundle bundle=new Bundle();
            bundle.putString(getString(R.string.name),name);
            bundle.putString(getString(R.string.height),height);
            bundle.putString(getString(R.string.mass),mass);
            bundle.putString(getString(R.string.created),createdOn);
            ((MainActivity)getActivity()).fragmentFactory("WarriorsDetails",bundle,true);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
