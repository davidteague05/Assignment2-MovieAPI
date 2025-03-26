package com.example.assignment2_movieapi.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assignment2_movieapi.model.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<List<MovieModel>> movieData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private OkHttpClient client = new OkHttpClient();
    private String apiKey = "2c7fcc38";

    public LiveData<List<MovieModel>> getMovieData() {
        return movieData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void searchMovies(String searchTerm) {
        String url = "https://www.omdbapi.com/?s=" + searchTerm + "&type=movie&apikey=" + apiKey;

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
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

                        List<MovieModel> movies = parseJsonResponse(jsonObject);
                        movieData.postValue(movies);

                    } catch (JSONException e) {
                        Log.e("MovieViewModel", "Error parsing JSON", e);
                        errorMessage.postValue("Parsing error: " + e.getMessage());
                    }
                } else {
                    Log.e("MovieViewModel", "API error: " + response.message());
                    errorMessage.postValue("API error: " + response.message());
                }
            }
        });
    }

    private List<MovieModel> parseJsonResponse(JSONObject jsonObject) throws JSONException {
        List<MovieModel> movies = new ArrayList<>();
        if (!jsonObject.has("Search")) return movies;

        JSONArray searchArray = jsonObject.getJSONArray("Search");

        for (int i = 0; i < searchArray.length(); i++) {
            JSONObject movieObject = searchArray.getJSONObject(i);

            String title = movieObject.optString("Title", "Unknown Title");
            String year = movieObject.optString("Year", "Unknown Year");
            String imdbID = movieObject.optString("imdbID", null);

            movies.add(new MovieModel(title, year, imdbID));
        }
        return movies;
    }
}
