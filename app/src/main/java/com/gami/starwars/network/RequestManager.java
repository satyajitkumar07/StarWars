package com.gami.starwars.network;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gami.starwars.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestManager<T> {

    public void requestCall(final int identity, final NetworkManager listener, final Context context, Call<T> call){
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if(response!=null){
                    if(response.isSuccessful()){
                        listener.onReceiveResponse(identity,response);
                    }else{
                        listener.onErrorResponse(identity);
//                        showColoredToast(context,context.getString(R.string.facing_problem), Color.RED);
                    }
                }else{
                    listener.onErrorResponse(identity);
//                    showColoredToast(context,context.getString(R.string.facing_problem),Color.RED);
                }
            }
            @Override
            public void onFailure(Call<T> call, Throwable t) {
                listener.onFailureReceive(identity,t);
//                showColoredToast(context,context.getString(R.string.facing_problem),Color.RED);
            }
        });
    }


}
