package com.koonat.moviesss.networking;

import com.koonat.moviesss.models.MovieListResponse;

import rx.Observable;

/**
 * Created by Natig on 3/16/17.
 */

public class Service {
    private final NetworkService networkService;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Observable<MovieListResponse> getMovieList() {

        return networkService.getMovies();
    }

}
