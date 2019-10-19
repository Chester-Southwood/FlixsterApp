package com.example.myapplication.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String backDropPath;
    private String posterPath;
    private String title;
    private String overView;

    public Movie(JSONObject obj) throws JSONException {
          this.backDropPath = obj.getString("backdrop_path");
          this.posterPath   = obj.getString("poster_path");
          this.title        = obj.getString("title");
          this.overView     = obj.getString("overview");
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

    public static List<Movie> fromJSONArray(JSONArray movieJSONArray) throws JSONException {
        List<Movie> movies = new ArrayList<Movie>();
        for(int index = 0; index < movieJSONArray.length(); index++) {
            movies.add(new Movie(movieJSONArray.getJSONObject(index)));
        }
        return movies;
    }
}
