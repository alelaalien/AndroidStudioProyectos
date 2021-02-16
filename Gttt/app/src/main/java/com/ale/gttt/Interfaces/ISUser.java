package com.ale.gttt.Interfaces;
import com.ale.gttt.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ISUser {
    @GET("api/user")
    Call<List<User>> GetAll();

    @POST("api/user")
    Call<Void> Create(@Body User u);

    @DELETE("api/user/{id}")
    Call<Void> Delete(@Path("id") int id);

    @PUT("api/user")
    Call<User> Update(@Query("id") int id, @Body User u);


}
