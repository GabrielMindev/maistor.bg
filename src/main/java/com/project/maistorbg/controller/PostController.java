package com.project.maistorbg.controller;

import com.project.maistorbg.model.DTOs.PostDTOs.PostDTO;
import com.project.maistorbg.model.DTOs.PostDTOs.PostForApplicationResponseDTO;
import com.project.maistorbg.model.DTOs.PostDTOs.PostResponseDTO;
import com.project.maistorbg.model.entities.Application;
import com.project.maistorbg.model.entities.Post;
import com.project.maistorbg.model.exceptions.NotFoundException;
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
public class PostController extends AbstractController {

    @Autowired
    PostService postService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    PostRepository postRepository;
//    @Autowired
//    private PostResponseDTO postResponseDTO;
//    @Autowired
//    CityRepository cityRepository;
    @Autowired
    ApplicationRepository applicationRepository;



//    public PostController(PostService postService, HttpServletRequest request) {
//        this.postService = postService;
//        this.request = request;
//    }

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDTO> addPost(HttpServletRequest request,@RequestBody PostDTO postDTO) {
        Integer userId = (Integer) request.getSession().getAttribute("LOGGED_ID");
        // code to create a post
        PostResponseDTO post = postService.addPost(userId, postDTO); // assuming this method returns the created post
        //PostResponseDTO postResponseDTO = new PostResponseDTO(post); // todo трябваше да направя конструктор?
        return ResponseEntity.ok(post);
        //return ResponseEntity.ok(postService.addPost((Integer) request.getSession().getAttribute("LOGGED"),postDTO));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDTO> deletePost(@PathVariable int postId,HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("LOGGED_ID");
        PostResponseDTO post = postService.deletePost(postId,userId);
        return ResponseEntity.ok(postService.deletePost(postId, (Integer) request.getSession().getAttribute("LOGGED_ID")));

    }


    @PutMapping("/posts/{id}")
    public ResponseEntity<PostResponseDTO> editPost(@PathVariable int id, @RequestBody PostDTO postDTO,HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("LOGGED_ID");
        // code to create a post
        PostResponseDTO post = postService.editPost(postDTO, id,userId); // assuming this method returns the created post
        //PostResponseDTO postResponseDTO = new PostResponseDTO(post); // todo трябваше да направя конструктор?
        return ResponseEntity.ok(post);
    }



    @GetMapping("users/{id}/posts")
    public ResponseEntity<List<PostResponseDTO>> getPostsForUser(@PathVariable int id, HttpServletRequest request) {
        List<Post> posts = postService.getAllPostForUser(id);
        List<PostResponseDTO> dto = mapper.map(posts, new TypeToken<List<PostResponseDTO>>() {
        }.getType());
        return ResponseEntity.ok(dto);
    }

//    @GetMapping("/posts/{categoryName}")
//    public PageImpl<PostForApplicationResponseDTO> getPostsForCategory(@PathVariable String categoryName, @RequestParam Optional<Integer> page, @RequestParam Optional<String> sortBy, HttpServletRequest request) {
//        return postService.getPostsForCategory(categoryName, page, sortBy);
//    }

//    @GetMapping("/posts/city/{cityName}")
//    public ResponseEntity<List<PostResponseDTO>> getAllByCity(@PathVariable String cityName) {
//        List<Post> posts = postRepository.findAllByCityName(cityName);
//        List<PostResponseDTO> postResponseDTOs = posts.stream()
//                .map(PostResponseDTO::new)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(postResponseDTOs);
//    }

    @GetMapping("/posts/all")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PostResponseDTO> getAll(){
        return postService.getAll();
    }

//    @GetMapping("/posts/{cityName}")
//    public ResponseEntity<List<PostResponseDTO>> getAllByCity(@PathVariable String cityName) {
//        List<Post> posts = postRepository.findAllByCityName(cityName);
//        List<PostResponseDTO> postResponseDTOs = posts.stream()
//                .map(postResponseDTO::create)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(postResponseDTOs);
//    }
}