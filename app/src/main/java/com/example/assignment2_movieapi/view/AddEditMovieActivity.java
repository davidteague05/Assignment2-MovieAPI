package com.example.assignment2_movieapi.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment2_movieapi.databinding.AddEditMovieBinding;
import com.example.assignment2_movieapi.model.FavouriteMovieModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddEditMovieActivity extends AppCompatActivity {

    private AddEditMovieBinding binding;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddEditMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();

        String docId = getIntent().getStringExtra("docId");
        String criticsRating = getIntent().getStringExtra("criticsRating");
        String description = getIntent().getStringExtra("description");
        String title = getIntent().getStringExtra("title");
        String director = getIntent().getStringExtra("director");
        String posterUrl = getIntent().getStringExtra("posterUrl");

        if (docId != null) {
            binding.titleEditText.setText(title);
            binding.editTextDirector.setText(director);
            binding.editTextPosterUrl.setText(posterUrl);
            binding.editTextCriticsRating.setText(criticsRating);

            binding.titleEditText.setEnabled(false);
            binding.editTextDirector.setEnabled(false);
            binding.editTextPosterUrl.setEnabled(false);
            binding.editTextCriticsRating.setEnabled(false);

            binding.editTextDescription.setText(description);
            binding.buttonSave.setText("Update");

            binding.buttonSave.setOnClickListener(v -> {
                String updatedDescription = binding.editTextDescription.getText().toString();

                db.collection("users")
                        .document(userId)
                        .collection("favourites")
                        .document(docId)
                        .update("description", updatedDescription)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(AddEditMovieActivity.this, "Movie updated", Toast.LENGTH_SHORT).show();
                            finish();
                        });
            });
            binding.buttonDelete.setOnClickListener(v -> {
                db.collection("users")
                        .document(userId)
                        .collection("favourites")
                        .document(docId)
                        .delete()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(AddEditMovieActivity.this, "Movie deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(AddEditMovieActivity.this, "Failed to delete movie", Toast.LENGTH_SHORT).show();
                        });
            });

        } else {
            binding.buttonSave.setText("Add");

            binding.buttonSave.setOnClickListener(v -> {
                String newTitle = binding.titleEditText.getText().toString();
                String newDirector = binding.editTextDirector.getText().toString();
                String newPosterUrl = binding.editTextPosterUrl.getText().toString();
                String newRating = binding.editTextCriticsRating.getText().toString();
                String newDescription = binding.editTextDescription.getText().toString();

                FavouriteMovieModel newMovie = new FavouriteMovieModel(newTitle, newDirector, newPosterUrl, newRating, newDescription, null);

                db.collection("users")
                        .document(userId)
                        .collection("favourites")
                        .add(newMovie)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(AddEditMovieActivity.this, "Movie added", Toast.LENGTH_SHORT).show();
                            finish();
                        });
            });
        }

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
