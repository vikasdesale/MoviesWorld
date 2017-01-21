package com.udacity.project2.popularmovies.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.project2.popularmovies.R;
import com.udacity.project2.popularmovies.network.Url;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Dell on 12/22/2016.
 */

public class DetailsFragment extends Fragment {
    @BindView(R.id.title) TextView title;
    @BindView(R.id.rdate) TextView date;
    @BindView(R.id.rate) TextView rate;
    @BindView(R.id.overview) TextView overview;
    @BindView(R.id.imageView) ImageView img;
    private Unbinder unbinder;

    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setData();

        return rootView;
    }

    // When binding a fragment in onCreateView, set the views to null in onDestroyView.
    // ButterKnife returns an Unbinder on the initial binding that has an unbind method to do this automatically.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void setData() {

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            title.setText("" + intent.getStringExtra("title"));
            String s = intent.getStringExtra("poster");
            String posterUrl = Url.POSTER_URL + s;
            Picasso.with(getContext()).load(posterUrl)
                    .into(img);
            rate.setText("Rating: " + intent.getDoubleExtra("vote", 0) + "/10");
            date.setText("Release Date: " + intent.getStringExtra("date"));
            overview.setText("Overview: " + intent.getStringExtra("overview"));
        }
    }
}
