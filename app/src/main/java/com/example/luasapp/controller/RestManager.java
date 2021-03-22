package com.example.luasapp.controller;

import com.example.luasapp.model.callback.LuasService;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RestManager {
    private LuasService luasService;

    public LuasService getLuasService() {
        if (luasService == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://luasforecasts.rpa.ie/")
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
            luasService = retrofit.create(LuasService.class);

        }
        return luasService;
    }
}
