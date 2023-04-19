package com.project.maistorbg.model.repositories;

import com.project.maistorbg.model.entities.RepairCategory;
import com.project.maistorbg.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    Optional<User> findUsersByEmail(String email);

    Optional<User> findUserByPhoneNumber(String phoneNumber);
    List<User> getAllByCategoriesContaining(RepairCategory category);


}
