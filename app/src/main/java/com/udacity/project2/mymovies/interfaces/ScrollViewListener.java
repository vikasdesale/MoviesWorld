package com.udacity.project2.mymovies.interfaces;

/**
 * Created by Dell on 1/22/2017.
 */

public interface ScrollViewListener {
    void onScrollChanged(ScrollViewExt scrollView,
                         int x, int y, int oldx, int oldy);
}