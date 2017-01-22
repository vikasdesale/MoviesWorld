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
            builder.withValue(ColumnsMovies.POSTER_PATH,  movie.getPosterPath());
            builder.withValue(ColumnsMovies.TITLE, movie.getTitle());
            builder.withValue(ColumnsMovies.ADULT,movie.isAdult());
            builder.withValue(ColumnsMovies.BACKDROP_PATH,(movie.getBackdropPath() == null)   ? "" : movie.getBackdropPath());
            builder.withValue(ColumnsMovies.ORIGIN_LANGUAGE, (movie.getOriginLanguage() == null) ? "" : movie.getOriginLanguage());
            builder.withValue(ColumnsMovies.ORIGIN_TITLE, (movie.getOriginTitle() == null)    ? "" : movie.getOriginTitle());
            builder.withValue(ColumnsMovies.OVERVIEW,(movie.getOverview() == null)       ? "" : movie.getOverview());
            builder.withValue(ColumnsMovies.RELEASE_DATE, (movie.getReleaseDate() == null)    ? "" :  movie.getReleaseDate());
            builder.withValue(ColumnsMovies.POPULARITY,(movie.getPopularity() == null)    ? 0.0 : movie.getPopularity());
            builder.withValue(ColumnsMovies.VOTE_AVERAGE,(movie.getVoteAverage() == null)    ? 0.0 :  movie.getVoteAverage());
            builder.withValue(ColumnsMovies.VIDEO,(movie.getVideo() == null)    ? false : movie.getVideo());
            builder.withValue(ColumnsMovies.VOTE_COUNT, (movie.getVoteCount() == null)    ? 0 : movie.getVoteCount());
            builder.withValue(ColumnsMovies.FAVOURITE,(movie.getReleaseDate() == null)    ? false: movie.isFavourite());
            batchOperations.add(builder.build());
        }

        try{
            context.getContentResolver().applyBatch(MoviesProvider.AUTHORITY, batchOperations);
        } catch(RemoteException | OperationApplicationException e){
            Log.e("vikas...........", "Error applying batch insert", e);
        }

    }

}
