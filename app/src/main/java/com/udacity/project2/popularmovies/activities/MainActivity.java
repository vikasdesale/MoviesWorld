package com.udacity.project2.popularmovies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udacity.project2.popularmovies.R;
import com.udacity.project2.popularmovies.fragment.MoviesFragment;

/**
 * Created by Dell on 12/15/2016.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MoviesFragment())
                    .commit();
        }
    }
}
