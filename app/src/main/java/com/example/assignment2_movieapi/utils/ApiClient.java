package com.example.assignment2_movieapi.utils;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ApiClient {
    private static final OkHttpClient client = new OkHttpClient();

    // Fix: Use MediaType.get() instead of MediaType.parse()
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    // GET Request Method
    public static void get(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    // POST Request Method
    public static void post(String url, String jsonBody, Callback callback) {
        RequestBody body = RequestBody.create(jsonBody, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}