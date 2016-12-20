package com.udacity.project2.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Dell on 12/20/2016.
 */
public class GetMovie {
    private static final String LOG_TAG ="Vikas" ;
    private ArrayList<MyParcelable> movieList;

    private MyParcelable[] movies;

    public String[][] getMovieDataFromJson(String movieJsonStr)throws JSONException {

        JSONObject movieJsonObject = new JSONObject(movieJsonStr);
        JSONArray movieArrayData = movieJsonObject.getJSONArray(Strings.MOVIE_LIST);

        String[][] resultStrs = new String[movieArrayData.length()][5];
        movies = new MyParcelable[movieArrayData.length()];
        for(int i = 0; i < movieArrayData.length(); i++) {
            int k = 0;

            // Get the JSON object representing the movie
            JSONObject movieData = movieArrayData.getJSONObject(i);
            movies[i] = new MyParcelable(
                    movieData.getString(Strings.MOVIE_TITLE),
                    movieData.getString(Strings.MOVIE_POSTER),
                    movieData.getString(Strings.MOVIE_OVERVIEW),
                    movieData.getString(Strings.MOVIE_RELEASE_DATE),
                    movieData.getString(Strings.MOVIE_VOTES));
            resultStrs[i][k] = movieData.getString(Strings.MOVIE_TITLE);
            resultStrs[i][k+1] = movieData.getString(Strings.MOVIE_POSTER);
            resultStrs[i][k+2] = movieData.getString(Strings.MOVIE_OVERVIEW);
            resultStrs[i][k+3] = movieData.getString(Strings.MOVIE_RELEASE_DATE);
            resultStrs[i][k+4] = movieData.getString(Strings.MOVIE_VOTES);

            Log.d(LOG_TAG, "Movie data :" + i + "\n" + resultStrs[i]);
        }
        movieList = new ArrayList<MyParcelable>(Arrays.asList(movies));
        return resultStrs;
    }
}
