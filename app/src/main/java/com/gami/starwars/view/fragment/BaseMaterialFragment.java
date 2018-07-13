package com.gami.starwars.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gami.starwars.R;
import com.gami.starwars.network.ApiInterface;

import javax.inject.Inject;

import retrofit2.Retrofit;

public abstract class BaseMaterialFragment extends Fragment {
    private int layoutId;
    private View rootView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
        }catch (Exception ex){}
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            if(rootView==null && layoutId!=0){
                // inflate layout
                rootView=inflater.inflate(getLayout(),container,false);
                setScreen(rootView);
            }
        }catch (Exception ex){}

        return rootView;
    }

    /*
    * set the layout of child fragment
    */
    protected abstract void setScreen(View view);

    /*
    *
    * */
    private int getLayout(){return layoutId;}

    /*
     *
     * */
    public void setLayoutId(int layoutId){
        this.layoutId=layoutId;
    }

    /*
    * {@name} use to set the title of screen through actionbar
    * */
    protected void setScreenName(String name){
        try {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setWindowTitle(name);
        }catch (Exception ex){}

    }

    /*
    * {@isEnabled} set value true/false. It show or hide the back button of actionbar
    * */
    protected void showBackArrow(boolean isEnabled){
        if (isEnabled){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        }else {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        }

    }

    /*
    * use for showing different colored toast
    * {@msg} message that need to show to user
    * {@color} color value for toast background
    * */
    public void showColoredToast( String msg, @ColorInt int color){
        Toast toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG);
        View view = toast.getView();
        //Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        //Gets the TextView from the Toast so it can be editted
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);
        //text.setTypeface(text.getTypeface(), Typeface.BOLD);
        toast.show();
    }

    /*
    * use for showing alert message{@msg} to user
    * */
    protected void showErrorMessageAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Dialog_Alert);
        builder.setMessage(message).setCancelable(true)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();
    }

    /*
    * remove fragment from activity back-stack
    * */
    protected void getBack(){
        try {
            getActivity().getSupportFragmentManager().popBackStack();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
