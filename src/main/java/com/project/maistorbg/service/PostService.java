package com.project.maistorbg.service;


import com.project.maistorbg.model.DTOs.PostDTOs.PostDTO;
import com.project.maistorbg.model.DTOs.PostDTOs.PostForApplicationResponseDTO;
import com.project.maistorbg.model.DTOs.PostDTOs.PostResponseDTO;
//import com.project.maistorbg.model.entities.City;
import com.project.maistorbg.model.entities.Post;
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
        CategoryRepository categoryRepository;
//        @Autowired
//        CityRepository cityRepository;
        @Autowired
        ApplicationRepository ApplicationRepository;
       // @Autowired
       // JdbcTemplate jdbcTemplate;

        public PostResponseDTO addPost(int id, PostDTO postDTO) {
            User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
            //City city = cityRepository.findByName(postDTO.getCityName()).orElseThrow(()-> new NotFoundException("City not found"));
            if (postDTO.getDescription() == null || postDTO.getDescription().isBlank() || postDTO.getDescription().isEmpty()){
                throw new BadRequestException("Description is mandatory");
            }
            Post post = new Post();
            post.setOwner(user);
            post.setDescription(postDTO.getDescription());
            post.setPublication_date(LocalDate.now());
            post.setCity(post.getCity());
            post = postRepository.save(post);
            return  modelMapper.map(post, PostResponseDTO.class);
        }
        public PostResponseDTO deletePost(int id, int userId){
            Post post = postRepository.findById(id).orElseThrow(()->new NotFoundException("Post not found"));
            if (post.getOwner().getId() != userId){
                throw new BadRequestException("User isn't post owner");
            }
            postRepository.deleteById(id);
            return modelMapper.map(post,PostResponseDTO.class);
        }

        public PageImpl<PostForApplicationResponseDTO> getAllPosts(Optional<Integer> page, Optional<String> sortBy) {
            List<PostForApplicationResponseDTO> dtos = postRepository.findAll(PageRequest.of(page.orElse(0),5, Sort.Direction.DESC, sortBy.orElse("postedDate")))
                    .stream().map(post -> modelMapper.map(post,PostForApplicationResponseDTO.class)).collect(Collectors.toList());
            return  new PageImpl<>(dtos);
        }

     /*   public PageImpl<PostForApplicationResponseDTO> getPostsForCategory(String categoryName,Optional<Integer> page, Optional<String> sortBy){

            List<PostForApplicationResponseDTO> dtos = postRepository.findAllByCategory(category,PageRequest
                            .of(page.orElse(0),5, Sort.Direction.DESC, sortBy.orElse("postedDate")))
                    .stream()
                    .map(post -> modelMapper.map(post,PostForApplicationResponseDTO.class)).collect(Collectors.toList());
            return new PageImpl<>(dtos);
        } */

        public PostResponseDTO editPost(PostDTO postDTO, int id, int userId) {
            Post post = postRepository.findById(id).orElseThrow(()->new NotFoundException("Post not found"));
            if (postDTO.getDescription() == null || postDTO.getDescription().isEmpty() || postDTO.getDescription().isBlank()){
                throw new BadRequestException("Description is mandatory");
            }
            if (userId != post.getOwner().getId()){
                throw new UnauthorizedException("User isn't post owner");
            }
            post.setDescription(postDTO.getDescription());
            post.setCity(postDTO.getCityName());
            post = postRepository.save(post);
            return modelMapper.map(post,PostResponseDTO.class);
        }
        /*  public PostResponseDTO acceptApplication(int postId, int offerId, int userId) {
            Post post = postRepository.findById(postId).orElseThrow(()-> new NotFoundException("Post not found"));
            Application application = offerRepository.findById(applicationId).orElseThrow(()-> new NotFoundException("Application not found"));
            if (application.getPost() != post){
                throw new BadRequestException("This offer doesn't belong to this post");
            }
            if (post.getOwner().getId() != userId){
                throw new UnauthorizedException("User is not post owner");
            }
            if (post.getAcceptedApplication() != null) {
                throw new BadRequestException("This post already has accepted offer");
            }
            post.setAcceptedApplicatin(application);
            //post.setAssignedDate(LocalDate.now());
            post = postRepository.save(post);
            return modelMapper.map(post,PostResponseDTO.class);
        } */

        public List<Post> getAllPostForUser(int  id){
            User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found!"));
            return postRepository.findAllByOwner(user);
        }
}
