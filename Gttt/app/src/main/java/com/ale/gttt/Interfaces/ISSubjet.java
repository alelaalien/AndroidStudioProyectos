package com.ale.gttt.Interfaces;

import com.ale.gttt.entities.Subjet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ISSubjet {

    @GET("api/subjet/")
    Call<ArrayList<Subjet>> GetAll(@Header ("Authorization") String auth,@Query("IdUser") int idUser, @Query("name") String name, @Query("Active") int active);

    @GET("api/subjet/")
    Call<ArrayList<Subjet>> GetById(@Header ("Authorization") String auth,@Query("Id") int id);

    @POST("api/subjet")
    Call<Void> Create(@Header ("Authorization") String auth, @Body Subjet s);

    @DELETE("api/subjet/{id}")
    Call<Void> Delete(@Header ("Authorization") String auth,@Path("id") int id);

    @DELETE("api/subjet/deleteall/")
    Call<Void> DeleteAll(@Header ("Authorization") String auth,@Query("IdUser") int idUser);

    @PUT("api/subjet")
    Call<Subjet> Update(@Header ("Authorization") String auth,@Query("id") int id, @Body Subjet u);
}
