package com.recflix.service;

import com.recflix.model.*;
import com.recflix.dto.*;
import com.recflix.repository.*;
import com.recflix.Exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

@Service
public class MovieDetailService {

    private final DirectorRepository directorRepository;

    private final DirectorService directorService;

    private final ActorRepository actorRepository;

    private final ActorService actorService;

    private final GenreRepository genreRepository;

    private final GenreService genreService;

    private final MovieDetailRepository movieDetailRepository;

    private final ConvertDTOService converter;

    @Autowired
    public MovieDetailService(DirectorRepository directorRepository,
                               DirectorService directorService,
                               ActorRepository actorRepository,
                               ActorService actorService,
                               GenreRepository genreRepository,
                               GenreService genreService,
                               MovieDetailRepository movieDetailRepository,
                               ConvertDTOService converter) {
        this.directorRepository = directorRepository;
        this.directorService = directorService;
        this.actorRepository = actorRepository;
        this.actorService = actorService;
        this.genreRepository = genreRepository;
        this.genreService = genreService;
        this.movieDetailRepository = movieDetailRepository;
        this.converter = converter;
    }

    public Set<MovieDetailDTO> getAllMovies() {
        Set<MovieDetailDTO> movieDTOs = new HashSet<>();
        movieDetailRepository.findAll().forEach(movie -> {
            movieDTOs.add(converter.convertToDTO(movie));
        });
        return movieDTOs;
    }

    public MovieDetailDTO getMovieById(Long id) {
        MovieDetail returnMovie = movieDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phim có id: " + id));
        return converter.convertToDTO(returnMovie);
    }

    public List<MovieShortDTO> getTopNByView(Long N) {
        int n = N.intValue();
        List<MovieShortDTO> result = new ArrayList<>();
        List<MovieDetail> topMovies = movieDetailRepository.findTopMovies(n);
        for (MovieDetail movie : topMovies) {
            result.add(converter.convertToShort(movie));
        }
        return result;
    }

    public List<MovieDetailDTO> getTopNByRating(Long N) {
        int n = N.intValue();
        List<MovieDetailDTO> result = new ArrayList<>();
        List<MovieDetail> topMovies = movieDetailRepository.findTopRatingMovies(n);
        for (MovieDetail movie : topMovies) {
            result.add(converter.convertToDTO(movie));
        }
        return result;
    }

    public MovieDetailDTO saveMovie(MovieDetail movieDetail) {
        Set<Director> processedDirectors = new HashSet<>();
        Set<Actor> processedActors = new HashSet<>();
        Set<Genre> processedGenres = new HashSet<>();

        for (Director director : movieDetail.getDirectors()) {
            Optional<Director> existingDirector = directorRepository.findByDirectorName(director.getDirectorName());
            if (existingDirector.isPresent()) {
                processedDirectors.add(existingDirector.get());
            } else {
                processedDirectors.add(directorRepository.save(director));
            }
        }

        for (Actor actor : movieDetail.getActors()) {
            Optional<Actor> existingActor = actorRepository.findByActorName(actor.getActorName());
            if (existingActor.isPresent()) {
                processedActors.add(existingActor.get());
            } else {
                processedActors.add(actorRepository.save(actor));
            }
        }

        for (Genre genre : movieDetail.getGenres()) {
            Optional<Genre> existingGenre = genreRepository.findByGenreName(genre.getGenreName());
            if (existingGenre.isPresent()) {
                processedGenres.add(existingGenre.get());
            } else {
                processedGenres.add(genreRepository.save(genre));
            }
        }

        movieDetail.setDirectors(processedDirectors);
        movieDetail.setActors(processedActors);
        movieDetail.setGenres(processedGenres);
        return converter.convertToDTO(movieDetailRepository.save(movieDetail));
    }

    public void increaseView(Long id) {
        movieDetailRepository.findById(id).map(existingMovie -> {
            existingMovie.setView(existingMovie.getView() + 1);
            return movieDetailRepository.save(existingMovie);
        }).orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    public MovieDetail updateMovie(Long id, MovieDetail movieDetail) {
        return movieDetailRepository.findById(id).map(existingMovie -> {
            existingMovie.setTitle(movieDetail.getTitle());
            existingMovie.setReleaseDate(movieDetail.getReleaseDate());
            existingMovie.setDuration(movieDetail.getDuration());
            existingMovie.setDescription(movieDetail.getDescription());
            existingMovie.setImdbRating(movieDetail.getImdbRating());
            existingMovie.setView(movieDetail.getView());
            existingMovie.setPosterPath(movieDetail.getPosterPath());
            existingMovie.setMoviePath(movieDetail.getMoviePath());
            existingMovie.setDirectors(movieDetail.getDirectors());
            existingMovie.setActors(movieDetail.getActors());
            existingMovie.setGenres(movieDetail.getGenres());
            return movieDetailRepository.save(existingMovie);
        }).orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    public void deleteMovie(Long id) {
        movieDetailRepository.deleteById(id);
    }
}
