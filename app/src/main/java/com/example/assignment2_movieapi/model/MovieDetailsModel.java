package com.example.assignment2_movieapi.model;

public class MovieDetailsModel {
    private String title;
    private String rated;
    private String released;
    private String director;
    private String runtime;
    private String language;
    private String year;
    private String plot;
    private String poster;
    private String criticsRating;

    public MovieDetailsModel(String title, String rated, String released, String director,
                             String runtime, String language, String year, String plot, String poster, String criticsRating) {
        this.title = title;
        this.rated = rated;
        this.released = released;
        this.director = director;
        this.runtime = runtime;
        this.language = language;
        this.year = year;
        this.plot = plot;
        this.poster = poster;
        this.criticsRating = criticsRating;
    }

    public String getTitle() { return title; }
    public String getRated() { return rated; }
    public String getReleased() { return released; }
    public String getDirector() { return director; }
    public String getRuntime() { return runtime; }
    public String getLanguage() { return language; }
    public String getYear() { return year; }
    public String getPlot() { return plot; }
    public String getPoster() { return poster; }
    public String getCriticsRating() { return criticsRating; }

}
