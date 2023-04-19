package com.project.maistorbg.model.repositories;

import com.project.maistorbg.model.entities.Application;
import com.project.maistorbg.model.entities.Post;
import com.project.maistorbg.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    List<Application> findAllByUser(User user);
    List<Application> findAllByPost(Post post);

}
