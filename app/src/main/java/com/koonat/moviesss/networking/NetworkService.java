package com.koonat.moviesss.networking;

import com.koonat.moviesss.models.MovieListResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Natig on 3/16/17.
 */

public interface NetworkService {
    @GET("/3/movie/popular?api_key=25278609ce4c34e23362aeceed566e9a&language=en-US&page=1")
    Observable<MovieListResponse> getMovies();
}
