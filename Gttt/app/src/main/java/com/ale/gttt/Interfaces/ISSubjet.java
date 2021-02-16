package com.ale.gttt.Interfaces;

import com.ale.gttt.entities.Subjet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ISSubjet {

    @GET("api/subjet/")
    Call<ArrayList<Subjet>> GetAll(@Query("IdUser") int idUser, @Query("Name") String name, @Query("Active") int active);

    @POST("api/subjet")
    Call<Void> Create(@Body Subjet s);

    @DELETE("api/subjet/{id}")
    Call<Void> Delete(@Path("id") int id);

    @DELETE("api/subjet/deleteall/")
    Call<Void> DeleteAll(@Query("IdUser") int idUser);

    @PUT("api/subjet")
    Call<Subjet> Update(@Query("id") int id, @Body Subjet u);
}
