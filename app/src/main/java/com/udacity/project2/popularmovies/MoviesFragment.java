package com.udacity.project2.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by Dell on 12/15/2016.
 */
public class MoviesFragment extends Fragment {

    private final String Movie_Parse = "v";
    public ProgressBar progressBar;
    private GridView gridView;
    private ArrayList<MyParcelable> movieParcelable;
    private LinearLayout errorLayout;
    private LinearLayout contLayout;

    public MoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (NetworkUtil.isNetworkConnected(getActivity())) {
            if (savedInstanceState == null || !savedInstanceState.containsKey(Movie_Parse)) {
                settings(Url.SORT_POPULAR);
            } else {
                movieParcelable = savedInstanceState.getParcelableArrayList(Movie_Parse);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.moviefragment, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_most_pop) {
            settings(Url.SORT_BY_RATE);
            return true;
        }
        if (id == R.id.action_high_rated) {
            settings(Url.SORT_POPULAR);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void settings(String type) {
        if (NetworkUtil.isNetworkConnected(getActivity())) {
           // 2.call asyncTask
            FetchMoviesData moviesData = new FetchMoviesData();
            moviesData.execute(type);
        } else {
            errorLayout.setVisibility(View.VISIBLE);
            contLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("v", GetMovie.movieParcelable);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //1. Create and set adapter for factoring code put in another java file

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        errorLayout = (LinearLayout) view.findViewById(R.id.error);
        contLayout = (LinearLayout) view.findViewById(R.id.content);
        gridView = (GridView) view.findViewById(R.id.gView);
        progressBar = (SmoothProgressBar) view.findViewById(R.id.progressbar);
        if (movieParcelable != null) {
            setup(movieParcelable);
        }
        if (!NetworkUtil.isNetworkConnected(getActivity())) {
            progressBar.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
            contLayout.setVisibility(View.GONE);
        }


        return view;
    }

    public void setup(final ArrayList<MyParcelable> movies) {
        final GridViewAdapter gridAdapter = new GridViewAdapter(getActivity(), R.layout.moive_grid_item, movies);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyParcelable mo = movies.get(position);

                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("poster", mo.getPoster());
                intent.putExtra("title", mo.getTitle());
                intent.putExtra("overview", mo.getOverview());
                intent.putExtra("date", mo.getRelease_date());
                intent.putExtra("vote", mo.getVote());

                startActivity(intent);
            }
        });
    }

    private class FetchMoviesData extends AsyncTask<String, Void, ArrayList<MyParcelable>> {

        private final String LOG_TAG = FetchMoviesData.class.getSimpleName();

        //1.Display progressbar in onPreExecute
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (progressBar != null) {
                errorLayout.setVisibility(View.GONE);
                contLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            }

        }

        //2.fetch data in doInBackground
        @Override
        protected ArrayList<MyParcelable> doInBackground(String... params) {
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
                Uri builtUri = Uri.parse(Url.BASE_URL).buildUpon()
                        .appendQueryParameter(Url.QUERY_PARAM, params[0])
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
        protected void onPostExecute(final ArrayList<MyParcelable> movies) {
            super.onPostExecute(movies);
            //3.update Adapter and display data using doPost
            setup(movies);
            progressBar.setVisibility(View.GONE);

        }

    }


}
