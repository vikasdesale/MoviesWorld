package com.udacity.project2.popularmovies.retrofitusedinproject;

import com.google.gson.annotations.SerializedName;
import com.udacity.project2.popularmovies.parcelable.MovieTrailerResults;

import java.util.List;

/**
 * Created by Dell on 1/25/2017.
 */

public class MovieTrailerResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private List<MovieTrailerResults> movieTrailerResults;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MovieTrailerResults> getMovieTrailerResults() {
        return movieTrailerResults;
    }

    public void setMovieTrailerResults(List<MovieTrailerResults> movieTrailerResults) {
        this.movieTrailerResults = movieTrailerResults;
    }
}
