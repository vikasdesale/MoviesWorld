package com.udacity.project2.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;

/**
 * Created by Dell on 12/19/2016.
 */
public class GridViewAdapter extends ArrayAdapter {

    private Context mContext;
    private int resource;
    private ArrayList<MyParcelable> parcel;
    private View grid;
    private LayoutInflater inflater;

    public GridViewAdapter(Context context, int resource,ArrayList<MyParcelable> parcelable) {
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


        if (convertView == null) {
              setGrid(position);

        } else {
            grid = (View) convertView;
        }

        return grid;
    }

    private void setGrid(int position) {
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        grid = new View(mContext);
        grid = inflater.inflate(R.layout.moive_grid_item, null);

        TextView textView = (TextView) grid.findViewById(R.id.text);
        ImageView imageView = (ImageView)grid.findViewById(R.id.image);

        MyParcelable parcelable=parcel.get(position);

        String posterPath = parcelable.getPoster();
        String title=parcelable.getTitle();

        if(posterPath != null||title!=null) {
            String posterUrl = Url.POSTER_URL + posterPath;
            Picasso.with(mContext).load(posterUrl)
                    .into(imageView);
            textView.setText(""+title);
        }
        else {
            textView.setText("No Title");
            imageView.setImageResource(R.drawable.v1);
        }
    }



}
