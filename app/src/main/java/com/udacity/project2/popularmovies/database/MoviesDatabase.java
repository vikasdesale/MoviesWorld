package com.udacity.project2.popularmovies.database;

import com.udacity.project2.popularmovies.interfaces.ColumnsMovies;
import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Dell on 1/22/2017.
 */

@Database(version = MoviesDatabase.VERSION)
public class MoviesDatabase {
    private MoviesDatabase(){}

    public static final int VERSION = 1;

    @Table(ColumnsMovies.class) public static final String MY_MOVIES = "myMovies";
}
