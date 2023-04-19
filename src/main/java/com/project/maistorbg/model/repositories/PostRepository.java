package com.project.maistorbg.model.repositories;

import com.project.maistorbg.model.entities.Post;
import com.project.maistorbg.model.entities.User;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {
    void deleteById(int id);
    List<Post> findAllByOwner(User User);
    List<Post> findAllByCategory(Category category);
}
