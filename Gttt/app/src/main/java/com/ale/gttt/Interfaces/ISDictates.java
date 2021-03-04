package com.ale.gttt.Interfaces;

import com.ale.gttt.entities.Dictates;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ISDictates {

    @GET("api/dictates")
    Call<ArrayList<Dictates>> GetAllBySubjet(@Query("subjetId") int id);

    @GET("api/dictates")
    Call<ArrayList<Dictates>> GetAllByTeacher(@Query("teacherId") int id);


    @POST("api/dictates")
    Call<Void> Create(@Body Dictates t);

    @DELETE("api/dictates")
    Call<Void> DeleteByTeacher(@Query("teacherId") int idTeach);

    @DELETE("api/dictates")
    Call<Void> DeleteBySubjet(@Query("subjetId") int idSub);



}
