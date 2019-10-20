package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.myapplication.R;
import com.example.myapplication.adapters.MovieAdapter;
import com.example.myapplication.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<Movie>();

        RecyclerView rvMovies = findViewById(R.id.rvMovie);
        final MovieAdapter movieAdapter = new MovieAdapter(this, movieList);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        final AsyncHttpClient client = new AsyncHttpClient();

        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObj = json.jsonObject;
                try{
                    JSONArray resultArr = jsonObj.getJSONArray("results");
                    Log.i(TAG, "Results: " + resultArr.toString());
                    movieList.addAll(Movie.fromJSONArray(resultArr));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: " + movieList.size());
                } catch(JSONException e) {
                    Log.e(TAG, "Hit JSON Exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure"); //TODO Handle failure scenario
            }
        });
    }
}
