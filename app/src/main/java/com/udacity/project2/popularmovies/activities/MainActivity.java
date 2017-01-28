package com.udacity.project2.popularmovies.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.udacity.project2.popularmovies.R;
import com.udacity.project2.popularmovies.fragment.DetailsFragment;
import com.udacity.project2.popularmovies.fragment.MoviesFragment;
import com.udacity.project2.popularmovies.interfaces.ColumnsMovies;

/**
 * Created by Dell on 12/15/2016.
 */
public class MainActivity extends AppCompatActivity implements MoviesFragment.CallbackDetails
{
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    private boolean mTwoPane;
    private String mLocation;

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

        MoviesFragment forecastFragment =  ((MoviesFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_main));
        forecastFragment.setUsePopularMoviesLayout(!mTwoPane);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
         //   startActivity(new Intent(this, SettingsActivity.class));
          //  return true;
       // }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
     /*   String location = Utility.getPreferredLocation( this );
        // update the location in our second pane using the fragment manager
        if (location != null && !location.equals(mLocation)) {
            ForecastFragment ff = (ForecastFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_forecast);
            if ( null != ff ) {
                ff.onLocationChanged();
            }
            DetailFragment df = (DetailFragment)getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
            if ( null != df ) {
                df.onLocationChanged(location);
            }
            mLocation = location;
        }*/
    }




    @Override
    public void onItemSelected(String mId, String mPosterPath, String mTitle, String mOverview, String mDate, double mVoteAverage) {
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
            args.putDouble("vote", mVoteAverage);
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