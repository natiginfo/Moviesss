package com.koonat.moviesss.networking;

import android.content.Context;
import android.util.Log;

import com.koonat.moviesss.defaults.Constants;
import com.koonat.moviesss.util.Utils;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.*;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Natig on 3/16/17.
 */
@Module
public class NetworkModule {
    File cacheFile;
    private static Context context;

    public NetworkModule(File cacheFile, Context context) {
        this.cacheFile = cacheFile;
        this.context = context;
    }

    @Provides
    @Singleton
    Retrofit provideCall() {
        Cache cache = null;
        try {
            cache = new Cache(cacheFile, 60 * 60 * 24 * 28);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Response originalResponse = chain.proceed(chain.request());
                        Log.d("NetworkModule", "p1");
                        String cacheControl = originalResponse.header("Cache-Control");

                        if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                            return originalResponse.newBuilder()
                                    .header("Cache-Control", "public, max-age=" + 10)
                                    .build();
                        } else {
                            return originalResponse;
                        }
                    }
                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Log.d("NetworkModule", "p2");

                        if (!Utils.isConnectiongToInternet(context)) {
                            Log.d("Network Module", "rewriting request");

                            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                            request = request.newBuilder()
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                    .build();
                        }

                        return chain.proceed(request);
                    }
                })
                .cache(cache)
                .build();


        return new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public NetworkService providesNetworkService(
            Retrofit retrofit) {
        return retrofit.create(NetworkService.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public Service providesService(
            NetworkService networkService) {
        return new Service(networkService);
    }
}
