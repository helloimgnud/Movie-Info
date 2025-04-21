package com.recflix.dto;

import java.util.Set;
import java.util.HashSet;

public class ActorDetailDTO {

    private Long actorId;
    private String actorName;
    private Set<MovieShortDTO> movies = new HashSet<>();

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public Set<MovieShortDTO> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieShortDTO> movies) {
        this.movies = movies;
    }
}
