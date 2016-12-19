package com.udacity.project2.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.data;
import static android.R.attr.imageButtonStyle;

/**
 * Created by Dell on 12/19/2016.
 */
public class GridViewAdapter extends ArrayAdapter {

    private Context mContext;
    private int resource;
    private int imageId[];
    private String web[];
    public GridViewAdapter(Context context, int resource, String[] web, int[] imageId) {
        super(context, resource);
        this.resource=resource;
        this.mContext=context;
        this.imageId=imageId;
        this.web=web;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.length;
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
            textView.setText(web[position]);
            imageView.setImageResource(imageId[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }



}
