package com.example.assignment2_movieapi.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2_movieapi.R;
import com.example.assignment2_movieapi.model.FavouriteMovieModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouriteMovieAdapter extends RecyclerView.Adapter<FavouriteMovieAdapter.MovieViewHolder> {

    private List<FavouriteMovieModel> movies;
    private Context context;
    private OnMovieClickListener listener;

    public FavouriteMovieAdapter(List<FavouriteMovieModel> movies, Context context, OnMovieClickListener listener) {
        this.movies = movies;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favourite_movies_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        FavouriteMovieModel movie = movies.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieDirector.setText("Director: " + movie.getDirector());
        holder.criticsRating.setText("imdbRating: " + movie.getCriticsRating());
        holder.itemView.setOnClickListener(v -> {
            listener.onMovieClick(movie, holder.getAdapterPosition());
        });

        Picasso.get()
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle, movieDirector, criticsRating;
        ImageView moviePoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieDirector = itemView.findViewById(R.id.movieDirector);
            criticsRating = itemView.findViewById(R.id.criticsRating);
            moviePoster = itemView.findViewById(R.id.moviePoster);
        }
    }
    public interface OnMovieClickListener {
        void onMovieClick(FavouriteMovieModel movie, int position);
    }


}
