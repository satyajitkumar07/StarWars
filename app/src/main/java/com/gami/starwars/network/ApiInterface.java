package com.gami.starwars.network;

import com.gami.starwars.model.StarWarWarriorsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    //"https://swapi.co/api/people/1/"

    @GET("https://swapi.co/api/people/{var}")
    Call<StarWarWarriorsModel> warriorDetailsWebService(@Path("var") String var);
}
