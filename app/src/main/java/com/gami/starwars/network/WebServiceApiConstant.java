package com.gami.starwars.network;

public interface WebServiceApiConstant {
    boolean isStagingEnable=false;
    String stagingBaseUrl="https://swapi.co/api/people/1/";
    String productionBaseUrl="https://swapi.co/api/people/1/";
    String BASE_URL=isStagingEnable?stagingBaseUrl:productionBaseUrl;

}
