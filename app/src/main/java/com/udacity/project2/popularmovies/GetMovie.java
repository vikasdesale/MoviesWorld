package com.udacity.project2.popularmovies;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Dell on 12/20/2016.
 */
public class GetMovie {
    public static ArrayList<MyParcelable> movieParcelable;
    private static MyParcelable[] movies;

    //Get Movie Data from JSON
    public static ArrayList<MyParcelable> getMovieDataFromJson(String movieJsonStr) throws JSONException {

        JSONObject movieJsonObject = new JSONObject(movieJsonStr);
        JSONArray movieArrayData = movieJsonObject.getJSONArray(Strings.MOVIE_LIST);
        movies = new MyParcelable[movieArrayData.length()];
        for (int i = 0; i < movieArrayData.length(); i++) {

            // Get the JSON object representing the movie
            JSONObject movieData = movieArrayData.getJSONObject(i);
            movies[i] = new MyParcelable(
                    movieData.getString(Strings.MOVIE_TITLE),
                    movieData.getString(Strings.MOVIE_POSTER),
                    movieData.getString(Strings.MOVIE_OVERVIEW),
                    movieData.getString(Strings.MOVIE_RELEASE_DATE),
                    movieData.getString(Strings.MOVIE_VOTES));
        }
        movieParcelable = new ArrayList<MyParcelable>(Arrays.asList(movies));
        return movieParcelable;
    }
}
