package com.project.maistorbg.service;

import com.project.maistorbg.model.entities.User;
import com.project.maistorbg.model.exceptions.NotFoundException;
import com.project.maistorbg.model.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    RepairCategoryRepository repairCategoryRepository;

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    protected ModelMapper mapper;

    protected User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

}

