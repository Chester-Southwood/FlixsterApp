package com.example.myapplication.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel //Used for serialization
public class Movie {
    //Non-Private for Parcel
    String backDropPath;
    String posterPath;
    String title;
    String overView;
    double voteAverage;
    Integer id;

    // DVC required by Parceler
    public Movie() {

    }

    public Movie(JSONObject obj) throws JSONException {
          this.backDropPath = obj.getString("backdrop_path");
          this.posterPath   = obj.getString("poster_path");
          this.title        = obj.getString("title");
          this.overView     = obj.getString("overview");
          this.voteAverage  = Double.parseDouble(obj.getString("vote_average"));
          this.id           = Integer.parseInt(obj.getString("id"));
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/" + posterPath);
    }

    public String getBackDropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/" + backDropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverView() {
        return overView;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getMovieId() {
        return id;
    }

    public static List<Movie> fromJSONArray(JSONArray movieJSONArray) throws JSONException {
        List<Movie> movies = new ArrayList<Movie>();
        for(int index = 0; index < movieJSONArray.length(); index++)
            movies.add(new Movie(movieJSONArray.getJSONObject(index)));
        return movies;
    }
}
