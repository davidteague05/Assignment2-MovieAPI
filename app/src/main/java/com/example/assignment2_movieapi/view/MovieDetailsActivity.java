package com.example.assignment2_movieapi.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.assignment2_movieapi.R;
import com.example.assignment2_movieapi.databinding.MovieDetailsBinding;
import com.example.assignment2_movieapi.viewmodel.MovieDetailsViewModel;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    MovieDetailsBinding binding;
    private MovieDetailsViewModel movieDetailsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        String imdbID = getIntent().getStringExtra("imdbID");
        if (imdbID != null) {
            movieDetailsViewModel.fetchMovieDetails(imdbID);
        }

        movieDetailsViewModel.getMovieDetails().observe(this, movieDetails -> {
            if (movieDetails != null) {
                binding.titleTextView.setText(movieDetails.getTitle());
                binding.ratedTextView.setText("Rated: " + movieDetails.getRated());
                binding.releaseTextView.setText("Release: " + movieDetails.getReleased());
                binding.directorTextView.setText("Director: " + movieDetails.getDirector());
                binding.runtimeTextView.setText("Runtime: " + movieDetails.getRuntime());
                binding.languageTextView.setText("Language: " + movieDetails.getLanguage());
                binding.yearTextView.setText("Year: " + movieDetails.getYear());
                binding.descriptionTextView.setText(movieDetails.getPlot());

                //from chat gpt
                Picasso.get()
                        .load(movieDetails.getPoster())
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(binding.posterImageView);
            }
        });

        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
