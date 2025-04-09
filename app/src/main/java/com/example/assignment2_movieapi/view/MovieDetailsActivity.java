package com.example.assignment2_movieapi.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.assignment2_movieapi.R;
import com.example.assignment2_movieapi.databinding.MovieDetailsBinding;
import com.example.assignment2_movieapi.model.MovieDetailsModel;
import com.example.assignment2_movieapi.viewmodel.MovieDetailsViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class MovieDetailsActivity extends AppCompatActivity {

    MovieDetailsBinding binding;
    private MovieDetailsViewModel movieDetailsViewModel;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("users");
    private MovieDetailsModel currentMovieDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();

        movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        String imdbID = getIntent().getStringExtra("imdbID");
        if (imdbID != null) {
            movieDetailsViewModel.fetchMovieDetails(imdbID);
        }

        movieDetailsViewModel.getMovieDetails().observe(this, movieDetails -> {
            if (movieDetails != null) {
                currentMovieDetails = movieDetails;
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

        binding.addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavourite();
            }
        });
    }

    private void addToFavourite() {

        collectionReference = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("favourites");
        if (currentMovieDetails != null) {
            Map<String, Object> movie = new HashMap<>();
            movie.put("title", currentMovieDetails.getTitle());
            movie.put("rated", currentMovieDetails.getRated());
            movie.put("released", currentMovieDetails.getReleased());
            movie.put("director", currentMovieDetails.getDirector());
            movie.put("runtime", currentMovieDetails.getRuntime());
            movie.put("language", currentMovieDetails.getLanguage());
            movie.put("year", currentMovieDetails.getYear());
            movie.put("description", currentMovieDetails.getPlot());
            movie.put("posterUrl", currentMovieDetails.getPoster());
            movie.put("criticsRating", currentMovieDetails.getCriticsRating());

            collectionReference.add(movie).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    String documentId = documentReference.getId();
                    Log.d("tag", "Document added with ID: " + documentId);
                }
            });
        }
    }
}
