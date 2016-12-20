package com.udacity.project2.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.squareup.picasso.Picasso;


/**
 * Created by Dell on 12/19/2016.
 */
public class GridViewAdapter extends ArrayAdapter {

    private static final String LOG_TAG ="GridView" ;
    private Context mContext;
    private int resource;
    private ArrayList<Movies> parcel;

    public GridViewAdapter(Context context, int resource,ArrayList<Movies> parcelable) {
        super(context, resource);
        this.resource=resource;
        this.mContext=context;
        this.parcel=parcelable;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return parcel.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.moive_grid_item, null);
            TextView textView = (TextView) grid.findViewById(R.id.text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.image);

            Movies paracelable=parcel.get(position);

            String posterPath = paracelable.getPoster();
            String title=paracelable.getTitle();

            if(posterPath != null||title!=null) {

                String posterUrl = Url.POSTER_URL + posterPath;
                Log.d(LOG_TAG, "poster url : " + posterUrl);

                Picasso.with(mContext).load(posterUrl)
                        .into(imageView);

                textView.setText(""+title);
            }
            else {
                 textView.setText("No Title");
                 imageView.setImageResource(R.drawable.v1);
            }

        } else {
            grid = (View) convertView;
        }

        return grid;
    }



}
