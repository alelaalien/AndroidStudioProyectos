package com.ale.gttt.Interfaces;

import com.ale.gttt.entities.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ISEvent {

    @GET("api/event")
    Call<List<Event>> GetAll();

    @POST("api/event")
    Call<Void> Create(@Body Event e);

    @DELETE("api/event/{id}")
    Call<Void> Delete(@Path("id") int id);

    @PUT("api/event")
    Call<Void> Update(@Body Event u);
}
