package com.udacity.project2.popularmovies.interfaces;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Dell on 1/22/2017.
 */

public interface ColumnsMovies {
    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id";
    @DataType(DataType.Type.INTEGER)
    @NotNull
    String KEY = "key";
    @DataType(DataType.Type.TEXT)
    @NotNull
    String POSTER_PATH = "posterPath";
    @DataType(DataType.Type.TEXT)
    @NotNull
    String TITLE = "title";
    @DataType(DataType.Type.INTEGER)
    @NotNull
    String ADULT = "adult";
    @DataType(DataType.Type.TEXT)
    @NotNull
    String BACKDROP_PATH = "bckdropPath";
    @DataType(DataType.Type.TEXT)
    @NotNull
    String ORIGIN_LANGUAGE = "originLanguage";
    @DataType(DataType.Type.TEXT)
    @NotNull
    String ORIGIN_TITLE = "originTitle";
    @DataType(DataType.Type.TEXT)
    @NotNull
    String OVERVIEW = "overview";
    @DataType(DataType.Type.TEXT)
    @NotNull
    String RELEASE_DATE = "releaseDate";
    @DataType(DataType.Type.REAL)
    @NotNull
    String POPULARITY = "popularity";
    @DataType(DataType.Type.INTEGER)
    @NotNull
    String VOTE_AVERAGE = "voteAverage";
    @DataType(DataType.Type.INTEGER)
    @NotNull
    String VIDEO = "video";
    @DataType(DataType.Type.INTEGER)
    @NotNull
    String VOTE_COUNT = "voteCount";
    @DataType(DataType.Type.INTEGER)
    String FAVOURITE = "favourite";

}
