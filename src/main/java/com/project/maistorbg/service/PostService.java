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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService extends AbstractService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    RepairCategoryRepository categoryRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    public PostResponseDTO addPost(int id, PostDTO postDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        RepairCategory category = repairCategoryRepository.findByName(postDTO.getName()).orElseThrow(() -> new NotFoundException("Category not found"));
        if (postDTO.getDescription() == null || postDTO.getDescription().isBlank() || postDTO.getDescription().isEmpty()) {
            throw new BadRequestException("Description is mandatory");
        }
        Post post = new Post();
        post.setOwner(user);
        post.setDescription(postDTO.getDescription());
        post.setPublication_date(LocalDate.now());
        post.setCity(postDTO.getCity());
        post.setRepairCategory(category);

        post = postRepository.save(post);
        return modelMapper.map(post, PostResponseDTO.class);
    }

    public PostResponseDTO deletePost(int id, int userId) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));
        if (post.getOwner().getId() != userId) {
            throw new BadRequestException("User isn't post owner");
        }
        postRepository.deleteById(id);
        return modelMapper.map(post, PostResponseDTO.class);
    }


    public PostResponseDTO editPost(PostDTO postDTO, int id, int userId) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));
        if (postDTO.getDescription() == null || postDTO.getDescription().isEmpty() || postDTO.getDescription().isBlank()) {
            throw new BadRequestException("Description is mandatory");
        }
        if (userId != post.getOwner().getId()) {
            throw new UnauthorizedException("User isn't post owner");
        }
        post.setDescription(postDTO.getDescription());
        post.setCity(postDTO.getCity());
        post = postRepository.save(post);
        return modelMapper.map(post, PostResponseDTO.class);
    }


    public List<Post> getAllPostForUser(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));
        return postRepository.findAllByOwner(user);
    }

    public List<PostResponseDTO> getAll() {
        List<Post> posts = postRepository.findAll();
        List<PostResponseDTO> responseOfferDTOList = posts.stream()
                .map(e -> modelMapper.map(e, PostResponseDTO.class)).collect(Collectors.toList());
        return responseOfferDTOList;
    }


}
