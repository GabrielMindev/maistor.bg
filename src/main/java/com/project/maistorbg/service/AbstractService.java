package com.project.maistorbg.service;

import com.project.maistorbg.model.entities.User;
import com.project.maistorbg.model.exceptions.NotFoundException;
import com.project.maistorbg.model.repositories.RepairCategoryRepository;
import com.project.maistorbg.model.repositories.CommentRepository;
import com.project.maistorbg.model.repositories.RatingRepository;
import com.project.maistorbg.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RatingRepository ratingRepository;

    @Autowired
    protected CommentRepository commentRepository;

    protected User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }
    @Autowired
    protected RepairCategoryRepository repairCategoryRepository;

    @Autowired
    protected ModelMapper mapper;

}

