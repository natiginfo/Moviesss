package com.koonat.moviesss.main;

import com.koonat.moviesss.models.Movie;
import com.koonat.moviesss.models.MovieListResponse;
import com.koonat.moviesss.networking.Service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

/**
 * Created by Natig on 3/16/17.
 */

public class MainPresenterTest {

    @Mock
    Service mockService; // repository

    @Mock
    MainView view;

    MainPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenter(mockService, view);
    }


    @Test
    public void shouldPass() {
        Assert.assertEquals(1, 1);
    }

    @Test
    public void test1() {
        List<Movie> moviesList = Arrays.asList(new Movie(), new Movie(), new Movie());
        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.setResults(moviesList);

        Observable<MovieListResponse> mockObservable = Observable.just(movieListResponse);
        doReturn(mockObservable).when(mockService).getMovieList();

    }


    @Test
    public void test2() {
        Observable<MovieListResponse> mockObservable = Observable.never();

        doReturn(mockObservable).when(mockService).getMovieList();


        TestSubscriber<MovieListResponse> testSubscriber = new TestSubscriber<>();

        mockService.getMovieList().subscribe(testSubscriber);

    }

}