package com.udacity.project2.mymovies.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.udacity.project2.mymovies.R;
import com.udacity.project2.mymovies.anim.AnimationUtils;
import com.udacity.project2.mymovies.interfaces.ColumnsMovies;
import com.udacity.project2.mymovies.network.Url;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 12/19/2016.
 */
public class RecyclerViewAdapter extends CursorRecyclerViewAdapter<RecyclerViewAdapter.ViewHolder> {

    public ClickListener clickListener;
    Cursor mCursor;
    private Context mContext;
    private View view;
    private int mPreviousPosition = 0;

    public RecyclerViewAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext = context;
        mCursor = cursor;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.moive_grid_item, viewGroup, false);

        return new ViewHolder(view);
    }

    public void setClickListener(ClickListener clickListener) {

        this.clickListener = clickListener;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        //DatabaseUtils.dumpCursor(cursor);
        int viewType = getItemViewType(cursor.getPosition());
        Log.d("vikas............","Cursor Value"+cursor.getCount());
        String posterPath = cursor.getString(cursor.getColumnIndex(ColumnsMovies.POSTER_PATH));
        String title = cursor.getString(cursor.getColumnIndex(ColumnsMovies.TITLE));
        viewHolder.imageView.setImageDrawable(null);
        if (posterPath != null || title != null) {
            // viewHolder.imageView.setImageDrawable(null);
            String posterUrl = Url.POSTER_URL + posterPath;

            //Got Advantages why to use Glide over picasso that's why replaced picasso.
            Glide.with(mContext).load(posterUrl)
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.placeholder)
                    // read original from cache (if present) otherwise download it and decode it
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(viewHolder.imageView);
            viewHolder.textView.setText("" + title);
        } else {
            viewHolder.imageView.setImageDrawable(null);
            viewHolder.textView.setText("No Title");
            viewHolder.imageView.setImageResource(R.drawable.placeholder);
        }
        if (viewType > mPreviousPosition) {
            AnimationUtils.animateSunblind(viewHolder, true);

        } else {
            AnimationUtils.animateSunblind(viewHolder, false);

        }
        mPreviousPosition = viewType;
    }


    public interface ClickListener {
        public void itemClicked(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.text)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            //get the view elements
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }
        }
    }


}
