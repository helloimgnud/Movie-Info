package com.recflix.service;

import com.recflix.model.*;
import com.recflix.dto.*;
import com.recflix.repository.*;
import com.recflix.Exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConvertDTOService {

    public MovieDetailDTO convertToDTO(MovieDetail movie) {

        MovieDetailDTO movieDTO = new MovieDetailDTO();
        movieDTO.setMovieId(movie.getMovieId());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setReleaseDate(movie.getReleaseDate());
        movieDTO.setDuration(movie.getDuration());
        movieDTO.setDescription(movie.getDescription());
        movieDTO.setImdbRating(movie.getImdbRating());
        movieDTO.setView(movie.getView());
        movieDTO.setPosterPath(movie.getPosterPath());
        movieDTO.setMoviePath(movie.getMoviePath());

        Set<DirectorShortDTO> directorDTOs = new HashSet<>();
        for (Director director : movie.getDirectors()) {
            DirectorShortDTO directorDTO = convertToShort(director);
            directorDTOs.add(directorDTO);
        }
        movieDTO.setDirectors(directorDTOs);

        Set<ActorShortDTO> actorDTOs = new HashSet<>();
        for (Actor actor : movie.getActors()) {
            ActorShortDTO actorDTO = convertToShort(actor);
            actorDTOs.add(actorDTO);
        }
        movieDTO.setActors(actorDTOs);

        Set<GenreShortDTO> genreDTOs = new HashSet<>();
        for (Genre genre : movie.getGenres()) {
            GenreShortDTO genreDTO = convertToShort(genre);
            genreDTOs.add(genreDTO);
        }
        movieDTO.setGenres(genreDTOs);

        return movieDTO;
    }

    public GenreDetailDTO convertToDTO(Genre genre) {

        GenreDetailDTO genreDTO = new GenreDetailDTO();

        genreDTO.setGenreId(genre.getGenreId());
        genreDTO.setGenreName(genre.getGenreName());

        Set<MovieShortDTO> movieDTOs = new HashSet<>();
        for (MovieDetail movie : genre.getMovies()) {
            MovieShortDTO movieDTO = convertToShort(movie);
            movieDTOs.add(movieDTO);
        }

        genreDTO.setMovies(movieDTOs);

        return genreDTO;
    }

    public DirectorDetailDTO convertToDTO(Director director) {

        DirectorDetailDTO directorDTO = new DirectorDetailDTO();

        directorDTO.setDirectorId(director.getDirectorId());
        directorDTO.setDirectorName(director.getDirectorName());

        Set<MovieShortDTO> movieDTOs = new HashSet<>();
        for (MovieDetail movie : director.getMovies()) {
            MovieShortDTO movieDTO = convertToShort(movie);
            movieDTOs.add(movieDTO);
        }

        directorDTO.setMovies(movieDTOs);

        return directorDTO;
    }

    public ActorDetailDTO convertToDTO(Actor actor) {

        ActorDetailDTO actorDTO = new ActorDetailDTO();

        actorDTO.setActorId(actor.getActorId());
        actorDTO.setActorName(actor.getActorName());

        Set<MovieShortDTO> movieDTOs = new HashSet<>();
        for (MovieDetail movie : actor.getMovies()) {
            MovieShortDTO movieDTO = convertToShort(movie);
            movieDTOs.add(movieDTO);
        }

        actorDTO.setMovies(movieDTOs);

        return actorDTO;
    }

    public MovieShortDTO convertToShort(MovieDetail detail){
    	MovieShortDTO newShortDTO = new MovieShortDTO();
    	newShortDTO.setMovieId(detail.getMovieId());
    	newShortDTO.setTitle(detail.getTitle());
    	newShortDTO.setPosterPath(detail.getPosterPath());
    	newShortDTO.setMoviePath(detail.getMoviePath());
    	return newShortDTO;
    }

    public DirectorShortDTO convertToShort(Director detail){
        DirectorShortDTO newShortDTO = new DirectorShortDTO();
        newShortDTO.setDirectorId(detail.getDirectorId());
        newShortDTO.setDirectorName(detail.getDirectorName());
        return newShortDTO;
    }

    public ActorShortDTO convertToShort(Actor detail){
        ActorShortDTO newShortDTO = new ActorShortDTO();
        newShortDTO.setActorId(detail.getActorId());
        newShortDTO.setActorName(detail.getActorName());
        return newShortDTO;
    }

    public GenreShortDTO convertToShort(Genre detail){
        GenreShortDTO newShortDTO = new GenreShortDTO();
        newShortDTO.setGenreId(detail.getGenreId());
        newShortDTO.setGenreName(detail.getGenreName());
        return newShortDTO;
    }

    public AccountDTO convertToDTO(Account detail){
        AccountDTO newAccountDTO = new AccountDTO(); 
        newAccountDTO.setUsername(detail.getUser().getUsername());
        newAccountDTO.setName(detail.getName());
        newAccountDTO.setDateOfBirth(detail.getDateOfBirth());
        newAccountDTO.setEmail(detail.getEmail());
        newAccountDTO.setPhoneNumber(detail.getPhoneNumber());
        newAccountDTO.setUserId(detail.getUser().getId());
        return newAccountDTO;
    }

    public UserDTO convertToDTO(Users detail){
        UserDTO userDto = new UserDTO(); 
        userDto.setUsername(detail.getUsername());
        userDto.setPassword(detail.getPassword());
        userDto.setRole(detail.getRole());
        return userDto; 
    }
}
