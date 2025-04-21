package com.recflix.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Long genreId;

    @Column(name = "genre_name", nullable = false)
    private String genreName;

    @ManyToMany(mappedBy = "genres")
    private Set<MovieDetail> movies = new HashSet<>();

    // Constructors, Getters, and Setters
    public Genre() {}

    public Genre(String genreName) {
        this.genreName = genreName;
    }

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

    public Set<MovieDetail> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieDetail> movies) {
        this.movies = movies;
    }
}

// package com.recflix.entity;

// import com.recflix.entity.MovieDetail;
// import jakarta.persistence.*;
// import lombok.*;
// import lombok.experimental.FieldDefaults;

// import java.util.Set;

// @Getter
// @Setter
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
// @FieldDefaults(level = AccessLevel.PRIVATE)
// @Entity
// public class Genre {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "genre_id")
//     Long genreId;
    
//     String genreName;

// }