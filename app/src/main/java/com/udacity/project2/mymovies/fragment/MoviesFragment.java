package com.udacity.project2.mymovies.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.udacity.project2.mymovies.BuildConfig;
import com.udacity.project2.mymovies.R;
import com.udacity.project2.mymovies.adapter.RecyclerViewAdapter;
import com.udacity.project2.mymovies.database.MoviesUtil;
import com.udacity.project2.mymovies.interfaces.ScrollViewExt;
import com.udacity.project2.mymovies.interfaces.ScrollViewListener;
import com.udacity.project2.mymovies.network.NetworkUtil;
import com.udacity.project2.mymovies.network.Url;
import com.udacity.project2.mymovies.parcelable.Movie;
import com.udacity.project2.mymovies.retrofit.ApiClient;
import com.udacity.project2.mymovies.retrofit.ApiInterface;
import com.udacity.project2.mymovies.retrofit.MovieResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.udacity.project2.mymovies.database.MoviesUtil.CacheDelete;


/**
 * Created by Dell on 12/15/2016.
 */
public class MoviesFragment extends Fragment implements RecyclerViewAdapter.ClickListener, ScrollViewListener {


    private static final String SELECTED_KEY = "selected_position";
    private static int favflag = 1;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.progressbar2)
    ProgressBar progressBar2;
    @BindView(R.id.error)
    LinearLayout errorLayout;
    @BindView(R.id.content)
    LinearLayout contLayout;
    @BindView(R.id.card_recycler_view)
    RecyclerView recyclerView;
    int pages = 1;
    int TotalPages;
    MoviesUtil movies;
    private Unbinder unbinder;
    private ArrayList<Movie> movieParcelable;
    private RecyclerViewAdapter gridAdapter;
    private int mPosition = GridView.INVALID_POSITION;
    private GridLayoutManager layoutManager;
    private ScrollViewExt scroll;
    private MoviesLoader mMoviesLoader;

    public MoviesFragment() {
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface CallbackDetails {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */

       public void onItemSelected(String mId, String mPosterPath, String mTitle, String mOverview, String mDate, String mVoteAverage);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movies = new MoviesUtil();
        setRetainInstance(true);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        MoviesCheck();
        scroll = (ScrollViewExt) view.findViewById(R.id.scroll);
        scroll.setScrollViewListener(this);
        return view;
    }

    private void MoviesCheck() {
        //checking for movies in temporary database
        if (movies.getAllMoviesCount(getActivity()) != 0) {
            allMovieWindow();
        } else if (movies.getFavMoviesCount(getActivity()) != 0) {
            openFavorite();
        } else if (movies.getAllMoviesCount(getActivity()) == 0 && movies.getAllMoviesCount(getActivity()) == 0) {
            progressBar2.setVisibility(View.GONE);
            if (!NetworkUtil.isNetworkConnected(getActivity())) {
                progressBar.setVisibility(View.GONE);
                progressBar2.setVisibility(View.GONE);
                errorLayout.setVisibility(View.VISIBLE);
                contLayout.setVisibility(View.GONE);
            } else {
                settings(Url.SORT_POPULAR_BASE_URL);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMoviesLoader != null)
            mMoviesLoader.restartLoader();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mPosition != GridView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.moviefragment, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favourite) {
            openFavorite();
            return true;
        }
        if (id == R.id.action_most_pop) {
            favflag = 0;
            pages = 1;
            settings(Url.SORT_POPULAR_BASE_URL);
            return true;
        }
        if (id == R.id.action_high_rated) {
            favflag = 0;
            pages = 1;
            settings(Url.SORT_BY_RATE_BASE_URL);
            return true;
        }   if (id == R.id.action_upcoming) {
            favflag = 0;
            pages = 1;
            settings(Url.SORT_UPCOMING_BASE_URL);
            return true;
        }
        if (id == R.id.action_now_playing) {
            favflag = 0;
            pages = 1;
            settings(Url.SORT_NOW_PLAYING_BASE_URL);
            return true;
        }  if (id == R.id.action_about_us) {
            Intent i=new Intent("com.udacity.project2.mymovies.activities.AboutUs.class");
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Load All Movies from temporary database
    private void allMovieWindow() {
        favflag = 0;
        Cursor allm = null;
        try {
            allm = movies.allMoviesCursor(getActivity());
            if (progressBar != null)
                progressBar.setVisibility(View.GONE);
            updateScreen(allm);

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        /*finally {if (allm != null || !allm.isClosed()) {allm.close(); }}*/
    }

    //Method for loading Favorite Movies from database
    private void openFavorite() {

        Cursor c2 = null;
        try {
            c2 = movies.favoriteMoviesCursor(getActivity());
            CacheDelete(getContext());
            if (c2.getCount() == 0) {
                Toast.makeText(getContext(), "Currently You have not any Favorite Movie...Add it!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Favorite Movies", Toast.LENGTH_SHORT).show();
                errorLayout.setVisibility(View.GONE);
                contLayout.setVisibility(View.VISIBLE);
                updateScreen(c2);
                favflag = 1;
            }
        } catch (Exception e) {
        }//  finally {   if (c2 != null || !c2.isClosed()) {    c2.close(); }}// if comment is out not shows movies
    }


    public void settings(String type) {

        if (NetworkUtil.isNetworkConnected(getActivity())) {
            //deletes all rows from temporary database.
            CacheDelete(getContext());
            if (errorLayout != null || progressBar != null || contLayout != null || progressBar2 != null) {
                errorLayout.setVisibility(View.GONE);
                contLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            }

            retro(type, pages);
        } else {
            errorLayout.setVisibility(View.VISIBLE);
            contLayout.setVisibility(View.GONE);
        }
    }

    private void retro(String type, int page) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Map<String, String> data = new HashMap<>();
        data.put("page", String.valueOf(page));
        data.put("api_key", BuildConfig.THE_MOVIE_DB_API_KEY);
        favflag = 0;
        Call<MovieResponse> call = null;
        if (type.equals(Url.SORT_BY_RATE_BASE_URL)) {

            call = apiService.getTopRatedMovies(data);
        } else if (type.equals(Url.SORT_UPCOMING_BASE_URL)) {
            call = apiService.getUpcomingMovies(data);

        }else if (type.equals(Url.SORT_NOW_PLAYING_BASE_URL)) {
            call = apiService.getUpcomingMovies(data);
        }
        else{
            call = apiService.getPopularMovies(data);

        }
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieParcelable = (ArrayList<Movie>) response.body().getResults();
                TotalPages = response.body().getTotalPages();

                movies.insertData(getContext(), movieParcelable, "no");
                allMovieWindow();
                if (progressBar != null)
                    progressBar.setVisibility(View.GONE);
                if (progressBar2 != null)
                    progressBar2.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    public void updateScreen(Cursor c) {
        gridAdapter = new RecyclerViewAdapter(getActivity(), c);
        mMoviesLoader = mMoviesLoader.newInstance(favflag, this, gridAdapter);
        gridAdapter = new RecyclerViewAdapter(getActivity(), c);
        mMoviesLoader.initLoader();
        gridAdapter.setClickListener(this);

        if (recyclerView != null)
            recyclerView.setAdapter(gridAdapter);
        if (mMoviesLoader != null)
            mMoviesLoader.restartLoader();

        if (progressBar != null || progressBar2 != null) {
            progressBar.setVisibility(View.GONE);
            progressBar2.setVisibility(View.GONE);
        }

    }


    @Override
    public void itemClicked(View view, int position) {
        Cursor onClick = null;
        try {
            if (favflag == 1) {
                onClick = movies.favoriteMoviesCursor(getActivity());
            } else {
                onClick = movies.allMoviesCursor(getActivity());
            }
            boolean cursor = onClick.moveToPosition(position);
            if (cursor) {
                  String args[]=movies.getData(onClick);
                ((CallbackDetails) getActivity())
                        .onItemSelected(args[0],args[1],args[2],args[3],args[4],args[5]);
            }
        } catch (Exception e) {
        }/*finally {if (onClick != null || !onClick.isClosed()) {onClick.close();}    }*/
    }


    //Method for all scroll end load movies of next page.
    @Override
    public void onScrollChanged(ScrollViewExt scrollView, int x, int y, int oldx, int oldy) {
        View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

        // if diff is zero, then the bottom has been reached
        if (diff == 0 && favflag == 0) {

            if (NetworkUtil.isNetworkConnected(getActivity())) {
                if (errorLayout != null || progressBar != null || contLayout != null || progressBar2 != null) {
                    errorLayout.setVisibility(View.GONE);
                    contLayout.setVisibility(View.VISIBLE);
                    if (pages <= TotalPages)
                        pages = pages + 1;
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                    progressBar2.setVisibility(View.VISIBLE);
                    retro(Url.SORT_POPULAR_BASE_URL, pages);
                } else {
                    Toast.makeText(getContext(), "More Movies Not Available", Toast.LENGTH_SHORT);
                }
            } else {
                errorLayout.setVisibility(View.VISIBLE);
                contLayout.setVisibility(View.GONE);
            }

        }

    }

    public void setUsePopularMoviesLayout(boolean usePopularMoviesLayout) {
       // this.usePopularMoviesLayout = usePopularMoviesLayout;
    }
}