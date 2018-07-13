package com.gami.starwars.di.component;

import com.gami.starwars.di.module.RetrofitModule;
import com.gami.starwars.view.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RetrofitModule.class)
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
