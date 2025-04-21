package com.recflix.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "actors")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private Long actorId;

    @Column(name = "actor_name", nullable = false)
    private String actorName;

    @ManyToMany(mappedBy = "actors")
    private Set<MovieDetail> movies = new HashSet<>();

    public Actor() {}

    public Actor(String actorName) {
        this.actorName = actorName;
    }

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

    public Set<MovieDetail> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieDetail> movies) {
        this.movies = movies;
    }
}
