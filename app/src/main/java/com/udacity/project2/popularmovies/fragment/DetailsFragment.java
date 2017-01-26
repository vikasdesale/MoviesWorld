package com.udacity.project2.popularmovies.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.project2.popularmovies.BuildConfig;
import com.udacity.project2.popularmovies.R;
import com.udacity.project2.popularmovies.activities.DetailsActivity;
import com.udacity.project2.popularmovies.adapter.RecyclerViewAdapter;
import com.udacity.project2.popularmovies.adapter.RecyclerViewReviewAdapter;
import com.udacity.project2.popularmovies.adapter.RecyclerViewTrailerAdapter;
import com.udacity.project2.popularmovies.network.NetworkUtil;
import com.udacity.project2.popularmovies.network.Url;
import com.udacity.project2.popularmovies.parcelable.MovieReview;
import com.udacity.project2.popularmovies.parcelable.MovieTrailerResults;
import com.udacity.project2.popularmovies.retrofitusedinproject.ApiClient;
import com.udacity.project2.popularmovies.retrofitusedinproject.ApiInterface;
import com.udacity.project2.popularmovies.retrofitusedinproject.MovieReviewResponse;
import com.udacity.project2.popularmovies.retrofitusedinproject.MovieTrailerResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Dell on 12/22/2016.
 */

public class DetailsFragment extends Fragment implements RecyclerViewTrailerAdapter.ClickListener {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rdate)
    TextView date;
    @BindView(R.id.rate)
    TextView rate;
    @BindView(R.id.overview)
    TextView overview;
    @BindView(R.id.imageView)
    ImageView img;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.progressContent)
    LinearLayout progressContent;
    private ShareActionProvider mShareActionProvider;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // If onLoadFinished happens before this, we can go ahead and set the share intent now.
        if ( != null) {
            mShareActionProvider.setShareIntent(createShareMovieIntent());
        }
    }

    private Intent createShareMovieIntent() {
    }

    @BindView(R.id.contentMain)
    LinearLayout contentMain;
    private Unbinder unbinder;
    @BindView(R.id.movieReview)
    RecyclerView movieReviewView;
    @BindView(R.id.movieTrailer)
    RecyclerView movieTrailerView;

    String id;
    private ArrayList<MovieTrailerResults> movieTrailerResults;
    private ArrayList<MovieReview> movieReviewResults;
    private RecyclerViewReviewAdapter reviewAdapter;
    private RecyclerViewTrailerAdapter trailerAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private Intent intent;
    View rootView;
    public DetailsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (NetworkUtil.isNetworkConnected(getActivity())) {
            if (savedInstanceState == null || !savedInstanceState.containsKey("trailers") ||
                    savedInstanceState.getParcelableArrayList("trailers") == null||
                    !savedInstanceState.containsKey("reviews") ||
                    savedInstanceState.getParcelableArrayList("reviews") == null) {
               getTrailers(id);

            } else{
                movieTrailerResults = savedInstanceState.getParcelableArrayList("trailers");
                movieReviewResults = savedInstanceState.getParcelableArrayList("reviews");
            }

       }



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        intent = getActivity().getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        contentMain.setVisibility(rootView.GONE);
        setLayoutManager(movieTrailerView);
        setLayoutManager(movieReviewView);
        if(movieTrailerResults !=null && movieReviewResults !=null){
            setData(movieTrailerResults,movieReviewResults);
        }
        return rootView;

    }

    void setLayoutManager(RecyclerView view){
        view.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        view.setLayoutManager(mLinearLayoutManager);
    }
    // When binding a fragment in onCreateView, set the views to null in onDestroyView.
    // ButterKnife returns an Unbinder on the initial binding that has an unbind method to do this automatically.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("trailers", movieTrailerResults);
        outState.putParcelableArrayList("reviews", movieReviewResults);

        super.onSaveInstanceState(outState);
    }
    public void setData(ArrayList<MovieTrailerResults> movieTrailerResults,ArrayList<MovieReview> movieReviews) {
        if (movieTrailerResults != null && movieReviews !=null) {
            trailerAdapter = new RecyclerViewTrailerAdapter(getActivity().getBaseContext(), R.layout.list_item_movie_trailer, movieTrailerResults);
            trailerAdapter.setClickListener(this);
            movieTrailerView.setAdapter(trailerAdapter);
            reviewAdapter = new RecyclerViewReviewAdapter(getActivity().getBaseContext(), R.layout.list_item_movie_trailer, movieReviews);
            movieReviewView.setAdapter(reviewAdapter);
            title.setText("" + intent.getStringExtra("title"));
            String s = intent.getStringExtra("poster");
            String posterUrl = Url.POSTER_URL + s;
            Picasso.with(getContext()).load(posterUrl)
                    .into(img);
            rate.setText("Rating: " + intent.getDoubleExtra("vote", 0) + "/10");
            date.setText("Release Date: " + intent.getStringExtra("date"));
            overview.setText("Overview: " + intent.getStringExtra("overview"));
            contentMain.setVisibility(rootView.VISIBLE);
            progressBar.setVisibility(rootView.GONE);

        }

    }



    public void getTrailers(final String id) {
        if (NetworkUtil.isNetworkConnected(getActivity())) {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<MovieTrailerResponse> call = null;

            call = apiService.getMovieTrailers(id, BuildConfig.THE_MOVIE_DB_API_KEY);

            call.enqueue(new Callback<MovieTrailerResponse>() {
                @Override
                public void onResponse(Call<MovieTrailerResponse> call, Response<MovieTrailerResponse> response) {
                    movieTrailerResults = (ArrayList<MovieTrailerResults>) response.body().getMovieTrailerResults();
                    Log.d("id..........", "" + movieTrailerResults);
                    getReviews(id, movieTrailerResults);
                }

                @Override
                public void onFailure(Call<MovieTrailerResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });

        }
    }
    public void getReviews(String id, final ArrayList<MovieTrailerResults> movieTrailers){
        if (NetworkUtil.isNetworkConnected(getActivity())) {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<MovieReviewResponse> call = null;

            call = apiService.getMovieReviews(id, BuildConfig.THE_MOVIE_DB_API_KEY);

            call.enqueue(new Callback<MovieReviewResponse>() {
                @Override
                public void onResponse(Call<MovieReviewResponse> call, Response<MovieReviewResponse> response) {
                    movieReviewResults= (ArrayList<MovieReview>) response.body().getMovieReviews();
                    setData(movieTrailers,movieReviewResults);
                }

                @Override
                public void onFailure(Call<MovieReviewResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });

        }

    }


    @Override
    public void itemClicked(View view, int position) {
        Toast.makeText(getContext(),"Hello Check Internet Connection and Try again...",Toast.LENGTH_SHORT);
        MovieTrailerResults movieTrailersClick=movieTrailerResults.get(position);
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" +movieTrailersClick.getKey()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" +movieTrailersClick.getKey()));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}
