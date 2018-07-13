package com.gami.starwars.network;

import retrofit2.Response;

public interface NetworkManager<T> {
    void onReceiveResponse(int identity, Response<T> response);
    void onErrorResponse(int identity);
    void onFailureReceive(int identity,Throwable throwable);
}
