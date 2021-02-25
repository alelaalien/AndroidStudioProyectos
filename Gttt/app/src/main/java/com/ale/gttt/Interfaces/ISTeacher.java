package com.ale.gttt.Interfaces;

import com.ale.gttt.entities.Teacher;

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

public interface ISTeacher {

    @GET("api/teacher")
    Call<ArrayList<Teacher>> GetAllFilters(@Query("IdUser") int idUser, @Query("Subjet") int s );

    @GET("api/teacher")
    Call<ArrayList<Teacher>> GetAll(@Query("IdUser") int idUser, @Query("Name") String name,@Query("Surname") String surname,@Query("Nick") String nick);


    @POST("api/teacher")
    Call<Void> Create(@Body Teacher t);

    @DELETE("api/teacher/{id}")
    Call<Void> Delete(@Path("id") int id);

    @DELETE("api/teacher/deleteall/")
    Call<Void> DeleteAll(@Query("IdUser") int idUser);

    @PUT("api/teacher")
    Call<Void> Update(@Query("id") int id, @Body Teacher t);

}
