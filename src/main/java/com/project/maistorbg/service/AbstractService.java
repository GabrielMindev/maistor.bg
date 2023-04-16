package com.project.maistorbg.service;

import com.project.maistorbg.model.repositories.CategoryRepository;
import com.project.maistorbg.model.repositories.CommentRepository;
import com.project.maistorbg.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractService {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected CommentRepository commentRepository;
    @Autowired
    protected ModelMapper mapper;



}

