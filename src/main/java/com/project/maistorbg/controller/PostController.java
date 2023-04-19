package com.project.maistorbg.controller;

import com.project.maistorbg.model.DTOs.PostDTOs.PostDTO;
import com.project.maistorbg.model.DTOs.PostDTOs.PostForApplicationResponseDTO;
import com.project.maistorbg.model.DTOs.PostDTOs.PostResponseDTO;
import com.project.maistorbg.model.entities.Post;
import com.project.maistorbg.model.repositories.ApplicationRepository;

import com.project.maistorbg.model.repositories.PostRepository;
import com.project.maistorbg.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PostController extends MyExceptionHandler {

    @Autowired
    PostService postService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    PostRepository postRepository;
//    @Autowired
//    CityRepository cityRepository;
    @Autowired
    ApplicationRepository applicationRepository;

    private final HttpServletRequest request;

    public PostController(PostService postService, HttpServletRequest request) {
        this.postService = postService;
        this.request = request;
    }

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDTO> addPost(@RequestBody PostDTO postDTO) {
        Integer userId = (Integer) request.getSession().getAttribute("LOGGED_ID");
        // code to create a post
        PostResponseDTO post = postService.addPost(userId, postDTO); // assuming this method returns the created post
        PostResponseDTO postResponseDTO = new PostResponseDTO(post); // todo трябваше да направя конструктор?
        return ResponseEntity.ok(postResponseDTO);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable int postId) {
        postRepository.deleteById(postId);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/posts/{id}")
    public ResponseEntity<PostResponseDTO> editPost(@PathVariable("id") int id, @RequestBody PostDTO postDTO) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Post post = optionalPost.get();
        if (post.getOwner().getId() != (int) request.getSession().getAttribute("USER_ID")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        post.setDescription(postDTO.getDescription());
        //post.setCity(postDTO.getCityName());
        postRepository.save(post);
        PostResponseDTO postResponseDTO = new PostResponseDTO(post); //// todo трябваше да направя конструктор?
        return ResponseEntity.ok(postResponseDTO);
    }


  /*  @PostMapping("/posts/{postId}/applications/{applicationId}/accept")
    public ResponseEntity<PostResponseDTO> acceptApplication(@PathVariable("postId") int postId, @PathVariable("applicationId") int applicationId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            Optional<Application> optionalApplication = post.getApplications().stream().filter(a -> a.getId() == applicationId).findFirst();
            if (optionalApplication.isPresent()) {
                Application application = optionalApplication.get();
                applicationRepository.save(application);
                postRepository.save(post);
                PostResponseDTO postResponseDTO = new PostResponseDTO(post);
                return ResponseEntity.ok(postResponseDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }  */

    @GetMapping("/posts/{id}")
    public ResponseEntity<List<PostResponseDTO>> getPostsForUser(@PathVariable int id, HttpServletRequest request) {
        List<Post> posts = postService.getAllPostForUser(id);
        List<PostResponseDTO> dto = mapper.map(posts, new TypeToken<List<PostResponseDTO>>() {
        }.getType());
        return ResponseEntity.ok(dto);
    }

  /*  @GetMapping("/posts/{categoryName}")
    public PageImpl<PostForApplicationResponseDTO> getPostsForCategory(@PathVariable String categoryName, @RequestParam Optional<Integer> page, @RequestParam Optional<String> sortBy, HttpServletRequest request) {
        return postService.getPostsForCategory(categoryName, page, sortBy);
    } */

    @GetMapping("/posts/city/{cityName}")
    public ResponseEntity<List<PostResponseDTO>> getAllByCity(@PathVariable String cityName) {
        List<Post> posts = postRepository.findAllByCity(cityName);
        List<PostResponseDTO> postResponseDTOs = posts.stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(postResponseDTOs);
    }
}