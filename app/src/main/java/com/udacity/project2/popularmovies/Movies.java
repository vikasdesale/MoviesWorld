package com.udacity.project2.popularmovies;

/**
 * Created by Dell on 12/20/2016.
 */
public class Movies {

    private String title;
    private String poster;
    private String overview;
    private String release_date;
    private String vote;
    public Movies(String title, String poster, String overview, String release_date, String vote)
    {
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.release_date = release_date;
        this.vote = vote;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }



}
