package com.recflix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recflix.Exception.*;
import com.recflix.model.*;
import com.recflix.dto.*;
import com.recflix.repository.*;

import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    
    private final ConvertDTOService converter;

    @Autowired
    public GenreService(GenreRepository genreRepository,
                                ConvertDTOService converter) 
    {
        this.genreRepository = genreRepository;
        this.converter = converter;
    }
    // Create
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    // Read All
    public Set<GenreShortDTO> getAllGenres() {
        Set<GenreShortDTO> genreDTOs = new HashSet<>();
        genreRepository.findAll().forEach(genre -> {
            genreDTOs.add(converter.convertToShort(genre));
        });
        return genreDTOs;
    }

    // Read By ID
    public GenreDetailDTO getGenreById(Long id) {
        Genre returnGenre = genreRepository.findById(id)
                                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phim có id: "+id) );
        return converter.convertToDTO(returnGenre);
    }

    // Update
    public Genre updateGenre(Long id, Genre genre) {
        return genreRepository.findById(id).map(existingGenre -> {
            existingGenre.setGenreName(genre.getGenreName());
            return genreRepository.save(existingGenre);
        }).orElseThrow(() -> new RuntimeException("Genre not found with id: " + id));
    }

    // Delete
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }

    // private GenreDetailDTO converter.convertToDTO(Genre genre) {

    //     GenreDetailDTO genreDTO = new GenreDetailDTO();

    //     Set<MovieShortDTO> movieDTOs = new HashSet<>();
    //     for (MovieDetail movie : genre.getMovies()) {
    //         MovieShortDTO movieDTO = converter.convertToShort(movie);
    //         movieDTOs.add(movieDTO);
    //     }
    //     genreDTO.setMovies(movieDTOs);

    //     return genreDTO;
    // }

    // public GenreShortDTO convertGenreToShort(Genre detail){
    //     GenreShortDTO newShortDTO = new GenreShortDTO();
    //     newShortDTO.setGenreId(detail.getGenreId());
    //     newShortDTO.setGenreName(detail.getGenreName());
    //     return newShortDTO;
    // }
}
