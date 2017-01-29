package com.udacity.project2.mymovies.database;

import com.udacity.project2.mymovies.interfaces.ColumnsMovies;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Dell on 1/22/2017.
 */

@Database(version = MoviesDatabase.VERSION)
public class MoviesDatabase {
    public static final int VERSION = 1;

    //temporary table
    @Table(ColumnsMovies.class)
    public static final String MY_MOVIES = "myMovies";

    //permanent table
    @Table(ColumnsMovies.class)
    public static final String FAVOURITE_MOVIES = "myFavourite";


    private MoviesDatabase() {
    }


}
