package com.gami.starwars.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gami.starwars.R;
import com.gami.starwars.utils.CommonData;
import com.gami.starwars.view_model.StarWarriorRequiredDetailsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
 * showing the required details
 * */
public class WarriorsDetails extends BaseMaterialFragment {

    private StarWarriorRequiredDetailsViewModel starWarriorRequiredDetailsViewModel;
    private Unbinder unbinder;
    // bind view
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.height)
    TextView height;
    @BindView(R.id.mass)
    TextView mass;
    @BindView(R.id.created)
    TextView created;

    public static WarriorsDetails newInstance() {
        WarriorsDetails fragment = new WarriorsDetails();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            if(getArguments()!=null){
                int layoutId=getArguments().getInt("layout_id");
                String name=getArguments().getString(getString(R.string.name));
                String height=getArguments().getString(getString(R.string.height));
                String mass=getArguments().getString(getString(R.string.mass));
                String created=getArguments().getString(getString(R.string.created));
                setData(name,height,mass,created);
                setLayoutId(layoutId);
            }
        }catch (Exception ex){}

    }

    private void setData(String name,String height,String mass,String created){
        //get view model object by ViewModelProviders
        starWarriorRequiredDetailsViewModel =
                ViewModelProviders.of(this).get(StarWarriorRequiredDetailsViewModel.class);
        if(starWarriorRequiredDetailsViewModel!=null){
            starWarriorRequiredDetailsViewModel.setName(name);
            starWarriorRequiredDetailsViewModel.setHeight(height);
            starWarriorRequiredDetailsViewModel.setMass(mass);
            starWarriorRequiredDetailsViewModel.setCreatedOn(created);
        }
    }

    @Override
    protected void setScreen(View view) {
        unbinder=ButterKnife.bind(this,view);
        name.setText(" : "+starWarriorRequiredDetailsViewModel.getName());
        height.setText(" : "+starWarriorRequiredDetailsViewModel.getHeight()+" meters");
        mass.setText(" : "+starWarriorRequiredDetailsViewModel.getMass()+" kg");
        created.setText(" : "+starWarriorRequiredDetailsViewModel.getCreatedOn());
    }

    @Override
    public void onResume() {
        super.onResume();
        setScreenName("Warrior Details");
        setHasOptionsMenu(true);
        showBackArrow(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == CommonData.backId) {
            getBack();
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
