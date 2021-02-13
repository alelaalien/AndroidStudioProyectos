package com.ale.gttt.Interfaces;

import com.ale.gttt.entities.BT;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ISSecurity {

    @POST("api/token")
    Call<List<String>> getToken(@Body BT bt);



}
