package com.udacity.project2.popularmovies.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.udacity.project2.popularmovies.R;
import com.udacity.project2.popularmovies.database.MoviesUtil;
import com.udacity.project2.popularmovies.fragment.DetailsFragment;
import com.udacity.project2.popularmovies.fragment.MoviesFragment;
import com.udacity.project2.popularmovies.interfaces.ColumnsMovies;
import com.udacity.project2.popularmovies.network.NetworkUtil;

/**
 * Created by Dell on 12/15/2016.
 */
public class MainActivity extends AppCompatActivity implements MoviesFragment.CallbackDetails
{
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    private boolean mTwoPane;
    private String mLocation;
    private MoviesUtil moviesUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {

                  getSupportFragmentManager().beginTransaction()
                          .replace(R.id.container, new DetailsFragment(), DETAILFRAGMENT_TAG)
                          .commit();
              }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }

        MoviesFragment moviesFragment =  ((MoviesFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_main));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }





    @Override
    public void onItemSelected(String mId, String mPosterPath, String mTitle, String mOverview, String mDate, String mVoteAverage) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putString("id", mId);
            args.putString("poster", mPosterPath);
            args.putString("title", mTitle);
            args.putString("overview",mOverview);
            args.putString("date", mDate);
            args.putString("vote", mVoteAverage);

            DetailsFragment fragment = new DetailsFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("id", mId);
            intent.putExtra("poster", mPosterPath);
            intent.putExtra("title", mTitle);
            intent.putExtra("overview",mOverview);
            intent.putExtra("date", mDate);
            intent.putExtra("vote", mVoteAverage);
            startActivity(intent);
        }
    }
}