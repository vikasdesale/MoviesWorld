package com.udacity.project2.popularmovies.notuseoldmethodasync;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.udacity.project2.popularmovies.BuildConfig;
import com.udacity.project2.popularmovies.parcelable.Movie;
import com.udacity.project2.popularmovies.network.Url;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Dell on 12/22/2016.
 */

public class FetchMoviesData extends AsyncTask<String, Void, ArrayList<Movie>> {

    private final String LOG_TAG = FetchMoviesData.class.getSimpleName();
    public MovieAsync movieAsync=null;
    public FetchMoviesData(MovieAsync asyncResponse) {
        movieAsync = asyncResponse;//Assigning call back interfacethrough constructor
    }

    //1.Display progressbar in onPreExecute
    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    //2.fetch data in doInBackground
    @Override
    protected ArrayList<Movie> doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        // Will contain the raw JSON response as a string.
        String movieJsonStr = null;
        //MAKING HTTP REQUEST
        if (params.length == 0) {
            return null;
        }

        try {

            //2.1 Building Url for Movies Query
            Uri builtUri = Uri.parse(params[0]).buildUpon()
                    .appendQueryParameter(Url.APPID_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            //2.2 creating request and opening connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            //Reading inputstream into string
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }

            if (stringBuffer.length() == 0) {
                return null;
            }

            movieJsonStr = stringBuffer.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        //2.3Arraylist is returned to onPost Method
        try {
            return GetMovie.getMovieDataFromJson(movieJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(final ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        //3.update Adapter and display data using doPost
          movieAsync.setup(movies);
    }

}