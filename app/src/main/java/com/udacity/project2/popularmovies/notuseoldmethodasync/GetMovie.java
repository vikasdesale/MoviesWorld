package com.udacity.project2.popularmovies.notuseoldmethodasync;


import com.udacity.project2.popularmovies.parcelable.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Dell on 12/20/2016.
 */
public class GetMovie {
    public static ArrayList<Movie> movieParcelable;
    private static Movie[] movies;

    //Get Movie Data from JSON
    public static ArrayList<Movie> getMovieDataFromJson(String movieJsonStr) throws JSONException {

        JSONObject movieJsonObject = new JSONObject(movieJsonStr);
        JSONArray movieArrayData = movieJsonObject.getJSONArray("results");
        movies = new Movie[movieArrayData.length()];
        for (int i = 0; i < movieArrayData.length(); i++) {

            // Get the JSON object representing the movie
            JSONObject movieData = movieArrayData.getJSONObject(i);
         /*   movies[i] = new Movie(
                    movieData.getString("poster_path"),
                    ...................like
        */
        }

        movieParcelable = new ArrayList<Movie>(Arrays.asList(movies));
        return movieParcelable;
    }
}
