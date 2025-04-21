package com.recflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.recflix.model.Actor;
import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Long> {
	Optional<Actor> findByActorName(String name);
}
