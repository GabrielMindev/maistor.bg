package com.project.maistorbg.model.repositories;

import com.project.maistorbg.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    Optional<User>findUsersByEmail(String email);
    Optional<User> findUserByPhoneNumber(String phoneNumber);

}
