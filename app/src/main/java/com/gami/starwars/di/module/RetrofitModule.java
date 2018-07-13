package com.gami.starwars.di.module;

import com.gami.starwars.network.ApiInterface;
import com.gami.starwars.network.WebServiceApiConstant;

import dagger.Module;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {
    @Singleton
    @Provides
    public ApiInterface getApiInterface(Retrofit retrofit){
        return retrofit.create(ApiInterface.class);
    }

    @Singleton
    @Provides
    public Retrofit getRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(WebServiceApiConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }


    @Singleton
    @Provides
    OkHttpClient getOkHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }
}
