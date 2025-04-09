package com.example.assignment2_movieapi.viewmodel;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.assignment2_movieapi.model.MovieDetailsModel;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDetailsViewModel extends ViewModel {
    private MutableLiveData<MovieDetailsModel> movieDetails = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private OkHttpClient client = new OkHttpClient();
    private String apiKey = "2c7fcc38";

    public LiveData<MovieDetailsModel> getMovieDetails() {
        return movieDetails;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void fetchMovieDetails(String imdbID) {
        String url = "https://www.omdbapi.com/?i=" + imdbID + "&apikey=" + apiKey;

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("MovieDetailsViewModel", "Network error", e);
                errorMessage.postValue("Network error: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonResponse = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);

                        if (jsonObject.has("Response") && jsonObject.getString("Response").equals("False")) {
                            String error = jsonObject.optString("Error", "Unknown error");
                            errorMessage.postValue("OMDB API Error: " + error);
                            return;
                        }

                        MovieDetailsModel movie = parseMovieDetails(jsonObject);
                        movieDetails.postValue(movie);

                    } catch (JSONException e) {
                        Log.e("MovieDetailsViewModel", "Error parsing JSON", e);
                        errorMessage.postValue("Parsing error: " + e.getMessage());
                    }
                } else {
                    Log.e("MovieDetailsViewModel", "API error: " + response.message());
                    errorMessage.postValue("API error: " + response.message());
                }
            }
        });
    }

    private MovieDetailsModel parseMovieDetails(JSONObject jsonObject) {
        return new MovieDetailsModel(
                jsonObject.optString("Title", "Unknown Title"),
                jsonObject.optString("Rated", "N/A"),
                jsonObject.optString("Released", "N/A"),
                jsonObject.optString("Director", "N/A"),
                jsonObject.optString("Runtime", "N/A"),
                jsonObject.optString("Language", "N/A"),
                jsonObject.optString("Year", "N/A"),
                jsonObject.optString("Plot", "No description available"),
                jsonObject.optString("Poster", null),
                jsonObject.optString("imdbRating", "N/A")
        );
    }
}
