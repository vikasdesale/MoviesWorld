package com.udacity.project2.mymovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.project2.mymovies.R;
import com.udacity.project2.mymovies.parcelable.MovieReview;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 1/24/2017.
 */

public class RecyclerViewReviewAdapter extends RecyclerView.Adapter<RecyclerViewReviewAdapter.ViewHolder> {


    public ClickListener clickListener;
    private Context mContext;
    private int resource;
    private ArrayList<MovieReview> parcel;
    private View view;
    private LayoutInflater inflater;
    private int mPreviousPosition = 0;


    public RecyclerViewReviewAdapter(Context context, int resource, ArrayList<MovieReview> parcelable) {
        this.resource = resource;
        this.mContext = context;
        this.parcel = parcelable;

    }

    @Override
    public RecyclerViewReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie_review, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//get the data item
        MovieReview parcelable = parcel.get(position);

        String author = parcelable.getAuthor();
        String content = parcelable.getContent();
        if (content != null && author != null) {
            viewHolder.author.setText("" + author);
            viewHolder.content.setText("" + content);
        } else if (content != null && author == null) {
            viewHolder.author.setText("No Author" + author);
            viewHolder.content.setText(" " + content);
        }

    }

    public void setClickListener(ClickListener clickListener) {

        this.clickListener = clickListener;
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.listItemReviewAuthor)
        TextView author;
        @BindView(R.id.listItemReviewContent)
        TextView content;

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
