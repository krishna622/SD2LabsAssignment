package com.krishna.sdlabs.service;

import com.google.gson.JsonObject;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {


    @GET("api/users")
    Observable<JsonObject> getUsers(@Query("offset") String offset, @Query("limit") String limit);

}