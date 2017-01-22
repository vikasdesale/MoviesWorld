package com.udacity.project2.popularmovies.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 12/21/2016.
 */

public class  Movie implements Parcelable {
    @SerializedName("poster_path") private String posterPath;
    @SerializedName("adult") private boolean adult;
    @SerializedName("overview") private String overview;
    @SerializedName("release_date") private String releaseDate;
    @SerializedName("genre_ids") private List<Integer> genreIds = new ArrayList<Integer>();
    @SerializedName("id") private Integer id;
    @SerializedName("original_title") private String originalTitle;
    @SerializedName("original_language") private String originalLanguage;
    @SerializedName("title") private String title;
    @SerializedName("backdrop_path") private String backdropPath;
    @SerializedName("popularity") private Double popularity;
    @SerializedName("vote_count") private Integer voteCount;
    @SerializedName("video") private Boolean video;
    @SerializedName("vote_average") private Double voteAverage;
    @SerializedName("favourite") boolean favourite = false;

    public Movie(String posterPath, boolean adult, String overview, String releaseDate, List<Integer> genreIds, Integer id,
                 String originalLanguage, String title, String backdropPath, Double popularity,
                 Integer voteCount, Boolean video, Double voteAverage) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.id = id;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
    }
    private Movie(Parcel in){
        this.posterPath=in.readString();
        this.adult         = in.readByte() != 0;
        this.overview  = in.readString();
        this.releaseDate   = in.readString();
        this.genreIds      =  new ArrayList<Integer>();
        in.readList(this.genreIds, List.class.getClassLoader()); ;
        this.id            = in.readInt();
        this.originalLanguage= in.readString();
        this.title         = in.readString();
        this.popularity      = in.readDouble();
        this.voteCount    = in.readInt();
        this.video    = in.readByte()!=0;
        this.voteAverage=in.readDouble();
        this.favourite = in.readByte() != 0;
    }
    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.posterPath);
        parcel.writeByte(this.adult ? (byte) 1 : (byte) 0);
        parcel.writeString(this.overview);
        parcel.writeString(this.releaseDate);
        parcel.writeList(this.genreIds);
        parcel.writeLong(this.id);
        parcel.writeString(this.originalLanguage);
        parcel.writeString(this.title);
        parcel.writeDouble(this.popularity);
        parcel.writeInt(this.voteCount);
        parcel.writeByte(this.video ? (byte) 1 : (byte) 0);
        parcel.writeDouble(this.voteAverage);
        parcel.writeByte(favourite ? (byte) 1 : (byte) 0);


    }

    public final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }

    };

    public boolean isFavourite() {
        return favourite;
    }
    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public class Builder {
    }
}