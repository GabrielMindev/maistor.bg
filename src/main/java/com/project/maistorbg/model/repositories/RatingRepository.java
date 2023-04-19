package com.project.maistorbg.model.repositories;

import com.project.maistorbg.model.entities.Rating;
import com.project.maistorbg.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer>{
    @Query(value = "SELECT * FROM workman_ratings WHERE ratings_workman_id = :ratedId AND client_id = :raterId LIMIT 1",nativeQuery = true)
    Optional<Rating> findByUserIdAndRatedWorkmanId(@Param("raterId") int userId, @Param("ratedId") int ratedWorkmanId);
}