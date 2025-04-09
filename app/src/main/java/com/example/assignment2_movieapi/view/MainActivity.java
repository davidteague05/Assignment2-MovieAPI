package com.example.assignment2_movieapi.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.assignment2_movieapi.databinding.ActivityMainBinding;
import com.example.assignment2_movieapi.model.MovieModel;
import com.example.assignment2_movieapi.viewmodel.MovieViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickListener {

    private ActivityMainBinding binding;
    FirebaseAuth mAuth;
    private MovieAdapter movieAdapter;
    private List<MovieModel> movieModelList;
    private MovieViewModel movieViewModel;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("movies");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();


        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        movieModelList = new ArrayList<>();
        movieAdapter = new MovieAdapter(movieModelList, this, this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(movieAdapter);

        movieViewModel.getMovieData().observe(this, movies -> {
            movieModelList.clear();
            movieModelList.addAll(movies);
            movieAdapter.notifyDataSetChanged();
        });

        movieViewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

        binding.searchButton.setOnClickListener(v -> {
            String query = binding.searchField.getText().toString().trim();
            if (!query.isEmpty()) {
                movieViewModel.searchMovies(query);
            }
        });

        binding.favouritePageButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavouriteMoviesActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onMovieClick(MovieModel movieModel) {
        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        intent.putExtra("imdbID", movieModel.getImdbID());
        startActivity(intent);
    }
}
