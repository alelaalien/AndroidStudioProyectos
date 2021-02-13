package com.ale.gttt.Interfaces;

import com.ale.gttt.entities.Teacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ISTeacher {

    @GET("api/teacher")
    Call<List<Teacher>> GetAll();

    @POST("api/teacher")
    Call<Void> Create(@Body Teacher t);

    @DELETE("api/teacher/{id}")
    Call<Void> Delete(@Path("id") int id);

    @PUT("api/teacher")
    Call<Void> Update(@Body Teacher t);
}
