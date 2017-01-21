package com.udacity.project2.popularmovies.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.udacity.project2.popularmovies.BuildConfig;
import com.udacity.project2.popularmovies.activities.DetailsActivity;
import com.udacity.project2.popularmovies.network.Url;
import com.udacity.project2.popularmovies.parcelable.Movie;
import com.udacity.project2.popularmovies.R;
import com.udacity.project2.popularmovies.adapter.RecyclerViewAdapter;
import com.udacity.project2.popularmovies.layout.GridLayoutManagerAutoFit;
import com.udacity.project2.popularmovies.network.NetworkUtil;
import com.udacity.project2.popularmovies.retrofitusedinproject.ApiClient;
import com.udacity.project2.popularmovies.retrofitusedinproject.ApiInterface;
import com.udacity.project2.popularmovies.retrofitusedinproject.MovieResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Dell on 12/15/2016.
 */
public class MoviesFragment extends Fragment implements RecyclerViewAdapter.ClickListener {


    private final String Movie_Parse = "v";
    @BindView(R.id.progressbar) ProgressBar progressBar;
    @BindView(R.id.error) LinearLayout errorLayout;
    @BindView(R.id.content) LinearLayout contLayout;
    @BindView(R.id.card_recycler_view) RecyclerView recyclerView;
    private Unbinder unbinder;
    private ArrayList<Movie> movieParcelable;

    public MoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (NetworkUtil.isNetworkConnected(getActivity())) {
            if (savedInstanceState == null || !savedInstanceState.containsKey(Movie_Parse) ||
                    savedInstanceState.getParcelableArrayList(Movie_Parse) == null) {
                settings(Url.SORT_POPULAR_BASE_URL);
            } else {
                movieParcelable = savedInstanceState.getParcelableArrayList(Movie_Parse);
            }
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerView.setHasFixedSize(true);
        GridLayoutManagerAutoFit layoutManager = new GridLayoutManagerAutoFit(getContext(), 160);
        recyclerView.setLayoutManager(layoutManager);
        if (movieParcelable != null) {
            updateScreen(movieParcelable);
            progressBar.setVisibility(View.GONE);
        }
        if (!NetworkUtil.isNetworkConnected(getActivity())) {
            progressBar.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
            contLayout.setVisibility(View.GONE);
        }


        return view;
    }

    public void settings(String type) {

        if (NetworkUtil.isNetworkConnected(getActivity())) {
            //METHOD 1 Retrofit:-

            //This is because internet connection goes down between activities
            if(errorLayout!=null||progressBar!=null||contLayout!=null) {
                errorLayout.setVisibility(View.GONE);
                contLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            }

                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);

            Call<MovieResponse> call=null;
            if (type.equals(Url.SORT_BY_RATE_BASE_URL)) {

                call = apiService.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_KEY);
            }else{
                call = apiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_KEY);

            }
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    movieParcelable = (ArrayList<Movie>) response.body().getResults();
                    //Log.d(TAG, "Number of movies received: " + movieParcelable.size()+""+movieParcelable.get(0));
                    updateScreen(movieParcelable);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });


            // METHOD 2.call asyncTask
            /*FetchMoviesData moviesData=new FetchMoviesData(new MovieAsync() {
                @Override
                public void setup(final ArrayList<Movie> movies) {
                  movieParcelable=movies;
                    updateScreen(movies);
                    progressBar.setVisibility(View.GONE);
                }
            });
            moviesData.execute(type);*/
        } else {
            errorLayout.setVisibility(View.VISIBLE);
            contLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("v", movieParcelable);
        super.onSaveInstanceState(outState);
    }



    // When binding a fragment in onCreateView, set the views to null in onDestroyView.
    // ButterKnife returns an Unbinder on the initial binding that has an unbind method to do this automatically.
    /*@Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }*/

    public void updateScreen(final ArrayList<Movie> movies) {
        final RecyclerViewAdapter gridAdapter = new RecyclerViewAdapter(getActivity(), R.layout.moive_grid_item, movies);
        gridAdapter.setClickListener(this);
        recyclerView.setAdapter(gridAdapter);

    }

    @Override
    public void itemClicked(View view, int position) {
        Movie mo = movieParcelable.get(position);
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("poster", mo.getPosterPath());
        intent.putExtra("title", mo.getTitle());
        intent.putExtra("overview", mo.getOverview());
        intent.putExtra("date", mo.getReleaseDate());
        intent.putExtra("vote", mo.getVoteAverage());

        startActivity(intent);
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
            settings(Url.SORT_POPULAR_BASE_URL);
            return true;
        }
        if (id == R.id.action_high_rated) {
            settings(Url.SORT_BY_RATE_BASE_URL);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
