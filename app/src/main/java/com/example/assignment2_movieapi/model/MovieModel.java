package com.example.assignment2_movieapi.model;

public class MovieModel {
    private String title;
    private String year;
    private String imdbID;
    private String poster;

    public MovieModel(String title, String year, String imdbID, String poster) {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.poster = poster;
    }

    public String getTitle() { return title; }

    public String getYear() { return year; }

    public String getImdbID() {
        return imdbID;
    }

    public String getPoster() {
        return poster;
    }
}