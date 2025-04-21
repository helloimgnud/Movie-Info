package com.recflix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recflix.Exception.*;
import com.recflix.model.*;
import com.recflix.dto.*;
import com.recflix.repository.*;

import java.util.*;

@Service
public class ActorService {

    private final ActorRepository actorRepository;
    
    private final ConvertDTOService converter;

    @Autowired
    public ActorService(ActorRepository actorRepository,
                                ConvertDTOService converter) 
    {
        this.actorRepository = actorRepository;
        this.converter = converter;
    }

    public Set<ActorShortDTO> getAllActors() {
        Set<ActorShortDTO> actorDTOs = new HashSet<>();
        actorRepository.findAll().forEach(actor -> {
            actorDTOs.add(converter.convertToShort(actor));
        });
        return actorDTOs;
    }

    // Read By ID
    public ActorDetailDTO getActorById(Long id) {
        Actor returnActor = actorRepository.findById(id)
                                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phim có id: "+id) );
        return converter.convertToDTO(returnActor);
    }

    //Update
    public Actor updateActor(Long id, Actor actor) {
        return actorRepository.findById(id).map(existingActor -> {
            existingActor.setActorName(actor.getActorName());
            return actorRepository.save(existingActor);
        }).orElseThrow(() -> new RuntimeException("Actor not found with id: " + id));
    }

    //Delete
    public void deleteActor(Long id) {
        actorRepository.deleteById(id);
    }

}
