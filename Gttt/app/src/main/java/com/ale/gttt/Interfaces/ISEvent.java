package com.ale.gttt.Interfaces;

import com.ale.gttt.entities.Event;
import com.ale.gttt.entities.Subjet;

import java.util.ArrayList;
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
    Call<ArrayList<Event>> GetAll(@Query("IdUser") int idUser);

    @GET("api/event")
    Call<ArrayList<Event>> GetAllParam(@Query("IdUser") int idUser, @Query("priority") int priority);

    @GET("api/event/")
    Call<ArrayList<Event>> SearchSubjet(@Query("IdUser") int idUser,  @Query("idSubjet") int idsubjet);

    @GET("api/event/")
    Call<ArrayList<Event>> GetTypeOf(@Query("IdUser") int idUser,  @Query("typeOf") int type);

    @POST("api/event")
    Call<Void> Create(@Body Event e);

    @DELETE("api/event/{id}")
    Call<Void> Delete(@Path("id") int id);

    @DELETE("api/event/deleteall/")
    Call<Void> DeleteAll(@Query("IdUser") int idUser);

    @DELETE("api/event/deleteall/")
    Call<Void> DeleteBySubjet(@Query("IdUser") int idUser, @Query("IdSubjet") int idSubjet);

    @PUT("api/event")
    Call<Event> Update(@Query("id") int id, @Body Event u);
}
