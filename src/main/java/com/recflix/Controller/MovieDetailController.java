package com.recflix.controller;

import com.recflix.model.*;
import com.recflix.dto.*;
import com.recflix.service.MovieDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/movies")
public class MovieDetailController {

    private final MovieDetailService movieDetailService;

    @Autowired
    public MovieDetailController(MovieDetailService movieDetailService) {
        this.movieDetailService = movieDetailService;
    }

    @GetMapping
    public ResponseEntity<Set<MovieDetailDTO>> getAllMovies() {
        return ResponseEntity.ok(movieDetailService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailDTO> getMovieById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(movieDetailService.getMovieById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/top/view/{N}")
    public ResponseEntity<List<MovieShortDTO>> getTopNByView(@PathVariable Long N) {
        try {
            return ResponseEntity.ok(movieDetailService.getTopNByView(N));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }    

    @GetMapping("/top/rating/{N}")
    public ResponseEntity<List<MovieDetailDTO>> getTopNByRating(@PathVariable Long N) {
        try {
            return ResponseEntity.ok(movieDetailService.getTopNByRating(N));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    } 

    @PostMapping
    public ResponseEntity<MovieDetailDTO> createMovie(@RequestBody MovieDetail movieDetail) {
        return ResponseEntity.ok(movieDetailService.saveMovie(movieDetail));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDetail> updateMovie(@PathVariable Long id, @RequestBody MovieDetail movieDetail) {
        try {
            return ResponseEntity.ok(movieDetailService.updateMovie(id, movieDetail));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieDetailService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
