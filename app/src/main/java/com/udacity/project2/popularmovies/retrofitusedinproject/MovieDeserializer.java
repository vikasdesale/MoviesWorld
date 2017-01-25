package com.udacity.project2.popularmovies.retrofitusedinproject;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.udacity.project2.popularmovies.parcelable.MovieTrailer;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Dell on 1/25/2017.
 */

public class MovieDeserializer implements JsonDeserializer<MovieTrailer> {

    private static final String RESULT = "result";
    private static final String ID = "id";
    private static final String LANG1 = "iso_639_1";
    private static final String LANG2 = "iso_3166_1";
    private static final String KEY = "key";
    private static final String NAME = "name";
    private static final String SITE = "site";
    private static final String SIZE = "size";
    private static final String TYPE = "type";


    @Override
    public MovieTrailer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        MovieTrailer m=null;
        JsonArray movieT = jsonObject.get(RESULT).getAsJsonArray();
        ArrayList<MovieTrailer> list = new ArrayList<MovieTrailer>();
        Log.d("vikas..................", "" + movieT);
        try {
            ArrayList<MovieTrailer> yourArray = new Gson().fromJson(movieT.toString(), (Type) new ArrayList<MovieTrailer>());
            for (int i = 0, l = movieT.size(); i < l; i++) {
                //   list.add(movieT.get(i).getAsJsonObject());
            }
        } catch (Exception e) {
        }

     return m;
    }

}
