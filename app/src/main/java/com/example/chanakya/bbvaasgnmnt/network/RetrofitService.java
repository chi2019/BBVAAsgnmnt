package com.example.chanakya.bbvaasgnmnt.network;

import com.example.chanakya.bbvaasgnmnt.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chanakya on 5/21/2018.
 */

public interface RetrofitService {

    //    https://maps.googleapis.com/maps/api/place/textsearch/json?
    // query=BBVA+Compass&location=41.9174331,88.2653861&radius=10000&key=AIzaSyDkL1ryGllP785vw6YTZFW46Wfq-eXs_D0

   @GET("json")
    Call<Result> getResponse(@Query("query") String BBVA  , @Query("location") String latlng , @Query("radius") long rad,
                                       @Query("key") String keyString);


}
