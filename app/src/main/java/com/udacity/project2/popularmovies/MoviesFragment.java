package com.udacity.project2.popularmovies;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by Dell on 12/15/2016.
 */
public class MoviesFragment extends Fragment {

    GridView grid;
    String[] web = {
            "Google",
            "Github",
            "Instagram",
            "Facebook",
            "Flickr",
            "Pinterest",
            "Quora",
            "Twitter",
            "Vimeo",
            "WordPress",
            "Youtube",
            "Stumbleupon",
            "SoundCloud",
            "Reddit",
            "Blogger"

    } ;
    int[] imageId = {
            R.drawable.v1,
            R.drawable.v2,
            R.drawable.v1,
            R.drawable.v2,
            R.drawable.v1,
            R.drawable.v2,
            R.drawable.v1,
            R.drawable.v2,
            R.drawable.v1,
            R.drawable.v2,
            R.drawable.v1,
            R.drawable.v2,
            R.drawable.v1,
            R.drawable.v2,
            R.drawable.v1

    };
    public MoviesFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.

        //1.check network
        // 2.call asyncTask
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.moviefragment, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_most_pop) {

            return true;
        }
        if (id == R.id.action_high_rated) {

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //1. Create and set adapter for factoring code put in another java file

        View view=inflater.inflate(R.layout.fragment_main,container,false);

        GridView gridView=(GridView)view.findViewById(R.id.gView);
        GridViewAdapter gridAdapter = new GridViewAdapter(getActivity(), R.layout.moive_grid_item,web,imageId);
        gridView.setAdapter(gridAdapter);

        //2.create onclicklistener



        //3.return view


        return view;
    }



}
