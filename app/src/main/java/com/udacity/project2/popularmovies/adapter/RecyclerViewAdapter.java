package com.udacity.project2.popularmovies.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;
import com.udacity.project2.popularmovies.R;
import com.udacity.project2.popularmovies.interfaces.ColumnsMovies;
import com.udacity.project2.popularmovies.network.Url;
import com.udacity.project2.popularmovies.anim.AnimationUtils;
import com.udacity.project2.popularmovies.parcelable.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 12/19/2016.
 */
public class RecyclerViewAdapter extends CursorRecyclerViewAdapter<RecyclerViewAdapter.ViewHolder>  {

    private Context mContext;
    private int resource;
    private ArrayList<Movie> parcel;
    private View view;
    private LayoutInflater inflater;
    public  ClickListener clickListener;
    private int mPreviousPosition = 0;
    Cursor mCursor;


  /*  public RecyclerViewAdapter(Context context, int resource, ArrayList<Movie> parcelable) {
        this.resource=resource;
        this.mContext=context;
        this.parcel=parcelable;

    }
*/
    public RecyclerViewAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext=context;
        mCursor=cursor;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
         view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.moive_grid_item, viewGroup, false);

        return new ViewHolder(view);
    }



    /*@Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //get the data item
        Movie parcelable = parcel.get(position);

        String posterPath = parcelable.getPosterPath();
        String title = parcelable.getTitle();
        viewHolder.imageView.setImageDrawable (null);
        if (posterPath != null || title != null) {
           // viewHolder.imageView.setImageDrawable(null);
            String posterUrl = Url.POSTER_URL + posterPath;
            Picasso.with(mContext).load(posterUrl)
                    .into(viewHolder.imageView);
            viewHolder.textView.setText("" + title);
        } else {
            viewHolder.imageView.setImageDrawable(null);
            viewHolder.textView.setText("No Title");
            viewHolder.imageView.setImageResource(R.drawable.v1);
        }
        if (position > mPreviousPosition) {
            AnimationUtils.animateSunblind(viewHolder, true);

        } else {
            AnimationUtils.animateSunblind(viewHolder, false);

        }
        mPreviousPosition = position;

    }
*/

   public void setClickListener(ClickListener clickListener){

        this.clickListener=clickListener;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        //DatabaseUtils.dumpCursor(cursor);
        int viewType = getItemViewType(cursor.getPosition());
        Log.d("Cursor..............",""+cursor);

        Log.d("vikas..............",""+viewHolder);
        String posterPath = cursor.getString(cursor.getColumnIndex(ColumnsMovies.POSTER_PATH));
        String title =cursor.getString(cursor.getColumnIndex(ColumnsMovies.TITLE));
        viewHolder.imageView.setImageDrawable (null);
        if (posterPath != null || title != null) {
            // viewHolder.imageView.setImageDrawable(null);
            String posterUrl = Url.POSTER_URL + posterPath;
            Picasso.with(mContext).load(posterUrl)
                    .into(viewHolder.imageView);
            viewHolder.textView.setText("" + title);
        } else {
            viewHolder.imageView.setImageDrawable(null);
            viewHolder.textView.setText("No Title");
            viewHolder.imageView.setImageResource(R.drawable.v1);
        }
       if (viewType > mPreviousPosition) {
            AnimationUtils.animateSunblind(viewHolder, true);

        } else {
            AnimationUtils.animateSunblind(viewHolder, false);

        }
        mPreviousPosition = viewType;
    }






    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.image) ImageView imageView;
        @BindView(R.id.text)  TextView textView;

    public ViewHolder(View itemView) {
        super(itemView);
        //get the view elements
        ButterKnife.bind(this, view);
        itemView.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {

        if(clickListener!=null){
            clickListener.itemClicked(v,getPosition());
        }
    }
}
    public  interface ClickListener{
        public void itemClicked(View view,int position);
    }


}
