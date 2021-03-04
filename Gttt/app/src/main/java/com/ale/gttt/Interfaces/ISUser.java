package com.ale.gttt.Interfaces;
import com.ale.gttt.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ISUser {
    @GET("api/user")
    Call<List<User>> GetAll();

    @GET("api/user/{id}")
    Call<User> GetById(@Path("id") int id);

    @POST("api/user")
    Call<Void> Create(@Body User u);

    @DELETE("api/user/{id}")
    Call<Void> Delete(@Header ("Authorization") String auth,@Path("id") int id);

    @PUT("api/user")
    Call<User> Update(@Header("Authorization") String auth, @Query("id") int id, @Body User u);


}
