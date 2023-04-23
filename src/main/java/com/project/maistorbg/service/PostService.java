package com.project.maistorbg.service;


import com.project.maistorbg.model.DTOs.PostDTOs.PostDTO;
import com.project.maistorbg.model.DTOs.PostDTOs.PostForApplicationResponseDTO;
import com.project.maistorbg.model.DTOs.PostDTOs.PostResponseDTO;
//import com.project.maistorbg.model.entities.City;
import com.project.maistorbg.model.entities.Application;
import com.project.maistorbg.model.entities.Post;
import com.project.maistorbg.model.entities.RepairCategory;
import com.project.maistorbg.model.entities.User;
import com.project.maistorbg.model.exceptions.BadRequestException;
import com.project.maistorbg.model.exceptions.NotFoundException;
import com.project.maistorbg.model.exceptions.UnauthorizedException;
import com.project.maistorbg.model.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService extends AbstractService {

    public PostResponseDTO addPost(int id, PostDTO postDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        RepairCategory category = repairCategoryRepository.findByName(postDTO.getName()).orElseThrow(() -> new NotFoundException("Category not found"));
        if (postDTO.getDescription() == null || postDTO.getDescription().isBlank()) {
            throw new BadRequestException("Description is mandatory");
        }
        if (postDTO.getCity() == null || postDTO.getCity().isBlank()) {
            throw new BadRequestException("City is mandatory");
        }
        if (postDTO.getName() == null || postDTO.getName().isBlank()) {
            throw new BadRequestException("Category name is mandatory");
        }
        Post post = new Post();
        post.setOwner(user);
        post.setDescription(postDTO.getDescription());
        post.setPublication_date(LocalDate.now());
        post.setCity(postDTO.getCity());
        post.setRepairCategory(category);

        post = postRepository.save(post);
        return mapper.map(post, PostResponseDTO.class);
    }

    public PostResponseDTO deletePost(int id, int userId) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));
        if (post.getOwner().getId() != userId) {
            throw new BadRequestException("User isn't post owner");
        }
        postRepository.deleteById(id);
        return mapper.map(post, PostResponseDTO.class);
    }


    public PostResponseDTO editPost(PostDTO postDTO, int id, int userId) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));
        RepairCategory category = repairCategoryRepository.findByName(postDTO.getName()).orElseThrow(() -> new NotFoundException("Category not found"));
        if (postDTO.getDescription() == null || postDTO.getDescription().isEmpty() || postDTO.getDescription().isBlank()) {
            throw new BadRequestException("Description is mandatory");
        }
        if (postDTO.getCity() == null || postDTO.getCity().isBlank()) {
            throw new BadRequestException("City is mandatory");
        }
        if (postDTO.getName() == null || postDTO.getName().isBlank()) {
            throw new BadRequestException("Category name is mandatory");
        }

        if (userId != post.getOwner().getId()) {
            throw new UnauthorizedException("User isn't post owner");
        }
        post.setDescription(postDTO.getDescription());
        post.setCity(postDTO.getCity());
        post.setRepairCategory(category);
        post = postRepository.save(post);
        return mapper.map(post, PostResponseDTO.class);
    }


    public Page<PostResponseDTO> getAllPostForUser(int id, int page, int size) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts =  postRepository.findAllByOwner(pageable, user);

        return posts.map(post -> mapper.map(post, PostResponseDTO.class));
    }


    public Page<PostResponseDTO> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(post -> mapper.map(post, PostResponseDTO.class));
    }



}
