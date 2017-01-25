package com.udacity.project2.popularmovies.retrofitusedinproject;

import com.google.gson.annotations.SerializedName;
import com.udacity.project2.popularmovies.parcelable.Movie;
import com.udacity.project2.popularmovies.parcelable.MovieTrailer;

import java.util.List;

/**
 * Created by Dell on 12/22/2016.
 */

public class MovieResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Movie> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("trailers")
    private  int trailers;
    @SerializedName("trailerR")
    private List<MovieTrailer> m;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public List<MovieTrailer> getTrailerResults() {
        return m;
    }

    public void setTrailerResult(List<MovieTrailer> m){this.m=m;}

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}