package com.recflix.dto;

import java.util.Set;
import java.util.HashSet;

public class GenreDetailDTO {

    private Long genreId;
    private String genreName;
    private Set<MovieShortDTO> movies = new HashSet<>();

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Set<MovieShortDTO> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieShortDTO> movies) {
        this.movies = movies;
    }
}
