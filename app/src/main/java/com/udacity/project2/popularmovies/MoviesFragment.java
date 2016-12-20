package com.udacity.project2.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.udacity.project2.popularmovies.R.id.container;
import static com.udacity.project2.popularmovies.R.id.parentPanel;

/**
 * Created by Dell on 12/15/2016.
 */
public class MoviesFragment extends Fragment {

    GridView gridView;
    private ArrayList<MyParcelable> pracelable;
    public MoviesFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.

        //1.check network
        FetchMoviesData moviesData = new FetchMoviesData();
        moviesData.execute(Url.SORT_POPULAR);
        // 2.call asyncTask
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.moviefragment, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_most_pop) {
            FetchMoviesData moviesData = new FetchMoviesData();
            moviesData.execute(Url.SORT_POPULAR);
            return true;
        }
        if (id == R.id.action_high_rated) {
            FetchMoviesData moviesData = new FetchMoviesData();
            moviesData.execute(Url.SORT_BY_RATE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //1. Create and set adapter for factoring code put in another java file

        View view=inflater.inflate(R.layout.fragment_main,container,false);
        gridView = (GridView) view.findViewById(R.id.gView);
        Log.v("Vieew","OUTPUT"+pracelable);

        //if(!=null) {
         //
         //GridViewAdapter gridAdapter = new GridViewAdapter(getActivity(), R.layout.moive_grid_item,);
         //gridView.setAdapter(gridAdapter);

        //2.create onclicklistener



        //3.return view


        return view;
    }

    private class FetchMoviesData extends AsyncTask<String, Void, ArrayList<Movies>> {

        private final String LOG_TAG = FetchMoviesData.class.getSimpleName();

        //1.Display progressbar in onPreExecute
        //2.fetch data in doInBackground
        @Override
        protected ArrayList<Movies> doInBackground(String... params) {
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
                Uri builtUri=Uri.parse(Url.BASE_URL).buildUpon()
                        .appendQueryParameter(Url.QUERY_PARAM,params[0])
                        .appendQueryParameter(Url.APPID_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                //2.2 creating request and opening connection
                urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();



                //Reading inputstream into string
                InputStream inputStream=urlConnection.getInputStream();
                StringBuffer stringBuffer=new StringBuffer();
                if(inputStream==null){
                    return null;
                }

                reader=new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    stringBuffer.append(line + "\n");
                }

                if (stringBuffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

               movieJsonStr=stringBuffer.toString();
               Log.v(LOG_TAG,"OUTPUT"+movieJsonStr);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }finally {
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
            //String [][] with all data is returned to onPost Method
            try {
                return GetMovie.getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }




            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            
        }

        @Override
        protected void onPostExecute(ArrayList<Movies> movies) {
            super.onPostExecute(movies);
            Log.v(LOG_TAG,"MY ............OUTPUT parse"+movies);


                final GridViewAdapter gridAdapter = new GridViewAdapter(getActivity(), R.layout.moive_grid_item, movies);
                gridView.setAdapter(gridAdapter);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
//3.update Adapter and display data using doPost

    }




}
