package com.project.maistorbg.model.repositories;

import com.project.maistorbg.model.DTOs.UserAdditionalInfoDTO;
import com.project.maistorbg.model.DTOs.UserWithoutPasswordDTO;
import com.project.maistorbg.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    Optional<User> findUsersByEmail(String email);

    Optional<User> findUserByPhoneNumber(String phoneNumber);

    // @Query("SELECT id, username, age, role_name, first_name, last_name, email, phone_number, registration_date, profile_photo_url FROM User u JOIN u.categories c WHERE c.nameRepair = :categoryName")
    //List<UserWithoutPasswordDTO> findAllByCategory(String categoryName);


}
