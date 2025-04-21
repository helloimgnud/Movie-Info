package com.recflix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.recflix.model.*;
import com.recflix.dto.*;
import com.recflix.service.DirectorService;

import java.util.Optional;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/directors")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @GetMapping
    public Set<DirectorShortDTO> getAllDirectors() {
        return directorService.getAllDirectors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorDetailDTO> getDirectorById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(directorService.getDirectorById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable Long id) {
        directorService.deleteDirector(id);
        return ResponseEntity.noContent().build();
    }
}
