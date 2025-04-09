package com.example.assignment2_movieapi.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assignment2_movieapi.databinding.FavouriteMoviesBinding;
import com.example.assignment2_movieapi.model.FavouriteMovieModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMoviesActivity extends AppCompatActivity {
    private FavouriteMoviesBinding binding;
    private FavouriteMovieAdapter favouriteMovieAdapter;

    private List<FavouriteMovieModel> favouriteMovies = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FavouriteMoviesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        CollectionReference collectionReference = db.collection("users").document(userId).collection("favourites");


        favouriteMovieAdapter = new FavouriteMovieAdapter(favouriteMovies, this, (movie, position) -> {
            Intent intent = new Intent(FavouriteMoviesActivity.this, AddEditMovieActivity.class);
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("director", movie.getDirector());
            intent.putExtra("posterUrl", movie.getPosterUrl());
            intent.putExtra("criticsRating", movie.getCriticsRating());
            intent.putExtra("description", movie.getDescription());
            intent.putExtra("docId", movie.getDocId());
            intent.putExtra("editable", true);
            startActivity(intent);
        });
        binding.recyclerView.setAdapter(favouriteMovieAdapter);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.searchPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                favouriteMovies.clear();
                for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                    String title = doc.getString("title");
                    String director = doc.getString("director");
                    String posterUrl = doc.getString("posterUrl");
                    String criticsRating = doc.getString("criticsRating");
                    String description = doc.getString("description");
                    String docId = doc.getId();
                    FavouriteMovieModel movie = new FavouriteMovieModel(title, director, posterUrl, criticsRating, description, docId);

                    favouriteMovies.add(movie);
                }
                favouriteMovieAdapter.notifyDataSetChanged();
            }
        });

        binding.addEditPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FavouriteMoviesActivity.this, AddEditMovieActivity.class);
                startActivity(intent);
            }
        });

    }

}
