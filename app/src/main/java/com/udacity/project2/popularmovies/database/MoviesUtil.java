package com.udacity.project2.popularmovies.database;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.udacity.project2.popularmovies.interfaces.ColumnsMovies;
import com.udacity.project2.popularmovies.parcelable.Movie;

import java.util.ArrayList;

/**
 * Created by Dell on 1/22/2017.
 */

public class MoviesUtil {
    public static void insertData(Context context,ArrayList<Movie> movies){
        Log.d("Vikas insert..........", "insert");
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(movies.size());

        for ( Movie movie : movies){
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                    MoviesProvider.MyMovies.CONTENT_URI);
            builder.withValue(ColumnsMovies.KEY,movie.getId());
            builder.withValue(ColumnsMovies.POSTER_PATH, movie.getPosterPath());
            builder.withValue(ColumnsMovies.TITLE, movie.getTitle());
            builder.withValue(ColumnsMovies.ADULT,movie.isAdult());
            builder.withValue(ColumnsMovies.BACKDROP_PATH, movie.getBackdropPath());
            builder.withValue(ColumnsMovies.ORIGIN_LANGUAGE, movie.getOriginalLanguage());
            builder.withValue(ColumnsMovies.ORIGIN_TITLE,movie.getTitle());
            builder.withValue(ColumnsMovies.OVERVIEW, movie.getOverview());
            builder.withValue(ColumnsMovies.RELEASE_DATE, movie.getReleaseDate());
            builder.withValue(ColumnsMovies.POPULARITY,movie.getPopularity());
            builder.withValue(ColumnsMovies.VOTE_AVERAGE, movie.getVoteAverage());
            builder.withValue(ColumnsMovies.VIDEO,movie.getVideo());
            builder.withValue(ColumnsMovies.VOTE_COUNT, movie.getVoteCount());
            builder.withValue(ColumnsMovies.FAVOURITE,movie.isFavourite());
            batchOperations.add(builder.build());
        }

        try{
            context.getContentResolver().applyBatch(MoviesProvider.AUTHORITY, batchOperations);
        } catch(RemoteException | OperationApplicationException e){
            Log.e("vikas...........", "Error applying batch insert", e);
        }

    }

}
