package com.udacity.project2.popularmovies.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 1/24/2017.
 */

public class MovieTrailer implements Parcelable {


    public static final Parcelable.Creator<MovieTrailer> CREATOR = new Parcelable.Creator<MovieTrailer>() {
        @Override
        public MovieTrailer createFromParcel(Parcel source) {
            return new MovieTrailer(source);
        }

        @Override
        public MovieTrailer[] newArray(int size) {
            return new MovieTrailer[size];
        }
    };
    @SerializedName("id")
    private double Id;
    @SerializedName("results")
    private List<Result> results;

    @Override
    public int describeContents() {
        return 0;
    }
    public MovieTrailer(Parcel source) {
        this.Id = source.readDouble();
        this.results=source.readArrayList(ClassLoader.getSystemClassLoader());
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
          parcel.writeDouble(Id);
        parcel.writeList(results);
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Double getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public static class Result implements Parcelable {
     public Result(String mId, String mLanguage, String mLanguage2, String mName, String mKey, Integer mSize, String mSite, String mType) {
         this.mId = mId;
         this.mLanguage = mLanguage;
         this.mLanguage2 = mLanguage2;
         this.mName = mName;
         this.mKey = mKey;
         this.mSize = mSize;
         this.mSite = mSite;
         this.mType = mType;
     }


     @SerializedName("id")
     private String mId;
     @SerializedName("iso_639_1")
     private String mLanguage;
     @SerializedName("iso_3166_1")
     private String mLanguage2;
     @SerializedName("key")
     private String mKey;
     @SerializedName("name")
     private String mName;
     @SerializedName("site")
     private String mSite;
     @SerializedName("size")
     private Integer mSize;
     @SerializedName("type")
     private String mType;

     public Result(Parcel source) {
         this.mId = source.readString();
         this.mLanguage = source.readString();
         this.mLanguage2 = source.readString();
         this.mKey = source.readString();
         this.mName = source.readString();
         this.mSite = source.readString();
         this.mSize = source.readInt();
         this.mType = source.readString();
     }

     public static final Creator<Result> CREATOR = new Creator<Result>() {
         @Override
         public Result createFromParcel(Parcel in) {
             return new Result(in);
         }

         @Override
         public Result[] newArray(int size) {
             return new Result[size];
         }
     };

     @Override
     public void writeToParcel(Parcel parcel, int i) {
         parcel.writeString(mId);
         parcel.writeString(mLanguage);
         parcel.writeString(mLanguage2);
         parcel.writeString(mKey);
         parcel.writeString(mName);
         parcel.writeString(mSite);
         parcel.writeInt(mSize);
         parcel.writeString(mType);
     }

     @Override
     public String toString() {
         return "MovieTrailer{" +
                 "mId='" + mId + '\'' +
                 ", mLanguage='" + mLanguage + '\'' +
                 ", mLanguage2='" + mLanguage2 + '\'' +
                 ", mKey='" + mKey + '\'' +
                 ", mName='" + mName + '\'' +
                 ", mSite='" + mSite + '\'' +
                 ", mSize=" + mSize +
                 ", mType='" + mType + '\'' +
                 '}';
     }

     @Override
     public int describeContents() {
         return 0;
     }

     public String getID() {
         return mId;
     }

     public void setID(String mId) {
         this.mId = mId;
     }

     public String getLanguage() {
         return mLanguage;
     }

     public void setLanguage(String mLanguage) {
         this.mLanguage = mLanguage;
     }

     public String getKey() {
         return mKey;
     }

     public String getmLanguage2() {
         return mLanguage2;
     }

     public void setmLanguage2(String mLanguage2) {
         this.mLanguage2 = mLanguage2;
     }

     public void setKey(String mKey) {
         this.mKey = mKey;
     }

     public String getName() {
         return mName;
     }

     public void setName(String mName) {
         this.mName = mName;
     }

     public String getSite() {
         return mSite;
     }

     public void setSite(String mSite) {
         this.mSite = mSite;
     }



        public Integer getSize() {
         return mSize;
     }

     public void setSize(Integer mSize) {
         this.mSize = mSize;
     }

     public String getType() {
         return mType;
     }

     public void setType(String mType) {
         this.mType = mType;
     }
 }

}
