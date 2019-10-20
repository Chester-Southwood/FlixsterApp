package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.activities.DetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.Movie;

import org.parceler.Parcels;

import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context      context;
    private List<Movie>  movieList;

    public MovieAdapter(final Context context, final List<Movie> movieList) {
        this.context    = context;
        this.movieList  = movieList;
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Movie movie = movieList.get(position);
        holder.bind(movie);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         TextView  movieTitle,
                   movieSynopsis;
         ImageView moviePoster;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.movieTitle    = itemView.findViewById(R.id.movieTitle);
            this.movieSynopsis = itemView.findViewById(R.id.movieSynopsis);
            this.moviePoster   = itemView.findViewById(R.id.moviePoster);
            // add listener for parcel to ViewHolder
            itemView.setOnClickListener(this);
        }

        public void bind(final Movie movie) {
            String imageUrl = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? movie.getBackDropPath() : movie.getPosterPath();
            this.movieTitle.setText(movie.getTitle());
            this.movieSynopsis.setText(movie.getOverView());
            Glide.with(context).load(imageUrl).into(this.moviePoster);
            this.movieTitle.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //android.widget.Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent intentDetailedActivity = new Intent(context, DetailActivity.class);
                    intentDetailedActivity.putExtra("title", movie.getTitle());
                    context.startActivity(intentDetailedActivity);
                }
            });
        }

        @Override
        public void onClick(final View view) {
            final int position = getAdapterPosition();
            //Validadate position
            if(position != RecyclerView.NO_POSITION) {
                Movie movieAtPos = movieList.get(position);
                Intent intentDetails = new Intent(context, DetailActivity.class);
                intentDetails.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movieAtPos));
                context.startActivity(intentDetails);
            } else {
                //TODO Make better "catch"
                Toast.makeText(context, "Cannot click here!!!", Toast.LENGTH_LONG);
            }
        }
    }
}