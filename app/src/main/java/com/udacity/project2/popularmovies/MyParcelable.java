package com.udacity.project2.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dell on 12/20/2016.
 */
public class MyParcelable implements Parcelable {
    public final Parcelable.Creator<MyParcelable> CREATOR = new Parcelable.Creator<MyParcelable>() {
        @Override
        public MyParcelable createFromParcel(Parcel parcel) {
            return new MyParcelable(parcel);
        }

        @Override
        public MyParcelable[] newArray(int i) {
            return new MyParcelable[i];
        }

    };
    private String title;
    private String poster;
    private String overview;
    private String release_date;
    private String vote;

    public MyParcelable(String title, String poster, String overview, String release_date, String vote) {
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.release_date = release_date;
        this.vote = vote;
    }

    private MyParcelable(Parcel in) {
        title = in.readString();
        poster = in.readString();
        overview = in.readString();
        release_date = in.readString();
        vote = in.readString();

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return title + "-" + poster + "-" + overview + "-" + release_date + "-" + vote;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeString(vote);
    }
}
