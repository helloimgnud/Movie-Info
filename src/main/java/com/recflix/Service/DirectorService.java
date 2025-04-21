package com.recflix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recflix.Exception.*;
import com.recflix.model.*;
import com.recflix.dto.*;
import com.recflix.repository.*;

import java.util.*;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;
    
    private final ConvertDTOService converter;

    @Autowired
    public DirectorService(DirectorRepository directorRepository,
                                ConvertDTOService converter) 
    {
        this.directorRepository = directorRepository;
        this.converter = converter;
    }

    public Set<DirectorShortDTO> getAllDirectors() {
        Set<DirectorShortDTO> directorDTOs = new HashSet<>();
        directorRepository.findAll().forEach(director -> {
            directorDTOs.add(converter.convertToShort(director));
        });
        return directorDTOs;
    }

    // Read By ID
    public DirectorDetailDTO getDirectorById(Long id) {
        Director returnDirector = directorRepository.findById(id)
                                    .orElseThrow(() -> new ResourceNotFoundException("Director not found with id: "+id) );
        return converter.convertToDTO(returnDirector);
    }

    // Update
    public Director updateDirector(Long id, Director director) {
        return directorRepository.findById(id).map(existingDirector -> {
            existingDirector.setDirectorName(director.getDirectorName());
            return directorRepository.save(existingDirector);
        }).orElseThrow(() -> new RuntimeException("Director not found with id: " + id));
    }

    // Delete
    public void deleteDirector(Long id) {
        directorRepository.deleteById(id);
    }
}
