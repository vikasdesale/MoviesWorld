package com.udacity.project2.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dell on 12/20/2016.
 */
public class GetMovie {
    private static final String LOG_TAG ="Vikas" ;
    private static ArrayList<MyParcelable> movieList;

    private static MyParcelable[] movies;

    public static ArrayList<Movies> getMovieDataFromJson(String movieJsonStr)throws JSONException {

        JSONObject movieJsonObject = new JSONObject(movieJsonStr);
        JSONArray movieArrayData = movieJsonObject.getJSONArray(Strings.MOVIE_LIST);
        ArrayList<Movies> al=new ArrayList<Movies>();
        movies = new MyParcelable[movieArrayData.length()];
        for(int i = 0; i < movieArrayData.length(); i++) {
            int l = 0;

            // Get the JSON object representing the movie
            JSONObject movieData = movieArrayData.getJSONObject(i);

            movies[i] = new MyParcelable(
                    movieData.getString(Strings.MOVIE_TITLE),
                    movieData.getString(Strings.MOVIE_POSTER),
                    movieData.getString(Strings.MOVIE_OVERVIEW),
                    movieData.getString(Strings.MOVIE_RELEASE_DATE),
                    movieData.getString(Strings.MOVIE_VOTES));



            al.add(new Movies(movieData.getString(Strings.MOVIE_TITLE),
                    movieData.getString(Strings.MOVIE_POSTER),
                    movieData.getString(Strings.MOVIE_OVERVIEW),
                    movieData.getString(Strings.MOVIE_RELEASE_DATE),
                    movieData.getString(Strings.MOVIE_VOTES)));

                Log.d(LOG_TAG, "Movie data :" + i + "\n" +al);



        }
        movieList = new ArrayList<MyParcelable>(Arrays.asList(movies));
        return al;
    }
}
