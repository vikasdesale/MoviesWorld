package com.udacity.project2.popularmovies.parcelable;

import android.os.Parcel;

import java.util.ArrayList;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell on 1/25/2017.
 */

public class MovieTrailer1 implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("result")
    @Expose
    private ArrayList<MovieTrailer> result;

    public MovieTrailer1(Parcel source) {
        this.id = source.readInt();
        result=new ArrayList<MovieTrailer>();
        source.readTypedList(result,null);
    }

    public static final Creator<MovieTrailer1> CREATOR = new Creator<MovieTrailer1>() {
        @Override
        public MovieTrailer1 createFromParcel(Parcel in) {
            return new MovieTrailer1(in);
        }

        @Override
        public MovieTrailer1[] newArray(int size) {
            return new MovieTrailer1[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeTypedList(result);
    }
}
