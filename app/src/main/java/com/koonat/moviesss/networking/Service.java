package com.koonat.moviesss.networking;

import com.koonat.moviesss.models.MovieListResponse;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Natig on 3/16/17.
 */

public class Service {
    private final NetworkService networkService;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Subscription getMovieList(final GetMovieListCallback callback) {

        return networkService.getMovies()
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
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(MovieListResponse movieListResponse) {
                        callback.onSuccess(movieListResponse);

                    }
                });
    }

    public interface GetMovieListCallback {
        void onSuccess(MovieListResponse movieListResponse);

        void onError(NetworkError networkError);
    }
}
