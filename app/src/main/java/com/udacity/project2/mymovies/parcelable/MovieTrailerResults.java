package com.udacity.project2.mymovies.parcelable;

/**
 * Created by Dell on 1/25/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MovieTrailerResults implements Serializable, Parcelable {

    public final static Parcelable.Creator<MovieTrailerResults> CREATOR = new Creator<MovieTrailerResults>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MovieTrailerResults createFromParcel(Parcel in) {
            MovieTrailerResults instance = new MovieTrailerResults();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.iso6391 = ((String) in.readValue((String.class.getClassLoader())));
            instance.iso31661 = ((String) in.readValue((String.class.getClassLoader())));
            instance.key = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.site = ((String) in.readValue((String.class.getClassLoader())));
            instance.size = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.type = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public MovieTrailerResults[] newArray(int size) {
            return (new MovieTrailerResults[size]);
        }

    };
    private final static long serialVersionUID = 5334739784928957913L;
    @SerializedName("id")
    private String id;
    @SerializedName("iso_639_1")
    private String iso6391;
    @SerializedName("iso_3166_1")
    private String iso31661;
    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;
    @SerializedName("site")
    private String site;
    @SerializedName("size")
    private Integer size;
    @SerializedName("type")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(iso6391);
        dest.writeValue(iso31661);
        dest.writeValue(key);
        dest.writeValue(name);
        dest.writeValue(site);
        dest.writeValue(size);
        dest.writeValue(type);
    }

    public int describeContents() {
        return 0;
    }

}
