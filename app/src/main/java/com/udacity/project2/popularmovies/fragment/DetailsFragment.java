package com.udacity.project2.popularmovies.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private Unbinder unbinder;
    @BindView(R.id.movieTrailer)
    RecyclerView movieTrailerView;
    String id;
    private ArrayList<MovieTrailer> movieTrailers;
    private ArrayList<MovieReview> movieReview;
    private RecyclerViewReviewAdapter reviewAdapter;
    private RecyclerViewTrailerAdapter trailerAdapter;
    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            Log.d("vikas........",""+id);
            getTrailers(id);
            Log.d("id..........",""+movieTrailers);
            setUpAdapter(movieTrailers);
            title.setText("" + intent.getStringExtra("title"));
            String s = intent.getStringExtra("poster");
            String posterUrl = Url.POSTER_URL + s;
            Picasso.with(getContext()).load(posterUrl)
                    .into(img);
            rate.setText("Rating: " + intent.getDoubleExtra("vote", 0) + "/10");
            date.setText("Release Date: " + intent.getStringExtra("date"));
            overview.setText("Overview: " + intent.getStringExtra("overview"));
        }

        return rootView;

    }

    // When binding a fragment in onCreateView, set the views to null in onDestroyView.
    // ButterKnife returns an Unbinder on the initial binding that has an unbind method to do this automatically.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

public ArrayList<MovieTrailer> getTrailers(String id){

    ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);

    Call<MovieResponse> call = null;

        call = apiService.getMovieTrailers(id,BuildConfig.THE_MOVIE_DB_API_KEY);

    call.enqueue(new Callback<MovieResponse>() {
        @Override
        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
            movieTrailers = (ArrayList<MovieTrailer>) response.body().getTrailerResults();
            Log.d("id..........",""+movieTrailers);
            Log.d(TAG, "server contacted at: " + call.request().url());

        }

        @Override
        public void onFailure(Call<MovieResponse> call, Throwable t) {
            // Log error here since request failed
            Log.e(TAG, t.toString());
            Log.d(TAG, "server contacted at: " + call.request().url());
        }
    });

        return movieTrailers;
    }
    public void setUpAdapter(ArrayList<MovieTrailer> movieTrailers) {
        ArrayList<MovieTrailer.Result> m=null;
        trailerAdapter=new RecyclerViewTrailerAdapter(getActivity(),R.layout.list_item_movie_trailer,m);
        movieTrailerView.setAdapter(trailerAdapter);

    }
}
