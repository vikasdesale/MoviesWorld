package com.udacity.project2.popularmovies.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 1/24/2017.
 */

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieTrailer implements Serializable, Parcelable
{

    @SerializedName("id")
    private Integer id;
    @SerializedName("results")
    private List<Result> results = null;
    public final static Parcelable.Creator<MovieTrailer> CREATOR = new Creator<MovieTrailer>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MovieTrailer createFromParcel(Parcel in) {
            MovieTrailer instance = new MovieTrailer();
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.results, (com.udacity.project2.popularmovies.parcelable.Result.class.getClassLoader()));
            return instance;
        }

        public MovieTrailer[] newArray(int size) {
            return (new MovieTrailer[size]);
        }

    }
            ;
    private final static long serialVersionUID = 1575653981790866793L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(results);
    }

    public int describeContents() {
        return 0;
    }

}