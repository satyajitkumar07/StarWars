package com.gami.starwars.appcontext;

import android.app.Application;

import com.gami.starwars.di.component.AppComponent;
import com.gami.starwars.di.component.DaggerAppComponent;
import com.gami.starwars.di.module.RetrofitModule;

public class StarWarApp extends Application {

    private AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent= DaggerAppComponent.builder().retrofitModule(new RetrofitModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
