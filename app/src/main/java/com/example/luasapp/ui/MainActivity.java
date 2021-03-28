package com.example.luasapp.ui;

import android.graphics.Color;
import android.os.Bundle;

import com.example.luasapp.R;
import com.example.luasapp.controller.RestManager;
import com.example.luasapp.helper.Utils;
import com.example.luasapp.model.adapter.LuasRecyclerViewAdapter;
import com.example.luasapp.model.pojo.Direction;
import com.example.luasapp.model.pojo.LuasPojo;
import com.example.luasapp.model.pojo.Tram;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private LuasRecyclerViewAdapter luasRecyclerViewAdapter;
    private RestManager restManager;
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        swipeLayout = findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);

        restManager = new RestManager();
        configViews();

        Calendar now = Calendar.getInstance();
        toolBarLayout.setTitle(now.get(Calendar.AM_PM) == Calendar.AM ? "Marlborough LUAS stop"
                : "Stillorgan LUAS stop");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_refresh);
        fab.setColorFilter(ContextCompat.getColor(this, R.color.white));
        fab.setOnClickListener(view -> loadRecyclerViewData(now));

        loadRecyclerViewData(now);
    }

    private void configViews() {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.luasListRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        luasRecyclerViewAdapter = new LuasRecyclerViewAdapter(this);
        recyclerView.setAdapter(luasRecyclerViewAdapter);
    }

    private void loadRecyclerViewData(Calendar now) {
        if (getNetworkAvailability()) {
            loadLuasFeed(now.get(Calendar.AM_PM) == Calendar.AM ? "mar" : "sti");
        } else {
            Snackbar.make(findViewById(android.R.id.content),
                    "Please connect to the internet!", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRefresh() {
        Calendar now = Calendar.getInstance();
        loadRecyclerViewData(now);
    }

    private void loadLuasFeed(String stopAbbreviation) {

        // Showing refresh animation before making http call
        swipeLayout.setRefreshing(true);

        // To remove the duplicated list below
        luasRecyclerViewAdapter.reset();
        luasRecyclerViewAdapter.notifyDataSetChanged();

        Call<LuasPojo> listCall = restManager.getLuasService().getAllServices(stopAbbreviation,
                "forecast", "false");
        // execute for the call back (asynchronous)
        listCall.enqueue(new Callback<LuasPojo>() {
            @Override
            public void onResponse(Call<LuasPojo> call, Response<LuasPojo> response) {
                if (response.isSuccessful()) {
                    LuasPojo stopInfo = response.body();

                    for (int i = 0; i < stopInfo.getDirection().size(); i++) {
                        Direction directions = stopInfo.getDirection().get(i);
                        if (stopAbbreviation.equals("mar") &&
                                directions.getName().contentEquals("Outbound")) {
                            for (int j = 0; j < directions.getTram().size(); j++) {
                                Tram trams = directions.getTram().get(j);
                                luasRecyclerViewAdapter.addItems(trams);
                                luasRecyclerViewAdapter.notifyDataSetChanged();
                            }
                        }

                        if (stopAbbreviation.equals("sti") &&
                                directions.getName().contentEquals("Inbound")) {
                            for (int j = 0; j < directions.getTram().size(); j++) {
                                Tram trams = directions.getTram().get(j);
                                luasRecyclerViewAdapter.addItems(trams);
                                luasRecyclerViewAdapter.notifyDataSetChanged();
                            }
                        }

                    }

                    // Stopping the refresh animation
                    swipeLayout.setRefreshing(false);

                } else {
                    int sc = response.code();
                    Snackbar.make(findViewById(android.R.id.content),
                            "Not connected to the api", Snackbar.LENGTH_LONG).show();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                            break;
                    }

                    // Stopping the refresh animation
                    swipeLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<LuasPojo> call, Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), "Not connected to the api",
                        Snackbar.LENGTH_LONG).show();

                // Stopping the refresh animation
                swipeLayout.setRefreshing(false);
            }
        });

    }

    public boolean getNetworkAvailability() {
        return Utils.isNetworkAvailable(getApplicationContext());
    }

}