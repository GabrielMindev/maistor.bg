package com.project.maistorbg.model.repositories;

import com.project.maistorbg.model.entities.Post;
import com.project.maistorbg.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    void deleteById(int id);
    Page<Post> findAllByOwner(Pageable pageable,User user);
}
