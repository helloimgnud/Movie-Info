package com.recflix.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import com.recflix.dto.*;
import com.recflix.service.*;

import java.util.*;

@RestController
public class GetVideoController {

    @Value("${CDN_Api_Key}")
    private String CDN_Api_Key;

    private final MovieDetailService movieDetailService;

    @Autowired
    public GetVideoController(MovieDetailService movieDetailService) {
        this.movieDetailService = movieDetailService;
    }

    @GetMapping("/watch")
    public ResponseEntity<VideoUrlDTO> watchVideo(@RequestParam Long movieId) {
        VideoUrlDTO newUrl = new VideoUrlDTO();
        MovieDetailDTO watchMovie = movieDetailService.getMovieById(movieId);
        movieDetailService.increaseView(movieId);
        String videoUrl = CDN_Api_Key + watchMovie.getMoviePath(); 
        newUrl.setVideoUrl(videoUrl);
        try {
            return ResponseEntity.ok(newUrl);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
