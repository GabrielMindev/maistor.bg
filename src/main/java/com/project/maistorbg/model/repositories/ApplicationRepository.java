package com.project.maistorbg.model.repositories;

import com.project.maistorbg.model.entities.Application;
import com.project.maistorbg.model.entities.Post;
import com.project.maistorbg.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    List<Application> findAllByPost(Post post);

}