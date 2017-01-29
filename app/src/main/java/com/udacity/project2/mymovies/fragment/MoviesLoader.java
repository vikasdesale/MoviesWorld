package com.udacity.project2.mymovies.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.GridView;

import com.udacity.project2.mymovies.adapter.RecyclerViewAdapter;
import com.udacity.project2.mymovies.database.MoviesProvider;

/**
 * Created by Dell on 1/28/2017.
 */

public class MoviesLoader implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int MOVIE_LOADER = 0;
    private static final String SELECTED_KEY = "selected_position";
    private static int favflag = -1;
    private Fragment mAttachedFragment;
    private RecyclerViewAdapter gridAdapter;
    private int mPosition = GridView.INVALID_POSITION;


    private MoviesLoader() {
    }

    public static MoviesLoader newInstance(int flag, @NonNull Fragment fragment, @NonNull RecyclerViewAdapter adapter) {
        MoviesLoader element = new MoviesLoader();
        element.mAttachedFragment = fragment;
        element.gridAdapter = adapter;
        element.favflag = flag;
        return element;
    }

    public void initLoader() {
        if (mAttachedFragment != null)
            mAttachedFragment.getLoaderManager().initLoader(MOVIE_LOADER, null, this);
    }

    public void restartLoader() {
        if (mAttachedFragment != null)
            mAttachedFragment.getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        //checking on rotate
        if (mAttachedFragment != null && mAttachedFragment.getActivity() != null) {


            if (favflag == 0) {
                return new CursorLoader(mAttachedFragment.getActivity(), MoviesProvider.MyMovies.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
            } else {
                if (favflag == 1) {
                    return new CursorLoader(mAttachedFragment.getActivity(), MoviesProvider.FavouriteMovies.CONTENT_URI_FAVOURITE,
                            null,
                            null,
                            null,
                            null);

                }
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (gridAdapter != null) {
            gridAdapter.swapCursor(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        gridAdapter.swapCursor(null);
    }

}
