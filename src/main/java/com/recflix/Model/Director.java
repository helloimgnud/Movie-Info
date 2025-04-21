package com.recflix.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "directors")
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_id")
    private Long directorId;

    @Column(name = "director_name", nullable = false)
    private String directorName;

    @ManyToMany(mappedBy = "directors")
    private Set<MovieDetail> movies = new HashSet<>();

    // Constructors
    public Director() {}

    public Director(String directorName) {
        this.directorName = directorName;
    }

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

    public Set<MovieDetail> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieDetail> movies) {
        this.movies = movies;
    }
}

