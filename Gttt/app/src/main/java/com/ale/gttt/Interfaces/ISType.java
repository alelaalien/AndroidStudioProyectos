package com.ale.gttt.Interfaces;

import com.ale.gttt.entities.TypeOf;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ISType {

    @GET("api/typeOf")
    Call<ArrayList<TypeOf>> GetAllFilters(@Query("IdUser") int idUser, @Query("description") String description);

    @GET("api/typeOf")
    Call<ArrayList<TypeOf>> GetAll(@Query("IdUser") int id);


    @POST("api/typeOf")
    Call<Void> Create(@Body TypeOf t);

    @DELETE("api/typeof/{id}")
    Call<Void> Delete(@Path("id") int id);

    @DELETE("api/typeof/deleteall/")
    Call<Void> DeleteAll(@Query("IdUser") int idUser);

    @PUT("api/typeof")
    Call<Void> Update(@Query("id") int id, @Body TypeOf t);


}
