package com.example.assignment2_movieapi.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2_movieapi.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView movieTitleText, movieYearText;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        movieTitleText = itemView.findViewById(R.id.movieTitle);
        movieYearText = itemView.findViewById(R.id.movieYear);
    }
}
