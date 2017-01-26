package com.udacity.project2.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.udacity.project2.popularmovies.interfaces.ColumnsMovies;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.ExecOnCreate;
import net.simonvt.schematic.annotation.OnConfigure;
import net.simonvt.schematic.annotation.OnCreate;
import net.simonvt.schematic.annotation.OnUpgrade;
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

    @ExecOnCreate
    public static final String EXEC_ON_CREATE = "SELECT * FROM " + MY_MOVIES;

    @ExecOnCreate
    public static final String EXEC_ON_CREATE1 = "SELECT * FROM " + FAVOURITE_MOVIES;

    private MoviesDatabase() {
    }



}
