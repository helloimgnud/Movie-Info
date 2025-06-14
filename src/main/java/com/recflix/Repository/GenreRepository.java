package com.recflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.recflix.model.Genre;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
	Optional<Genre> findByGenreName(String name);
}
