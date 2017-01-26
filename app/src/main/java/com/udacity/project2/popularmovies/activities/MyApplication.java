package com.udacity.project2.popularmovies.activities;

import android.app.Application;

import com.udacity.project2.popularmovies.database.MoviesUtil;

/**
 * Created by Dell on 1/26/2017.
 */

public class MyApplication extends Application {

        @Override
        public void onCreate() {
            super.onCreate();
            MoviesUtil.CacheDelete(getBaseContext());
        }

}
