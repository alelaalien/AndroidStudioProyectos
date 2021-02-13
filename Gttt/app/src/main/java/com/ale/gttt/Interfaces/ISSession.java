package com.ale.gttt.Interfaces;

import com.ale.gttt.entities.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ISSession {

    @POST("api/token2/login")
    Call<User> Login(@Body User u);
}
