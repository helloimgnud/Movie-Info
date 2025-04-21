package com.recflix.repository;

import com.recflix.model.MovieDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.*;

@Repository
public interface MovieDetailRepository extends JpaRepository<MovieDetail, Long> {

	List<MovieDetail> findTopNByOrderByViewDesc(Pageable pageable);

	@Query(value = "SELECT * FROM movie_details ORDER BY view_count DESC LIMIT :limit", nativeQuery = true)
	List<MovieDetail> findTopMovies(@Param("limit") int limit);

	@Query(value = "SELECT * FROM movie_details ORDER BY imdb_rating DESC LIMIT :limit", nativeQuery = true)
	List<MovieDetail> findTopRatingMovies(@Param("limit") int limit);
}
