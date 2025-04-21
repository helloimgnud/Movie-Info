package com.recflix.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MovieDetailDTO {

    private Long movieId;
    private String title;
    private Date releaseDate;
    private Long duration;
    private String description;
    private Double imdbRating;
    private Long view;
    private String posterPath;
    private String moviePath;
    private Set<DirectorShortDTO> directors = new HashSet<>();
    private Set<ActorShortDTO> actors = new HashSet<>();
    private Set<GenreShortDTO> genres = new HashSet<>();

    // Getters and Setters

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public Long getView() {
        return view;
    }

    public void setView(Long view) {
        this.view = view;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getMoviePath() {
        return moviePath;
    }

    public void setMoviePath(String moviePath) {
        this.moviePath = moviePath;
    }

    public Set<DirectorShortDTO> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<DirectorShortDTO> directors) {
        this.directors = directors;
    }

    public Set<ActorShortDTO> getActors() {
        return actors;
    }

    public void setActors(Set<ActorShortDTO> actors) {
        this.actors = actors;
    }

    public Set<GenreShortDTO> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreShortDTO> genres) {
        this.genres = genres;
    }
}
