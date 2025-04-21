package com.recflix.dto;

import java.util.HashSet;
import java.util.Set;

public class DirectorDetailDTO {

    private Long directorId;
    private String directorName;
    private Set<MovieShortDTO> movies = new HashSet<>();

    // Getters and Setters

    public Long getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Long directorId) {
        this.directorId = directorId;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public Set<MovieShortDTO> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieShortDTO> movies) {
        this.movies = movies;
    }
}
