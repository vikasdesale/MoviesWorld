package com.udacity.project2.popularmovies.database;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;
import android.util.Log;

import com.udacity.project2.popularmovies.interfaces.ColumnsMovies;
import com.udacity.project2.popularmovies.parcelable.Movie;

import java.util.ArrayList;

/**
 * Created by Dell on 1/22/2017.
 */

public class MoviesUtil {

    public static void insertData(Context context, ArrayList<Movie> movies,String storeF) {
        Log.d("Vikas insert..........", "insert");
        Cursor c=null;
        int flag = 0;
        ContentProviderOperation.Builder builder=null;
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(movies.size());
       if(storeF.equals("favourite")){
           c = context.getContentResolver().query(MoviesProvider.FavouriteMovies.CONTENT_URI_FAVOURITE,
                   null, null, null, null);
       }
        else {
           c = context.getContentResolver().query(MoviesProvider.MyMovies.CONTENT_URI,
                   null, null, null, null);
       }

        try {

            for (Movie movie : movies) {

                    boolean cursorCheck = c.moveToFirst();
                    if (cursorCheck) {
                        Log.d("Vikas", String.valueOf(c.getCount()));

                        while (c.moveToNext()) {
                            if (movie.getTitle().toString().equals(c.getString(c.getColumnIndex(ColumnsMovies.TITLE)))) {
                                // Log.d("POPULAR MOVIES","Movie Title Same"+movie.getTitle().toString()+" ="+c.getString(c.getColumnIndex(ColumnsMovies.TITLE)));
                                flag = 0;
                                break;
                            } else {
                                flag = 1;
                                // Log.d("POPULAR MOVIES","Movie Title Difference"+movie.getTitle().toString()+" ="+c.getString(c.getColumnIndex(ColumnsMovies.TITLE)));

                            }
                        }
                    }


                if (flag == 1 || c.getCount() == 0)
                    if (movie.getPosterPath() != null && movie.getTitle() != null && movie.getId() != null && storeF.equals("favourite")) {
                        builder = ContentProviderOperation.newInsert(
                                MoviesProvider.FavouriteMovies.CONTENT_URI_FAVOURITE);
                    }else if(movie.getPosterPath() != null && movie.getTitle() != null && movie.getId() != null){
                        builder = ContentProviderOperation.newInsert(
                                MoviesProvider.MyMovies.CONTENT_URI);
                    }
                if(builder!=null) {
                    builder.withValue(ColumnsMovies.KEY, movie.getId());
                    builder.withValue(ColumnsMovies.POSTER_PATH, movie.getPosterPath());
                    builder.withValue(ColumnsMovies.TITLE, movie.getTitle());
                    builder.withValue(ColumnsMovies.ADULT, movie.isAdult());
                    builder.withValue(ColumnsMovies.BACKDROP_PATH, (movie.getBackdropPath() == null) ? "" : movie.getBackdropPath());
                    builder.withValue(ColumnsMovies.ORIGIN_LANGUAGE, (movie.getOriginLanguage() == null) ? "" : movie.getOriginLanguage());
                    builder.withValue(ColumnsMovies.ORIGIN_TITLE, (movie.getOriginTitle() == null) ? "" : movie.getOriginTitle());
                    builder.withValue(ColumnsMovies.OVERVIEW, (movie.getOverview() == null) ? "" : movie.getOverview());
                    builder.withValue(ColumnsMovies.RELEASE_DATE, (movie.getReleaseDate() == null) ? "" : movie.getReleaseDate());
                    builder.withValue(ColumnsMovies.POPULARITY, movie.getPopularity());
                    builder.withValue(ColumnsMovies.VOTE_AVERAGE, movie.getVoteAverage());
                    builder.withValue(ColumnsMovies.VIDEO, (movie.getVideo() == null) ? false : movie.getVideo());
                    builder.withValue(ColumnsMovies.VOTE_COUNT, movie.getVoteCount());
                    batchOperations.add(builder.build());
                }

            }


            context.getContentResolver().applyBatch(MoviesProvider.AUTHORITY, batchOperations);
        } catch ( Exception e) {
            Log.e("POPULAR MOVIES", "Error applying batch insert", e);

        }

    }



    public static Cursor getCursor(Context context) {
        Cursor c=null;
        try {

            c = context.getContentResolver().query(MoviesProvider.MyMovies.CONTENT_URI,
                    null, null, null, null);
        } catch (Exception e) {
        }
            return c;
    }

    public static Cursor getFavouriteCursor(Context context) {
        Cursor c=null;
        try {

            c = context.getContentResolver().query(MoviesProvider.FavouriteMovies.CONTENT_URI_FAVOURITE,
                    null, null, null, null);
        } catch (Exception e) {
        }
        return c;
    }
    public static void CacheDelete(Context context) {

        try {

            context.getContentResolver().delete(MoviesProvider.MyMovies.CONTENT_URI,
                    null, null);
        } catch (Exception e) {
        }

    }

    public static Boolean isFavouriteCursor(Context context,String title) {
        Cursor c=null;
        try {

            c = context.getContentResolver().query(MoviesProvider.FavouriteMovies.CONTENT_URI_FAVOURITE,
                    null, null, null, null);

            boolean cursorCheck = c.moveToFirst();
            if (cursorCheck) {
                Log.d("Vikas", String.valueOf(c.getCount()));

                while (c.moveToNext()) {
                    if (title.equals(c.getString(c.getColumnIndex(ColumnsMovies.TITLE)))) {
                        // Log.d("POPULAR MOVIES","Movie Title Same"+movie.getTitle().toString()+" ="+c.getString(c.getColumnIndex(ColumnsMovies.TITLE)));
                        return true;
                    } else {
                            return  false;

                             // Log.d("POPULAR MOVIES","Movie Title Difference"+movie.getTitle().toString()+" ="+c.getString(c.getColumnIndex(ColumnsMovies.TITLE)));

                    }
                }

            }
        } catch (Exception e) {
        }
        return false;
    }
}
