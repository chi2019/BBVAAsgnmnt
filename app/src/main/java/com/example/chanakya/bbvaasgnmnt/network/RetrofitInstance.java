package com.example.chanakya.bbvaasgnmnt.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chanakya on 5/21/2018.
 */

public class RetrofitInstance {

    //  https://maps.googleapis.com/maps/api/place/textsearch/json?query=BBVA+Compass&location=41.9174331,88.2653861&radius=10000&key=AIzaSyDkL1ryGllP785vw6YTZFW46Wfq-eXs_D0

    public static String base_url ="https://maps.googleapis.com/maps/api/place/textsearch/";

    public static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance(){

        retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return retrofit;
    }


}
