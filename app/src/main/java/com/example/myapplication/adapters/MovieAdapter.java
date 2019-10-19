package com.example.myapplication.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Movie;

import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context      context;
    private List<Movie>  moviesList;

    public MovieAdapter(Context context, List<Movie> moviesList) {
        this.context    = context;
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView  movieTitle,
                          movieSynopsis;
        private ImageView moviePoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.movieTitle    = itemView.findViewById(R.id.movieTitle);
            this.movieSynopsis = itemView.findViewById(R.id.movieSynopsis);
            this.moviePoster   = itemView.findViewById(R.id.moviePoster);
        }

        public void bind(Movie movie) {
            String imageUrl = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? movie.getBackDropPath() : movie.getPosterPath();
            this.movieTitle.setText(movie.getTitle());
            this.movieSynopsis.setText(movie.getOverView());
            Glide.with(context).load(imageUrl).into(this.moviePoster);
        }
    }

}
