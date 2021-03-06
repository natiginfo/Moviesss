package com.koonat.moviesss.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.koonat.moviesss.BaseApp;
import com.koonat.moviesss.R;
import com.koonat.moviesss.about.AboutUsActivity;
import com.koonat.moviesss.details.MovieDetailsActivity;
import com.koonat.moviesss.models.Movie;
import com.koonat.moviesss.models.MovieListResponse;
import com.koonat.moviesss.networking.Service;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseApp
        implements NavigationView.OnNavigationItemSelectedListener, MainView {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;

    @BindView(R.id.nav_view) NavigationView navigationView;

    @BindView(R.id.recyclerViewMoviesList) RecyclerView recyclerView;

    @BindView(R.id.progressBar) ProgressBar progressBar;

    @Inject public Service service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDeps().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();

        MainPresenter presenter = new MainPresenter(service, this);
        presenter.getMovieList();
    }

    private void init() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about_us) {
            Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void showProgress() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeProgress() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(MovieListResponse movieListResponse) {
        MainAdapter adapter = new MainAdapter(getApplicationContext(), movieListResponse.getResults(),
                new MainAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Movie movie) {
                        Intent intent = MovieDetailsActivity.newIntent(MainActivity.this, movie.getTitle() + " (" + movie.getYear() + ")", movie.getImageUrl(), movie.getOverview());
                        startActivity(intent);

                    }
                });

        recyclerView.setAdapter(adapter);
    }
}