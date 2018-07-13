package com.gami.starwars.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gami.starwars.R;
import com.gami.starwars.appcontext.StarWarApp;
import com.gami.starwars.network.ApiInterface;
import com.gami.starwars.view.fragment.BaseMaterialFragment;
import com.gami.starwars.view.fragment.WarriorsDetails;
import com.gami.starwars.view.fragment.WarriorsListScreen;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.rotate_in,R.anim.rotate_out);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((StarWarApp)getApplicationContext()).getAppComponent().inject(this);
        fragmentFactory("WarriorsListScreen",null,false);
    }

    /*
    * using factory design pattern
    * @type: which fragment is to call, define its type
    * @bundle: pass data from one fragment to another if required
    * @isAddToBackStack : set true/false whether fragment need to add on activity back-stack or not
    * */
    public void fragmentFactory(String type, Bundle bundle, boolean isAddToBackStack){
        if(type!=null && type.length()>0){
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            BaseMaterialFragment baseMaterialFragment=null;
            if(bundle==null){
                bundle=new Bundle();
            }
            if(type.equalsIgnoreCase("WarriorsListScreen")){
                bundle.putInt("layout_id", R.layout.warrior_list_layout);
                baseMaterialFragment= WarriorsListScreen.newInstance();
            }else if(type.equalsIgnoreCase("WarriorsDetails")){
                bundle.putInt("layout_id", R.layout.warrior_details_layout);
                baseMaterialFragment= WarriorsDetails.newInstance();
            }
            baseMaterialFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.content_main,baseMaterialFragment);
            if(isAddToBackStack){
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commit();
        }
    }

    /*
    * provide ApiInterface instance
    * */
    public ApiInterface getApiInterface(){
        return apiInterface;
    }
}

