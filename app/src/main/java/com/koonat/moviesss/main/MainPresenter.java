package com.koonat.moviesss.main;

import com.koonat.moviesss.models.MovieListResponse;
import com.koonat.moviesss.networking.NetworkError;
import com.koonat.moviesss.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Natig on 3/16/17.
 */

public class MainPresenter {
    private final Service service;
    private final MainView view;
    private CompositeSubscription subscriptions;

    public MainPresenter(Service service, MainView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getMovieList() {
        view.showProgress();

        Subscription subscription = service.getMovieList(new Service.GetMovieListCallback() {
            @Override
            public void onSuccess(MovieListResponse movieListResponse) {
                view.removeProgress();
                view.onSuccess(movieListResponse);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeProgress();
                view.onFailure(networkError.getAppErrorMessage());
            }

        });

        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
