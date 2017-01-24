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

    public static void insertData(Context context, ArrayList<Movie> movies) {
        Log.d("Vikas insert..........", "insert");
        Cursor c;
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(movies.size());
        c = context.getContentResolver().query(MoviesProvider.MyMovies.CONTENT_URI,
                null, null, null, null);
        int flag = 0;
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
                    if (movie.getPosterPath() != null && movie.getTitle() != null && movie.getId() != null) {
                        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                                MoviesProvider.MyMovies.CONTENT_URI);
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
                        builder.withValue(ColumnsMovies.FAVOURITE, (movie.getReleaseDate() == null) ? false : movie.isFavourite());
                        batchOperations.add(builder.build());
                    }

            }


            context.getContentResolver().applyBatch(MoviesProvider.AUTHORITY, batchOperations);
        } catch (RemoteException | OperationApplicationException e) {
            Log.e("POPULAR MOVIES", "Error applying batch insert", e);

        } finally {
            c.close();
        }

    }

    /*public static ArrayList<Movie> getDatabaseContent(Context context){
       Cursor cursor = context.getContentResolver().query(MoviesProvider.MyMovies.CONTENT_URI,
                null, null, null, null);
        ArrayList<Movie> mArrayList = new ArrayList<Movie>();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // The Cursor is now set to the right position
            mArrayList.add(new Movie(

                    cursor.getString(cursor.getColumnIndex(ColumnsMovies.POSTER_PATH)),
                    cursor.getInt(cursor.getColumnIndex(ColumnsMovies.ADULT))>0,
                    cursor.getString(cursor.getColumnIndex(ColumnsMovies.OVERVIEW)),
                    cursor.getString(cursor.getColumnIndex(ColumnsMovies.RELEASE_DATE)),
                    null,
                    cursor.getInt(cursor.getColumnIndex(ColumnsMovies.KEY)),
                    cursor.getString(cursor.getColumnIndex(ColumnsMovies.ORIGIN_LANGUAGE)),
                    cursor.getString(cursor.getColumnIndex(ColumnsMovies.ORIGIN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(ColumnsMovies.TITLE)),
                    cursor.getString(cursor.getColumnIndex(ColumnsMovies.BACKDROP_PATH)),
                    cursor.getDouble(cursor.getColumnIndex(ColumnsMovies.POPULARITY)),
                    cursor.getInt(cursor.getColumnIndex(ColumnsMovies.VOTE_COUNT)),
                    cursor.getInt(cursor.getColumnIndex(ColumnsMovies.VIDEO))>0,
                    cursor.getDouble(cursor.getColumnIndex(ColumnsMovies.VOTE_AVERAGE)),
                    cursor.getInt(cursor.getColumnIndex(ColumnsMovies.FAVOURITE))>0));

        }

        return mArrayList;
    }*/

    public static Cursor getCursor(Context context) {
        return context.getContentResolver().query(MoviesProvider.MyMovies.CONTENT_URI,
                null, null, null, null);
    }
}
