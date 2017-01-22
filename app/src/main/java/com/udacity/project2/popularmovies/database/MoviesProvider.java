package com.udacity.project2.popularmovies.database;

import android.net.Uri;

import com.udacity.project2.popularmovies.interfaces.ColumnsMovies;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Dell on 1/22/2017.
 */

@ContentProvider(authority = MoviesProvider.AUTHORITY, database = MoviesDatabase.class)
public class MoviesProvider {

    public static final String AUTHORITY = "com.udacity.project2.popularmovie.database.MoviesProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String MY_MOVIES = "myMovies";
    }

    private static Uri buildUri(String ... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths){
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = MoviesDatabase.MY_MOVIES) public static class MyMovies{
        @ContentUri(
                path = Path.MY_MOVIES,
                type = "vnd.android.cursor.dir/favouriteMovies",
                defaultSort = ColumnsMovies._ID + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.MY_MOVIES);

        @InexactContentUri(
                name = "FAVOURITE_MOVIE_ID",
                path = Path.MY_MOVIES + "/#",
                type = "vnd.android.cursor.item/favouriteMovie",
                whereColumn = ColumnsMovies.KEY,
                pathSegment = 1)
        public static Uri withId(long id){
            return buildUri(Path.MY_MOVIES, String.valueOf(id));
        }
    }
}
