package com.udacity.project2.popularmovies.notuseoldmethodasync;

import com.udacity.project2.popularmovies.parcelable.Movie;

import java.util.ArrayList;

/**
 * Created by Dell on 12/22/2016.
 */

public interface MovieAsync {
    void setup(final ArrayList<Movie> movies);
}
