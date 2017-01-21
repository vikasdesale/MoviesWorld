package com.udacity.project2.popularmovies;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Dell on 12/20/2016.
 */
public class DetailsActivity extends AppCompatActivity {

    private static TextView title;
    private static TextView date;
    private static TextView rate;
    private static TextView overview;
    private static ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailsFragment())
                    .commit();
        }
    }

    public static class DetailsFragment extends Fragment {
        public DetailsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_details, container, false);
            initialize(rootView);
            setData();

            return rootView;
        }


        private void initialize(View rootView) {

            title = (TextView) rootView.findViewById(R.id.title);
            date = (TextView) rootView.findViewById(R.id.rdate);
            rate = (TextView) rootView.findViewById(R.id.rate);
            overview = (TextView) rootView.findViewById(R.id.overview);
            img = (ImageView) rootView.findViewById(R.id.imageView);
        }

        private void setData() {

            Intent intent = getActivity().getIntent();
            if (intent != null) {
                title.setText("" + intent.getStringExtra("title"));
                String s = intent.getStringExtra("poster");
                String posterUrl = Url.POSTER_URL + s;
                Picasso.with(getContext()).load(posterUrl)
                        .into(img);
                rate.setText("Rating: " + intent.getStringExtra("vote") + "/10");
                date.setText("Release Date: " + intent.getStringExtra("date"));
                overview.setText("Overview: " + intent.getStringExtra("overview"));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
