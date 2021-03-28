package com.example.luasapp;

import com.example.luasapp.controller.RestManager;
import com.example.luasapp.model.pojo.LuasPojo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LuasAPITest {

    private static final String stillorgan = "sti";
    private static final String marlborough = "mar";
    private static final String wrongStop = "34";
    private RestManager restManager;

    @Before
    public void beforeTest() {
        restManager =  new RestManager();
    }

    @Test
    public void serverCallWithSuccessful() {
        CountDownLatch latch = new CountDownLatch(1);
        Call<LuasPojo> listCall = restManager.getLuasService().getAllServices(stillorgan,
                "forecast","false");
        Assert.assertNotNull(restManager);
        listCall.enqueue(new Callback<LuasPojo>() {
            @Override
            public void onResponse(Call<LuasPojo> call, Response<LuasPojo> response) {
                if (response.isSuccessful()) {
                    System.out.println("Success");
                    latch.countDown();
                } else {
                    System.out.println("Failure");
                    latch.countDown();
                }
            }

            @Override
            public void onFailure(Call<LuasPojo> call, Throwable t) {
                System.out.println("Failure");
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void serverCallWithError() {
        CountDownLatch latch = new CountDownLatch(1);
        Call<LuasPojo> listCall = restManager.getLuasService().getAllServices(wrongStop,
                "forecast","false");
        Assert.assertNotNull(restManager);
        listCall.enqueue(new Callback<LuasPojo>() {
            @Override
            public void onResponse(Call<LuasPojo> call, Response<LuasPojo> response) {
                if (response.isSuccessful()) {
                    System.out.println("Success");
                    latch.countDown();
                } else {
                    System.out.println("Failure");
                    latch.countDown();
                }
            }

            @Override
            public void onFailure(Call<LuasPojo> call, Throwable t) {
                System.out.println("Failure");
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @After
    public void afterTest() {
    }

}
