package com.koonat.moviesss.deps;

import com.koonat.moviesss.main.MainActivity;
import com.koonat.moviesss.networking.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Natig on 3/16/17.
 */

@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(MainActivity homeActivity);
}
