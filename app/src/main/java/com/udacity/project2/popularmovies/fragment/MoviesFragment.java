package com.udacity.project2.popularmovies.fragment;


import android.content.Intent;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.content.CursorLoader;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.udacity.project2.popularmovies.adapter.RecyclerViewReviewAdapter;
import com.udacity.project2.popularmovies.adapter.RecyclerViewTrailerAdapter;
import com.udacity.project2.popularmovies.database.MoviesProvider;
import com.udacity.project2.popularmovies.database.MoviesUtil;
import com.udacity.project2.popularmovies.interfaces.ColumnsMovies;
import com.udacity.project2.popularmovies.interfaces.ScrollViewExt;
import com.udacity.project2.popularmovies.interfaces.ScrollViewListener;
import com.udacity.project2.popularmovies.parcelable.Movie;
import com.udacity.project2.popularmovies.BuildConfig;
import com.udacity.project2.popularmovies.activities.DetailsActivity;
import com.udacity.project2.popularmovies.network.Url;
import com.udacity.project2.popularmovies.R;
import com.udacity.project2.popularmovies.adapter.RecyclerViewAdapter;
import com.udacity.project2.popularmovies.layout.GridLayoutManagerAutoFit;
import com.udacity.project2.popularmovies.network.NetworkUtil;
import com.udacity.project2.popularmovies.parcelable.MovieReview;
import com.udacity.project2.popularmovies.parcelable.MovieTrailer;
import com.udacity.project2.popularmovies.retrofitusedinproject.ApiClient;
import com.udacity.project2.popularmovies.retrofitusedinproject.ApiInterface;
import com.udacity.project2.popularmovies.retrofitusedinproject.MovieResponse;

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
import static com.udacity.project2.popularmovies.database.MoviesUtil.getCursor;

/**
 * Created by Dell on 12/15/2016.
 */
public class MoviesFragment extends Fragment implements LoaderCallbacks<Cursor>, RecyclerViewAdapter.ClickListener, ScrollViewListener {


    private static final int MOVIE_LOADER = 0;
    private static final String SELECTED_KEY = "selected_position";
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
    private Unbinder unbinder;
    private ArrayList<Movie> movieParcelable;
    private RecyclerViewAdapter gridAdapter;
    private Cursor c;
    private int mPosition = GridView.INVALID_POSITION;

    public MoviesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerView.setHasFixedSize(true);
        ScrollViewExt scroll;
        GridLayoutManagerAutoFit layoutManager = new GridLayoutManagerAutoFit(getContext(), 160);
        recyclerView.setLayoutManager(layoutManager);
        c = getCursor(getActivity());
        if (c != null) {
            progressBar.setVisibility(View.VISIBLE);
            updateScreen(getCursor(getActivity()));

            progressBar2.setVisibility(View.GONE);
        }
        if (!NetworkUtil.isNetworkConnected(getActivity())) {
            progressBar.setVisibility(View.GONE);
            progressBar2.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
            contLayout.setVisibility(View.GONE);
        }

        scroll = (ScrollViewExt) view.findViewById(R.id.scroll);
        scroll.setScrollViewListener(this);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to Listview.INVALID_POSITION,
        // so check for that before storing.
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    public void settings(String type) {

        if (NetworkUtil.isNetworkConnected(getActivity())) {
            //METHOD 1 Retrofit:-

            //This is because internet connection goes down between activities
            if (errorLayout != null || progressBar != null || contLayout != null || progressBar2 != null) {
                errorLayout.setVisibility(View.GONE);
                contLayout.setVisibility(View.VISIBLE);
                progressBar2.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.VISIBLE);
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

        Call<MovieResponse> call = null;
        if (type.equals(Url.SORT_BY_RATE_BASE_URL)) {

            call = apiService.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_KEY);
        } else {
            call = apiService.getPopularMovies(data);

        }
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieParcelable = (ArrayList<Movie>) response.body().getResults();
                TotalPages = response.body().getTotalPages();

                MoviesUtil.insertData(getContext(), movieParcelable);

                c = getCursor(getActivity());
                updateScreen(c);
                progressBar.setVisibility(View.GONE);
                progressBar2.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }


    // When binding a fragment in onCreateView, set the views to null in onDestroyView.
    // ButterKnife returns an Unbinder on the initial binding that has an unbind method to do this automatically.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    public void updateScreen(Cursor c) {
        gridAdapter = new RecyclerViewAdapter(getActivity(), c);
        gridAdapter.setClickListener(this);
        recyclerView.setAdapter(gridAdapter);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        c.close();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        c.close();
    }

    @Override
    public void itemClicked(View view, int position) {
        boolean cursor = c.moveToPosition(position);
        if (cursor) {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("id",c.getString(c.getColumnIndex(ColumnsMovies.KEY)));
            intent.putExtra("poster", c.getString(c.getColumnIndex(ColumnsMovies.POSTER_PATH)));
            intent.putExtra("title", c.getString(c.getColumnIndex(ColumnsMovies.TITLE)));
            intent.putExtra("overview", c.getString(c.getColumnIndex(ColumnsMovies.OVERVIEW)));
            intent.putExtra("date", c.getString(c.getColumnIndex(ColumnsMovies.RELEASE_DATE)));
            intent.putExtra("vote", c.getDouble(c.getColumnIndex(ColumnsMovies.VOTE_AVERAGE)));
            startActivity(intent);
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
            settings(Url.SORT_POPULAR_BASE_URL);
            return true;
        }
        if (id == R.id.action_high_rated) {
            settings(Url.SORT_BY_RATE_BASE_URL);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrollChanged(ScrollViewExt scrollView, int x, int y, int oldx, int oldy) {
        // We take the last son in the scrollview
        View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

        // if diff is zero, then the bottom has been reached
        if (diff == 0) {

            if (NetworkUtil.isNetworkConnected(getActivity())) {
                //METHOD 1 Retrofit:-

                //This is because internet connection goes down between activities
                if (errorLayout != null || progressBar != null || contLayout != null || progressBar2 != null) {
                    errorLayout.setVisibility(View.GONE);
                    contLayout.setVisibility(View.VISIBLE);
                    // do stuff
                    if (pages <= TotalPages)
                        pages = pages + 1;
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                    progressBar2.setVisibility(View.VISIBLE);
                      //                    Log.d("POPULAR MOVIES","Pages..........." + pages);
                    Log.d("POPULAR MOVIES","Total Pages..........." + TotalPages);

                    retro(Url.SORT_POPULAR_BASE_URL, pages);
                } else {
                    Toast.makeText(getContext(), "More Movies Not Available", Toast.LENGTH_SHORT);
                }
                //progressBar.setVisibility(View.VISIBLE);
            } else {
                errorLayout.setVisibility(View.VISIBLE);
                contLayout.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), MoviesProvider.MyMovies.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        gridAdapter.swapCursor(cursor);
        if (mPosition != GridView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            recyclerView.smoothScrollToPosition(mPosition);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        gridAdapter.swapCursor(null);
    }



}
