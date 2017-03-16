package com.koonat.moviesss;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.koonat.moviesss.deps.DaggerDeps;
import com.koonat.moviesss.deps.Deps;
import com.koonat.moviesss.networking.NetworkModule;

import java.io.File;

/**
 * Created by Natig on 3/16/17.
 */

public class BaseApp extends AppCompatActivity {
    Deps deps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        Log.d("BaseApp", cacheFile.getAbsolutePath().toString());
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile, getApplicationContext())).build();
    }

    public Deps getDeps() {
        return deps;
    }
}
