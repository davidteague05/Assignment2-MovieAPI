package com.example.assignment2_movieapi.model;

public class MovieModel {
    private String title;
    private String year;
    private String imdbID;

    public MovieModel(String title, String year, String imdbID) {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
    }

    public String getTitle() { return title; }

    public String getYear() { return year; }

    public String getImdbID() {
        return imdbID;
    }

}