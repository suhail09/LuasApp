package com.example.luasapp.model.callback;

import com.example.luasapp.model.pojo.LuasPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LuasService {
    @GET("xml/get.ashx")
    Call<LuasPojo> getAllServices(@Query("stop") String stopAbv, @Query("action") String action,
                                  @Query("encrypt") String encrypt);
}
