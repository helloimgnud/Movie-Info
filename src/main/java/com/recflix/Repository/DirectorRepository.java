package com.recflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.recflix.model.Director;
import java.util.Optional;

public interface DirectorRepository extends JpaRepository<Director, Long> {
	Optional<Director> findByDirectorName(String name);
}
