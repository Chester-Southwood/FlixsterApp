package com.example.myapplication.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel //Used for serialization
public class Movie {

    private int    id;
    private double voteAverage;
    private String backDropPath;
    private String overView;
    private String posterPath;
    private String title;

    // DVC required by Parceler
    public Movie() {

    }

    public Movie(JSONObject obj) throws JSONException {
        this.backDropPath = obj.getString("backdrop_path");
        this.id           = Integer.parseInt(obj.getString("id"));
        this.overView     = obj.getString("overview");
        this.posterPath   = obj.getString("poster_path");
        this.title        = obj.getString("title");
        this.voteAverage  = Double.parseDouble(obj.getString("vote_average"));
    }

    public String getBackDropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backDropPath);
    }

    public int getMovieId() {
        return id;
    }

    public String getOverView() {
        return overView;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public static List<Movie> fromJSONArray(JSONArray movieJSONArray) throws JSONException {
        List<Movie> movieList = new ArrayList<Movie>();
        for(int index = 0; index < movieJSONArray.length(); index++) {
            movieList.add(new Movie(movieJSONArray.getJSONObject(index)));
        }
        return movieList;
    }
}
