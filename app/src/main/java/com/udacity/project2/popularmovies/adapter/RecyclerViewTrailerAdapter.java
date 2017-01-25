package com.udacity.project2.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.project2.popularmovies.R;
import com.udacity.project2.popularmovies.parcelable.MovieTrailerResults;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 1/24/2017.
 */

public class RecyclerViewTrailerAdapter extends  RecyclerView.Adapter<RecyclerViewTrailerAdapter.ViewHolder>{


    private Context mContext;
    private int resource;
    private ArrayList<MovieTrailerResults> parcel;
    private View view;
    private LayoutInflater inflater;
    public ClickListener clickListener;
    private int mPreviousPosition = 0;
    int i=0;

    public RecyclerViewTrailerAdapter(Context context, int resource, ArrayList<MovieTrailerResults> parcelable) {
        this.resource=resource;
        this.mContext=context;
        this.parcel=parcelable;

    }

    @Override
    public RecyclerViewTrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie_trailer, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//get the data item
      MovieTrailerResults parcelable = parcel.get(position);
        viewHolder.imageView.setImageResource (android.R.drawable.ic_media_play);
        viewHolder.textView.setText(parcelable.getName());
    }

    public void setClickListener(RecyclerViewTrailerAdapter.ClickListener clickListener){

        this.clickListener=clickListener;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return parcel.size();
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.trailerIcon)
        ImageView imageView;
        @BindView(R.id.trailerName)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            //get the view elements
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {

            //if(clickListener!=null){
                Log.d("Vikas................","Item Clicked"+getPosition());

                clickListener.itemClicked(v,getPosition());
                int d=getPosition();
//            }
        }
    }

}
