package com.udacity.project2.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dell on 12/20/2016.
 */
public class MyParcelable implements Parcelable {
    private String title;
    private String poster;
    private String overview;
    private String release_date;
    private String vote;

    public MyParcelable(String title, String poster, String overview, String release_date, String vote)
    {
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.release_date = release_date;
        this.vote = vote;
    }

    private MyParcelable(Parcel in){
        title = in.readString();
        poster = in.readString();
        overview = in.readString();
        release_date = in.readString();
        vote = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() { return title + "--" + poster + "--" + overview; }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeString(vote);
    }

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
}
