package com.ale.gttt.Interfaces;

import com.ale.gttt.entities.Event;
import com.ale.gttt.entities.Subjet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ISEvent {

    @GET("api/event")
    Call<List<Event>> GetAll();

    @POST("api/event")
    Call<Void> Create(@Body Event e);

    @DELETE("api/event/{id}")
    Call<Void> Delete(@Path("id") int id);

    @DELETE("api/event/deleteall/")
    Call<Void> DeleteAll(@Query("IdUser") int idUser);

    @PUT("api/event")
    Call<Void> Update(@Query("id") int id, @Body Event u);
}
