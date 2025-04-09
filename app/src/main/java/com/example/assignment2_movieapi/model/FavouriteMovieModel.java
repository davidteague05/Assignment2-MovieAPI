package com.example.assignment2_movieapi.model;

public class FavouriteMovieModel {
        private String title;
        private String director;
        private String poster;
        private String criticsRating;
        private String description;
        private String docId;

        public FavouriteMovieModel(String title, String director, String poster, String criticsRating, String description, String docId) {
            this.title = title;
            this.director = director;
            this.poster = poster;
            this.criticsRating = criticsRating;
            this.description = description;
            this.docId = docId;
        }

        public String getTitle() { return title; }
        public String getDirector() { return director; }
        public String getPosterUrl() { return poster; }
        public String getCriticsRating() { return criticsRating; }
        public String getDescription() { return description; }
        public String getDocId() { return docId; }

}
