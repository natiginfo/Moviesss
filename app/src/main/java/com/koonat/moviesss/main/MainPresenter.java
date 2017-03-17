package com.koonat.moviesss.main;

import com.koonat.moviesss.models.MovieListResponse;
import com.koonat.moviesss.networking.NetworkError;
import com.koonat.moviesss.networking.Service;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
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

        Subscription subscription = service.getMovieList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends MovieListResponse>>() {
                    @Override
                    public Observable<? extends MovieListResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<MovieListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        NetworkError networkError = new NetworkError(e);
                        view.removeProgress();
                        view.onFailure(networkError.getAppErrorMessage());

                    }

                    @Override
                    public void onNext(MovieListResponse movieListResponse) {
                        view.removeProgress();
                        view.onSuccess(movieListResponse);
                    }
                });
        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
    
}
