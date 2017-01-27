package com.udacity.project2.popularmovies.database;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.udacity.project2.popularmovies.interfaces.ColumnsMovies;
import com.udacity.project2.popularmovies.parcelable.Movie;

import java.util.ArrayList;

/**
 * Created by Dell on 1/22/2017.
 */

public class MoviesUtil {


    public static int insertData(Context context, ArrayList<Movie> movies, String storeF) {
        Cursor c = null;
        int flag = 0;
        ContentProviderOperation.Builder builder = null;
        try {
            ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(movies.size());
            if (storeF.equals("favourite")) {
                c = context.getContentResolver().query(MoviesProvider.FavouriteMovies.CONTENT_URI_FAVOURITE,
                        null, null, null, null);
            } else {

                c = context.getContentResolver().query(MoviesProvider.MyMovies.CONTENT_URI,
                        null, null, null, null);
            }


            for (Movie movie : movies) {


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


                if (flag == 1 || c.getCount() == 0) {
                    if (movie.getPosterPath() != null && movie.getTitle() != null && movie.getId() != null && storeF.equals("favourite")) {
                        builder = ContentProviderOperation.newInsert(
                                MoviesProvider.FavouriteMovies.CONTENT_URI_FAVOURITE);
                    } else if (movie.getPosterPath() != null && movie.getTitle() != null && movie.getId() != null) {
                        builder = ContentProviderOperation.newInsert(
                                MoviesProvider.MyMovies.CONTENT_URI);
                    }
                    if (builder != null) {
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
            }

            context.getContentResolver().applyBatch(MoviesProvider.AUTHORITY, batchOperations);
        } catch (Exception e) {
            Log.e("POPULAR MOVIES", "Error applying batch insert", e);

        } finally {
            //  if (c != null||!c.isClosed()) {
            //    c.close();
            //}

        }
        if (flag == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void CacheDelete(Context context) {

        try {

            context.getContentResolver().delete(MoviesProvider.MyMovies.CONTENT_URI,
                    null, null);
        } catch (Exception e) {
        }

    }

    public static void FavouriteDelete(Context context, String title) {

        try {

            context.getContentResolver().delete(MoviesProvider.FavouriteMovies.CONTENT_URI_FAVOURITE,
                    ColumnsMovies.TITLE + "=?", new String[]{title});
        } catch (Exception e) {
        }

    }

    public static int CheckisFavourite(Context context, String title) {
        Cursor me = null;
        int flag = 0;
        try {
            me = context.getContentResolver().query(MoviesProvider.FavouriteMovies.CONTENT_URI_FAVOURITE,
                    null, ColumnsMovies.TITLE + "=?", new String[]{title}, null);
                if (title.equals(me.getString(me.getColumnIndex(ColumnsMovies.TITLE)))) {
                    flag = 0;
                } else {
                    flag = 1;
                }

        } catch (Exception e) {

        } finally {
            if (me != null || !me.isClosed()) {
               me.close();
            }
        }
        if (flag == 1 || me.getCount() == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
