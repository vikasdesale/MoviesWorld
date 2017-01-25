package com.udacity.project2.popularmovies.retrofitusedinproject;

import com.google.gson.annotations.SerializedName;
import com.udacity.project2.popularmovies.parcelable.Movie;
import com.udacity.project2.popularmovies.parcelable.MovieTrailer;
import com.udacity.project2.popularmovies.parcelable.Result;

import java.util.List;

/**
 * Created by Dell on 1/25/2017.
 */

public class MovieTrailerResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private List<Result> results;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
