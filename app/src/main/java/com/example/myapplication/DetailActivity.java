package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.myapplication.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class DetailActivity extends YouTubeBaseActivity {

    Movie movie;

    TextView movieTitle;
    TextView movieSynopsis;
    RatingBar ratingBar;

    final String YOUTUBE_API_BASE_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=%s";
    static final String YOUTUBE_API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        movieTitle = (TextView) findViewById(R.id.movieTitle);
        movieSynopsis = (TextView) findViewById(R.id.movieSynopsis);
        ratingBar = (RatingBar) findViewById(R.id.rbVoteAverage);
        youTubePlayerView = findViewById(R.id.player);

        //Unwrap movie via intent, using key/value pairing. The simple name of Movie is its own key.
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieAdapter 2", Movie.class.getSimpleName());

        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set the title and overview
        movieTitle.setText(movie.getTitle());
        movieSynopsis.setText(movie.getOverView());

        // vote average is 0..10, convert to 0..5 by dividing by 2
        ratingBar.setRating((float)movie.getVoteAverage());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(YOUTUBE_API_BASE_URL, movie.getMovieId(), YOUTUBE_API_KEY), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray resultJSONArray = json.jsonObject.getJSONArray("results");
                    if(resultJSONArray.length() == 0) {
                        Log.d("Empty Array", "resultJSONArray is empty");
                        //need to handle better
                    } else {
                        String youtubeKey = resultJSONArray.getJSONObject(0).getString("key");
                        Log.d("DetailActivity", youtubeKey);
                        initializeYouTube(youtubeKey);
                    }
                } catch(JSONException e) {
                    Log.e("DetailActivity", "Failed to parse JSON", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });
    }

    private void initializeYouTube(final String youtubeKey){
        // initialize with API key stored in secrets.xml
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                // do any work here to cue video, play video, etc.
                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                // log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });
    }
}
