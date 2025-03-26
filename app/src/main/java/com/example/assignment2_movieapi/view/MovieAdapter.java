package com.example.assignment2_movieapi.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2_movieapi.R;
import com.example.assignment2_movieapi.model.MovieModel;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<MovieModel> movieModelList;
    private Context context;
    private OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onMovieClick(MovieModel movieModel);
    }

    public MovieAdapter(List<MovieModel> movieModelList, Context context, OnMovieClickListener listener) {
        this.movieModelList = movieModelList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieModel movieModel = movieModelList.get(position);
        holder.bind(movieModel, listener);
    }

    @Override
    public int getItemCount() {
        return movieModelList.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle, movieYear;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieYear = itemView.findViewById(R.id.movieYear);
        }

        public void bind(final MovieModel movieModel, final OnMovieClickListener listener) {
            movieTitle.setText(movieModel.getTitle());
            movieYear.setText("Year: " + movieModel.getYear());

            itemView.setOnClickListener(v -> listener.onMovieClick(movieModel));
        }
    }
}
