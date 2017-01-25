package com.udacity.project2.popularmovies.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.project2.popularmovies.BuildConfig;
import com.udacity.project2.popularmovies.R;
import com.udacity.project2.popularmovies.adapter.RecyclerViewReviewAdapter;
import com.udacity.project2.popularmovies.adapter.RecyclerViewTrailerAdapter;
import com.udacity.project2.popularmovies.database.MoviesUtil;
import com.udacity.project2.popularmovies.network.Url;
import com.udacity.project2.popularmovies.parcelable.Movie;
import com.udacity.project2.popularmovies.parcelable.MovieReview;
import com.udacity.project2.popularmovies.parcelable.MovieTrailer;
import com.udacity.project2.popularmovies.parcelable.Result;
import com.udacity.project2.popularmovies.retrofitusedinproject.ApiClient;
import com.udacity.project2.popularmovies.retrofitusedinproject.ApiInterface;
import com.udacity.project2.popularmovies.retrofitusedinproject.MovieResponse;
import com.udacity.project2.popularmovies.retrofitusedinproject.MovieTrailerResponse;

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
 * Created by Dell on 12/22/2016.
 */

public class DetailsFragment extends Fragment {
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
    @BindView(R.id.contentMain)
    LinearLayout contentMain;

    private Unbinder unbinder;
    @BindView(R.id.movieTrailer)
    RecyclerView movieTrailerView;
    String id;
    private ArrayList<Result> result;
    private ArrayList<MovieReview> movieReview;
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

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        intent = getActivity().getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            Log.d("vikas........", "" + id);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        contentMain.setVisibility(rootView.GONE);
        movieTrailerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        movieTrailerView.setLayoutManager(mLinearLayoutManager);
        result = getTrailers(id);


        return rootView;

    }

    // When binding a fragment in onCreateView, set the views to null in onDestroyView.
    // ButterKnife returns an Unbinder on the initial binding that has an unbind method to do this automatically.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

public ArrayList<Result> getTrailers(String id){

    ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);

    Call<MovieTrailerResponse> call = null;

        call = apiService.getMovieTrailers(id,BuildConfig.THE_MOVIE_DB_API_KEY);

    call.enqueue(new Callback<MovieTrailerResponse>() {
        @Override
        public void onResponse(Call<MovieTrailerResponse> call, Response<MovieTrailerResponse> response) {
         result= (ArrayList<Result>) response.body().getResults();
            Log.d("id..........", "" + result);
            if (result != null) {
                trailerAdapter = new RecyclerViewTrailerAdapter(getActivity().getBaseContext(), R.layout.list_item_movie_trailer, result);
                movieTrailerView.setAdapter(trailerAdapter);
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

        @Override
        public void onFailure(Call<MovieTrailerResponse> call, Throwable t) {
            // Log error here since request failed
            Log.e(TAG, t.toString());
        }
    });

        return result;
    }

}
