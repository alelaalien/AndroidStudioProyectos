package com.ale.gttt.Interfaces;

import com.ale.gttt.entities.Login;
import com.ale.gttt.entities.Token;
import com.ale.gttt.entities.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ISSession {

    @POST("api/token2/login")
    Call<Token> Login(@Body Login l);
}
