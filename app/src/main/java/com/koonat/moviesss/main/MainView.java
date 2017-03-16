package com.koonat.moviesss.main;

import com.koonat.moviesss.models.MovieListResponse;

/**
 * Created by Natig on 3/16/17.
 */

public interface MainView {
    void showProgress();

    void removeProgress();

    void onFailure(String errorMessage);

    void onSuccess(MovieListResponse movieListResponse);
}
